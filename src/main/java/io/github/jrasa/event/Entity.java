package io.github.jrasa.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Entity {
    private String entity;
    private String value;
    private String role;
    private String group;

    public static Builder builder() {
        return new Builder();
    }

    private Entity() {}

    private Entity(Builder builder) {
        this.entity = builder.entity;
        this.value = builder.value;
        this.role = builder.role;
        this.group = builder.group;
    }

    public static class Builder {
        private String entity;
        private String value;
        private String role;
        private String group;

        public Entity build() {
            return new Entity(this);
        }

        public Builder entity(String entity, String value) {
            this.entity = entity;
            this.value = value;
            return this;
        }

        public Builder role(String role) {
            this.role = role;
            return this;
        }

        public Builder group(String group) {
            this.group = group;
            return this;
        }

        private Builder() {}
    }
}
