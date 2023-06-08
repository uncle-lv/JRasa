package io.github.jrasa.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DomainTest {
    private static final String JSON = "{" +
              "\"version\": \"3.1\"," +
              "\"session_config\": {" +
                "\"session_expiration_time\": 60," +
                "\"carry_over_slots_to_new_session\": true" +
              "}," +
              "\"intents\": [" +
                "{" +
                  "\"chitchat\": {" +
                    "\"use_entities\": [\"address\", \"date\"]" +
                  "}" +
                "}, " +
                "\"deny\"," +
                "\"goodbye\"," +
                "\"greet\"" +
              "]," +
              "\"entities\": [\"address\", \"date\"]," +
              "\"slots\": {" +
                "\"address\": {" +
                  "\"type\": \"text\"," +
                  "\"influence_conversation\": false," +
                  "\"mappings\": [{\"entity\": \"address\", \"type\": \"from_entity\"}]}," +
                "\"date\": {" +
                  "\"type\": \"text\"," +
                  "\"influence_conversation\": false," +
                  "\"mappings\": [{\"entity\": \"date\", \"type\": \"from_entity\"}]}" +
              "}, " +
              "\"responses\": {" +
                "\"utter_greet\": [{\"text\": \"您好\"}], " +
                "\"utter_goodbye\": [{\"text\": \"再见！\"}]," +
                "\"utter_default\": [{\"text\": \"我不明白您说的话\"}]," +
                "\"utter_chitchat/whoyouare\": [{\"text\": \"你好，我是Rasa，一个智能对话机器人。\"}]" +
              "}," +
              "\"actions\": [" +
                "\"utter_goodbye\"," +
                "\"utter_greet\"," +
                "\"utter_default\"," +
                "\"utter_chitchat\"" +
              "]," +
            "\"forms\": {" +
                "\"ask_form\": {\"required_slots\": [\"address\", \"date\"], \"ignored_intents\": [\"goodbye\"]}" +
              "}" +
            "}";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testDeserialize() throws JsonProcessingException, NoSuchFieldException, IllegalAccessException {
        Domain domain = OBJECT_MAPPER.readValue(JSON, Domain.class);
        Intent chitchat = new Intent();
        Field chitchatName = chitchat.getClass().getDeclaredField("name");
        chitchatName.setAccessible(true);
        chitchatName.set(chitchat, "chitchat");
        Field chitchatUseEntities = chitchat.getClass().getDeclaredField("useEntities");
        chitchatUseEntities.setAccessible(true);
        chitchatUseEntities.set(chitchat, new ArrayList<String>(){{
            add("address");
            add("date");
        }});
        Intent deny = new Intent();
        Field denyName = deny.getClass().getDeclaredField("name");
        denyName.setAccessible(true);
        denyName.set(deny, "deny");
        Intent goodbye = new Intent();
        Field goodbyeName = chitchat.getClass().getDeclaredField("name");
        goodbyeName.setAccessible(true);
        goodbyeName.set(goodbye, "goodbye");
        Intent greet = new Intent();
        Field greetName = chitchat.getClass().getDeclaredField("name");
        greetName.setAccessible(true);
        greetName.set(greet, "greet");

        Slot address = new Slot();
        Field addressType = address.getClass().getDeclaredField("type");
        addressType.setAccessible(true);
        addressType.set(address, "text");
        Field addressInfluenceConversation = address.getClass().getDeclaredField("influenceConversation");
        addressInfluenceConversation.setAccessible(true);
        addressInfluenceConversation.set(address, false);
        Mapping addressMapping = new Mapping();
        Field addressEntityMapping = addressMapping.getClass().getDeclaredField("entity");
        addressEntityMapping.setAccessible(true);
        addressEntityMapping.set(addressMapping, "address");
        Field addressTypeMapping = addressMapping.getClass().getDeclaredField("type");
        addressTypeMapping.setAccessible(true);
        addressTypeMapping.set(addressMapping, "from_entity");
        Field addressMappings = address.getClass().getDeclaredField("mappings");
        addressMappings.setAccessible(true);
        addressMappings.set(address, new ArrayList<Mapping>(){{
            add(addressMapping);
        }});
        Slot date = new Slot();
        Field dateType = date.getClass().getDeclaredField("type");
        dateType.setAccessible(true);
        dateType.set(date, "text");
        Field dateInfluenceConversation = date.getClass().getDeclaredField("influenceConversation");
        dateInfluenceConversation.setAccessible(true);
        dateInfluenceConversation.set(date, false);
        Mapping dateMapping = new Mapping();
        Field dateEntityMapping = dateMapping.getClass().getDeclaredField("entity");
        dateEntityMapping.setAccessible(true);
        dateEntityMapping.set(dateMapping, "date");
        Field dateTypeMapping = dateMapping.getClass().getDeclaredField("type");
        dateTypeMapping.setAccessible(true);
        dateTypeMapping.set(dateMapping, "from_entity");
        Field dateMappings = date.getClass().getDeclaredField("mappings");
        dateMappings.setAccessible(true);
        dateMappings.set(date, new ArrayList<Mapping>(){{
            add(dateMapping);
        }});

        Response utterGreet = new Response();
        Field utterGreetText = utterGreet.getClass().getDeclaredField("text");
        utterGreetText.setAccessible(true);
        utterGreetText.set(utterGreet, "您好");
        Response utterGoodbye = new Response();
        Field utterGoodbyeText = utterGoodbye.getClass().getDeclaredField("text");
        utterGoodbyeText.setAccessible(true);
        utterGoodbyeText.set(utterGoodbye, "再见！");
        Response utterDefault = new Response();
        Field utterDefaultText = utterDefault.getClass().getDeclaredField("text");
        utterDefaultText.setAccessible(true);
        utterDefaultText.set(utterDefault, "我不明白您说的话");
        Response utterChitchat = new Response();
        Field utterChitchatText = utterChitchat.getClass().getDeclaredField("text");
        utterChitchatText.setAccessible(true);
        utterChitchatText.set(utterChitchat, "你好，我是Rasa，一个智能对话机器人。");

        Form form = new Form();
        List<String> requiredSlots = new ArrayList<String>(){{
            add("address");
            add("date");
        }};
        Field requiredSlotsField = form.getClass().getDeclaredField("requiredSlots");
        requiredSlotsField.setAccessible(true);
        requiredSlotsField.set(form, requiredSlots);
        List<String> ignoredIntents = new ArrayList<String>(){{
            add("goodbye");
        }};
        Field ignoredIntentsField = form.getClass().getDeclaredField("ignoredIntents");
        ignoredIntentsField.setAccessible(true);
        ignoredIntentsField.set(form, ignoredIntents);

        Assertions.assertEquals(new ArrayList<Intent>(){{
            add(chitchat);
            add(deny);
            add(goodbye);
            add(greet);
        }}, domain.getIntents());
        Assertions.assertEquals(new ArrayList<String>(){{
            add("address");
            add("date");
        }}, domain.getEntities());
        Assertions.assertEquals(new HashMap<String, Slot>(){{
            put("address", address);
            put("date", date);
        }}, domain.getSlots());
        Assertions.assertEquals(new HashMap<String, List<Response>>(){{
            put("utter_greet", new ArrayList<Response>(){{add(utterGreet);}});
            put("utter_goodbye", new ArrayList<Response>(){{add(utterGoodbye);}});
            put("utter_default", new ArrayList<Response>(){{add(utterDefault);}});
            put("utter_chitchat/whoyouare", new ArrayList<Response>(){{add(utterChitchat);}});
        }}, domain.getResponses());
        Assertions.assertEquals(new ArrayList<String>(){{
            add("utter_goodbye");
            add("utter_greet");
            add("utter_default");
            add("utter_chitchat");
        }}, domain.getActions());
        Assertions.assertEquals(new HashMap<String, Form>(){{
            put("ask_form", form);
        }}, domain.getForms());
    }
}
