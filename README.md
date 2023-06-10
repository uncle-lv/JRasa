# JRasa
![stars](https://img.shields.io/github/stars/uncle-lv/JRasa?style=plastic)  ![issues](https://img.shields.io/github/issues/uncle-lv/JRasa?style=plastic) ![forks](https://img.shields.io/github/forks/uncle-lv/JRasa?style=plastic) ![license](https://img.shields.io/github/license/uncle-lv/JRasa?style=plastic) ![JDK8](https://img.shields.io/badge/JDK-8-important)

A Java SDK for [Rasa action server](https://rasa.com/docs/rasa/action-server)



## Maven

```xml
<dependency>
  <groupId>io.github</groupId>
  <artifactId>jrasa</artifactId>
  <version>0.1.0-SNAPSHOT</version>
</dependency>
```



## Usage

I don't repeat the concepts about the Rasa SDK. So you should read [the Rasa SDK documentation](https://rasa.com/docs/rasa/action-server) first.

### Running a Rasa SDK Action Server

You can run a Rasa SDK Action Server with the Java web framework you like. There is [a SpringBoot demo](https://github.com/uncle-lv/JRasa/tree/main/src/test/java/server) for you.



### Writing Custom Actions

#### Actions

 To define a custom action, create a class which implements the interface [Action](https://github.com/uncle-lv/JRasa/blob/main/src/main/java/io/github/jrasa/Action.java). 

```java
import io.github.jrasa.Action;
import io.github.jrasa.CollectingDispatcher;
import io.github.jrasa.domain.Domain;
import io.github.jrasa.event.Event;
import io.github.jrasa.exception.RejectExecuteException;
import io.github.jrasa.tracker.Tracker;

import java.util.List;

public class CustomAction implements Action {
    @Override
    public String name() {
        return "action_name";
    }

    @Override
    public List<? extends Event> run(CollectingDispatcher collectingDispatcher, Tracker tracker, Domain domain) throws RejectExecuteException {
        return Action.empty();
    }
}
```

💡 If no events need to be returned, you can use `Action.empty()` to return an empty list.



#### Tracker

##### getSlot

Because Java is a static programming language, you have to assign the type when you get a slot value.

There is five methods to get a slot value:

- `Object getSlot(String key)`
- `<T> T getSlot(String key, Class<T> type)`
- `String getStringSlot(String key)`
- `Boolean getBoolSlot(String key)`
- `Double getDoubleSlot(String key)`



`Object getSlot(String key)` can get a slot value of any type.

With `<T> T getSlot(String key, Class<T> type)`, you can assign the type of slot value.

`String getStringSlot(String key)` `Boolean getBoolSlot(String key)` `Double getDoubleSlot(String key)` can get the common type of slot value.

💡 Decimal numbers in JSON are deserialized to double in Java, so double is used instead of float.



#### Dispatcher

There is a [Message](https://github.com/uncle-lv/JRasa/blob/main/src/main/java/io/github/jrasa/message/Message.java) class to represent responses, because methods have no default value parameters in Java.

You can build a `Message` instance with `Builder` like this:

```java
Message message = Message.builder()
                .text("Hello")
                .image("https://i.imgur.com/nGF1K8f.jpg")
                .response("utter_greet")
                .attachment("")
                .kwargs(new HashMap<String, Object>(){{
                    put("name", "uncle-lv");
                }})
                .build();
```

And then send it with `utterMessage`:

```java
dispatcher.utterMessage(message);
```



#### Events

All events are subclasses of abstract class [Event](https://github.com/uncle-lv/JRasa/blob/main/src/main/java/io/github/jrasa/event/Event.java). Their properties are the same as in the documentation. Some of them which have too many  properties should been build with Builder.



#### Special Action Types

##### Knowledge Base Actions

Not implemented yet.



##### Slot Validation Actions

There is only one difference from the official SDK.

The methods/functions are named `validate_<slot_name>`/`extract_<slot name>`(snake case) in the official SDK. In JRasa, they should be named `validate<SlotName>`/`extract<SlotName>`(camel case), just like convention in Java.



## Contributions

Thank you for any feedback.



## License

[Apache-2.0](https://github.com/uncle-lv/JRasa/blob/main/LICENSE)



## Thanks

- [Rasa](https://github.com/RasaHQ/rasa)
