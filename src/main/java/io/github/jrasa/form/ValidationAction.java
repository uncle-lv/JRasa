package io.github.jrasa.form;

import io.github.jrasa.Action;
import io.github.jrasa.CollectingDispatcher;
import io.github.jrasa.common.StringUtils;
import io.github.jrasa.domain.Condition;
import io.github.jrasa.domain.Domain;
import io.github.jrasa.domain.Mapping;
import io.github.jrasa.domain.Slot;
import io.github.jrasa.event.Event;
import io.github.jrasa.event.SlotSet;
import io.github.jrasa.message.Message;
import io.github.jrasa.tracker.Tracker;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A helper class for slot validations and extractions of custom slots.
 *
 * @author uncle-lv
 */
@Slf4j
public abstract class ValidationAction implements Action {
    private static final String NAME = "action_validate_slot_mappings";

    /**
     * Unique identifier of this simple action.
     */
    @Override
    public String name() {
        return NAME;
    }

    /**
     * Runs the custom actions. Please the docstring of the parent class.
     */
    @Override
    public List<? extends Event> run(CollectingDispatcher dispatcher, Tracker tracker, Domain domain) {
        List<SlotSet> extractionEvents = this.getExtractionEvents(dispatcher, tracker, domain);
        tracker.addSlots(extractionEvents);
        return this.extractValidationEvents(dispatcher, tracker, domain);
    }

    /**
     * Extracts custom slots using available `extract{SlotName}` methods.
     * Uses the information from {@link ValidationAction#requiredSlots(List, CollectingDispatcher, Tracker, Domain)} to gather which slots should be extracted.
     *
     * @param dispatcher dispatcher: the dispatcher which is used to send messages back to the user.
     *                   Use {@link CollectingDispatcher#utterMessage(Message)} for sending messages.
     * @param tracker the state tracker for the current user.
     *                You can access slot values using #{@link Tracker#getSlot(String)},
     *                the most recent user message is {@link Tracker#getLatestMessage()} and any other {@link Tracker} property.
     * @param domain the bot's domain.
     * @return {@link SlotSet} for any extracted slots.
     */
    private List<SlotSet> getExtractionEvents(CollectingDispatcher dispatcher, Tracker tracker, Domain domain) {
        Map<String, Object> customSlots = new HashMap<>();
        List<String> slotsToExtract = this.requiredSlots(
                this.domainSlots(domain),
                dispatcher,
                tracker,
                domain
        );

        for (String slot : slotsToExtract) {
            Map<String, Object> extractionOutput = this.extractSlot(slot, dispatcher, tracker, domain);
            customSlots.putAll(extractionOutput);
            tracker.updateSlots(extractionOutput);
        }
        return customSlots.entrySet().stream()
                .map(entry -> new SlotSet(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Validate slots by calling a validation function for each slot.
     *
     * @param dispatcher the dispatcher which is used to send messages back to the user.
     * @param tracker the conversation tracker for the current user.
     * @param domain the bot's domain.
     * @return {@link SlotSet} events for every validated slot.
     */
    public List<SlotSet> getValidationEvents(CollectingDispatcher dispatcher, Tracker tracker, Domain domain) {
        List<String> slotsToValidate = this.requiredSlots(this.domainSlots(domain), dispatcher, tracker, domain);
        Map<String, Object> slots = tracker.slotsToValidate();
        for (Map.Entry<String, Object> entry : new ArrayList<>(slots.entrySet())) {
            String slotName = entry.getKey();
            Object slotValue = entry.getValue();
            if (!slotsToValidate.contains(slotName)) {
                slots.remove(slotName);
                continue;
            }

            String methodName = String.format("validate%s", StringUtils.upperInitial(slotName));
            Method validateMethod;
            try {
                validateMethod = this.getClass().getMethod(
                        methodName,
                        Object.class,
                        CollectingDispatcher.class,
                        Tracker.class,
                        Domain.class
                );
            } catch (NoSuchMethodException e) {
                validateMethod = null;
            }

            if (null == validateMethod) {
                log.warn("Skipping validation for `{}`: there is no validation method specified.", slotName);
                continue;
            }

            Object retVal;
            try {
                retVal = validateMethod.invoke(this, slotValue, dispatcher, tracker, domain);
            } catch (IllegalAccessException | InvocationTargetException e) {
                retVal = null;
            }
            if (retVal instanceof Map) {
                Map<String, Object> validationOutput = (Map<String, Object>) retVal;
                slots.putAll(validationOutput);
                tracker.updateSlots(validationOutput);
            } else {
                log.warn("Cannot validate `{}`: make sure the validation method returns the correct output.", slotName);
            }
        }
        return slots.entrySet().stream()
                .map(entry -> new SlotSet(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Returns slots which the validation action should fill.
     *
     * @param domainSlots names of slots of this form which were mapped in the domain.
     * @param dispatcher the dispatcher which is used to send messages back to the user.
     * @param tracker the conversation tracker for the current user.
     * @param domain the bot's domain.
     * @return a list of slot names.
     */
    protected List<String> requiredSlots(List<String> domainSlots, CollectingDispatcher dispatcher, Tracker tracker, Domain domain) {
        return domainSlots;
    }

    /**
     * Returns slots which were mapped in the domain.
     *
     * @param domain The current domain.
     * @return Slot names mapped in the domain which do not include a mapping with an active loop condition.
     */
    private List<String> domainSlots(Domain domain) {
        return this.globalSlots(domain);
    }

    private List<String> globalSlots(Domain domain) {

        Map<String, Slot> allSlots = domain.getSlots();
        if (null == allSlots) {
            allSlots = Collections.emptyMap();
        }

        return allSlots.entrySet().stream()
                .filter(entry -> !isMappedToForm(entry.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private Map<String, Object> extractSlot(String slotName, CollectingDispatcher dispatcher, Tracker tracker, Domain domain) {
        String methodName = String.format("extract%s", StringUtils.upperInitial(slotName));
        boolean slotInDomain = this.domainSlots(domain).contains(slotName);
        Method extractMethod;
        try {
            extractMethod = this.getClass().getMethod(methodName, CollectingDispatcher.class, Tracker.class, Domain.class);
        } catch (NoSuchMethodException e) {
            extractMethod = null;
        }

        if (null == extractMethod) {
            if (!slotInDomain) {
                log.warn(
                        "No method '{}' found for slot '{}'. Skipping extraction for this slot.",
                        methodName, slotName
                );
            }
            return Collections.emptyMap();
        }

        Object retVal;
        try {
            retVal = extractMethod.invoke(this, dispatcher, tracker, domain);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        if (retVal instanceof Map) {
            return (Map<String, Object>) retVal;
        }

        log.warn("Cannot extract `{}`: make sure the extract method returns the correct output.", slotName);
        return Collections.emptyMap();
    }

    private List<SlotSet> extractValidationEvents(CollectingDispatcher dispatcher, Tracker tracker, Domain domain) {
        List<SlotSet> validationEvents = this.getValidationEvents(dispatcher, tracker, domain);
        tracker.addSlots(validationEvents);
        return validationEvents;
    }

    private static boolean isMappedToForm(Slot slot) {
        List<Mapping> mappings = slot.getMappings();
        if (null == mappings || mappings.isEmpty()) {
            return false;
        }

        for (Mapping mapping : mappings) {
            List<Condition> conditions = mapping.getConditions();
            if (null == conditions) {
                conditions = Collections.emptyList();
            }
            for (Condition condition : conditions) {
                if (!StringUtils.isNullOrEmpty(condition.getActiveLoop())) {
                    return true;
                }
            }
        }

        return false;
    }

    private static String slotName(String slotName) {
        StringBuilder name = new StringBuilder();
        String[] words = slotName.split("-");
        for (String word : words) {
            name.append(StringUtils.upperInitial(word));
        }
        return name.toString();
    }
}
