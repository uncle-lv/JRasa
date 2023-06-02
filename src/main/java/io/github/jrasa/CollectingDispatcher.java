package io.github.jrasa;

import io.github.jrasa.message.Message;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Send messages back to user
 *
 * @author uncle-lv
 */
@Getter
public class CollectingDispatcher {
    private List<Message> messages;

    public void utterMessage(Message message) {
        if (null == this.messages) {
            this.messages = new ArrayList<>(1);
        }
        this.messages.add(message);
    }
}
