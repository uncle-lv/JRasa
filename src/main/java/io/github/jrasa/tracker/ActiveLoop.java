package io.github.jrasa.tracker;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.jrasa.common.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class ActiveLoop {
    private String name;

    @JsonIgnore
    public boolean isActive() {
        return !StringUtils.isNullOrEmpty(this.name);
    }
}

