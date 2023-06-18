# JRasa
![stars](https://img.shields.io/github/stars/uncle-lv/JRasa?style=plastic)  ![issues](https://img.shields.io/github/issues/uncle-lv/JRasa?style=plastic) ![forks](https://img.shields.io/github/forks/uncle-lv/JRasa?style=plastic) ![license](https://img.shields.io/github/license/uncle-lv/JRasa?style=plastic) ![JDK8](https://img.shields.io/badge/JDK-8-important)  ![Rasa](https://img.shields.io/badge/Rasa-3.x-%235b13ec)

[English](https://github.com/uncle-lv/JRasa/blob/main/README.md) | [中文](https://github.com/uncle-lv/JRasa/blob/main/README_zh.md)

一个Java版的[Rasa SDK](https://rasa.com/docs/rasa/action-server)



## Maven

```xml
<dependency>
  <groupId>io.github</groupId>
  <artifactId>jrasa</artifactId>
  <version>0.1.0-SNAPSHOT</version>
</dependency>
```



## 兼容性

Rasa: >=3.5.1



## 使用方式

在使用之前，请先阅读[Rasa SDK文档](https://rasa.com/docs/rasa/action-server)，了解Rasa SDK中的基本概念。



### 运行Rasa SDK Action Server

你可以使用你喜欢的Java web框架来运行Rasa SDK Action Server。 这是一个[SpringBoot](https://github.com/uncle-lv/JRasa/tree/main/src/test/java/server)的示例。



### 编写自定义Actions

#### Actions

你可以通过实现 [Action](https://github.com/uncle-lv/JRasa/blob/main/src/main/java/io/github/jrasa/Action.java)接口来定义一个自定义action。

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

💡 如果没有事件可供返回，你可以使用`Action.empty()`返回一个空列表。



#### Tracker

##### getSlot

因为Java是静态编程语言，所以你必须指定你要获取的slot的值的类型。

目前，有五个方法可以用来获取slot的值：

- `Object getSlot(String key)`
- `<T> T getSlot(String key, Class<T> type)`
- `String getStringSlot(String key)`
- `Boolean getBoolSlot(String key)`
- `Double getDoubleSlot(String key)`



`Object getSlot(String key)` 可以获取任意类型的slot值。

你可以使用`<T> T getSlot(String key, Class<T> type)`去获取指定type的slot值。

`String getStringSlot(String key)` `Boolean getBoolSlot(String key)` `Double getDoubleSlot(String key)` 用于获取常见类型的slot值。

💡 JSON中的小数在反序列时，会被转化为Java中的double类型，所以这里使用了Double而不是Float。



#### Dispatcher

因为Java中的方法不支持默认参数，所以这里使用了[Message](https://github.com/uncle-lv/JRasa/blob/main/src/main/java/io/github/jrasa/message/Message.java)类来表示消息响应。

你可以使用建造者模式来构建一个`Message`实例：

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

然后，使用 `utterMessage`方法发送消息:

```java
dispatcher.utterMessage(message);
```



#### Events

所有事件都是抽象类[Event](https://github.com/uncle-lv/JRasa/blob/main/src/main/java/io/github/jrasa/event/Event.java)的子类。事件的属性和官方文档中的相同。一些有较多属性的事件需要用建造者模式构造。



##### SlotSet

```java
SlotSet SlotSet = new SlotSet("name", "Mary");
```



##### AllSlotsReset

```java
AllSlotsReset allSlotsReset = new AllSlotsReset();
```



##### ReminderScheduled

```java
ReminderScheduled reminderScheduled = ReminderScheduled.builder("EXTERNAL_dry_plant")
        .name("remind_water_plants")
        .entities(new ArrayList<Entity>(){{
            add(Entity.builder().entity("plant", "orchid").build());
        }})
      .triggerDateTime(LocalDateTime.parse("2018-09-03T11:41:10.128172", DateTimeFormatter.ISO_LOCAL_DATE_TIME))
        .build();
```



##### ReminderCancelled

```java
ReminderCancelled reminderCancelled = ReminderCancelled.builder()
                .name("remind_water_plants")
                .build();
```



##### ConversationPaused

```java
ConversationPaused conversationPaused = new ConversationPaused();
```



##### ConversationResumed

```java
ConversationResumed conversationResumed = new ConversationResumed();
```



##### FollowupAction

```java
FollowupAction followupAction = new FollowupAction("action_say_goodbye");
```



##### UserUtteranceReverted

```java
UserUtteranceReverted userUtteranceReverted = new UserUtteranceReverted();
```



##### ActionReverted

```java
ActionReverted actionReverted = new ActionReverted();
```



##### Restarted

```java
Restarted restarted = new Restarted();
```



##### SessionStarted

```java
SessionStarted sessionStarted = new SessionStarted();
```



##### UserUttered

```java
UserUttered userUttered = UserUttered.builder()
                .text("Hello bot")
                .build();
```



##### BotUttered

```java
BotUttered botUttered = BotUttered.builder()
                .text("Hello user")
                .build();
```



##### ActionExecuted

```java
ActionExecuted actionExecuted = ActionExecuted.builder("action_greet_user")
                .build();
```



#### Special Action Types

##### Knowledge Base Actions

🛠️ 尚未实现。



##### Slot Validation Actions

Slot Validation Actions与官方SDK只有一点不同。

在官方SDK中，方法/函数被命名为`validate_<slot_name>`/`extract_<slot name>`（下划线命名）。在JRasa中，它们应该遵循Java命名规范，被命名为`validate<SlotName>`/`extract<SlotName>`（驼峰命名）。



## Contributions

感谢您的任何反馈。



## License

[Apache-2.0](https://github.com/uncle-lv/JRasa/blob/main/LICENSE)



## 鸣谢

- [Rasa](https://github.com/RasaHQ/rasa)
