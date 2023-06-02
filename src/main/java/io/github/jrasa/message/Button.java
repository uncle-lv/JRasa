package io.github.jrasa.message;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Button {
    private String title;
    private String payload;

    public Button(String title, String payload) {
        this.title = title;
        this.payload = payload;
    }

    private Button() {}
}
