package io.github.jrasa.domain;

import io.github.jrasa.message.Button;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@EqualsAndHashCode
@ToString
public class Response {
    private String text;
    private String channel;
    private String image;
    private List<Button> buttons;
    private Map<String, Object> custom;
}
