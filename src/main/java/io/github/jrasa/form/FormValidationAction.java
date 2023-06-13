package io.github.jrasa.form;

import io.github.jrasa.CollectingDispatcher;
import io.github.jrasa.domain.Domain;
import io.github.jrasa.domain.Form;
import io.github.jrasa.event.SlotSet;
import io.github.jrasa.tracker.Tracker;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A helper class for slot validations and extractions of custom slots in forms.
 *
 * @author uncle-lv
 */
public abstract class FormValidationAction extends ValidationAction {
    private static final String REQUESTED_SLOT = "requested_slot";

    /**
     * Returns the form's name.
     */
    public String formName() {
        return this.name().replaceFirst("validate_", "");
    }

    /**
     * Sets the next slot which should be requested.
     * Skips setting the next requested slot in case missing slots was not overridden.
     *
     * @param dispatcher the dispatcher which is used to send messages back to the user.
     * @param tracker the conversation tracker for the current user.
     * @param domain the bot's domain.
     * @return null in case missing slots was not overridden and returns null.
     *         Otherwise returns a {@link SlotSet} event for the next slot to be requested.
     *         If the {@link SlotSet} event sets requested slot to null, the form will be deactivated.
     */
    public SlotSet nextRequestedSlot(CollectingDispatcher dispatcher, Tracker tracker, Domain domain) {
        List<String> requiredSlots = this.requiredSlots(this.domainSlots(domain), dispatcher, tracker, domain);
        if (this.domainSlots(domain).equals(requiredSlots)) {
            return null;
        }

        List<String> missingSlots = requiredSlots.stream()
                .filter(slotName -> null == tracker.getSlot(slotName))
                .collect(Collectors.toList());

        return new SlotSet(REQUESTED_SLOT, missingSlots);
    }

    /**
     * Returns slots which were mapped in the domain.
     *
     * @param domain The current domain.
     * @return Slot names which should be filled by the form.
     *         By default it returns the slot names which are listed for this form in the domain and use predefined mappings.
     */
    public List<String> domainSlots(Domain domain) {
        Map<String, Form> forms = domain.getForms();
        if (null == forms) {
            return Collections.emptyList();
        }

        Form form = forms.get(this.formName());
        if (form.getRequiredSlots() != null) {
            return null == form.getRequiredSlots() ? Collections.emptyList() : form.getRequiredSlots();
        }

        return Collections.emptyList();
    }

    private List<SlotSet> extractValidationEvents(CollectingDispatcher dispatcher, Tracker tracker, Domain domain) {
        List<SlotSet> validationEvents = this.getValidationEvents(dispatcher, tracker, domain);
        tracker.addSlots(validationEvents);
        SlotSet nextSlot = this.nextRequestedSlot(dispatcher, tracker, domain);
        if (nextSlot != null) {
            validationEvents.add(nextSlot);
        }
        return validationEvents;
    }
}
