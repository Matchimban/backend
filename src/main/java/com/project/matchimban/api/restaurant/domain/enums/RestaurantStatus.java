package com.project.matchimban.api.restaurant.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RestaurantStatus {
    UNAUTHORIZED("unauthorized"),
    PUBLISHED("published"),
    INVISIBLE("invisible"),
    DELETED("deleted");

    private final String value;

    RestaurantStatus(String value) {
        this.value = value;
    }

    @JsonCreator
    public static RestaurantStatus from(String value) {
        for (RestaurantStatus status : RestaurantStatus.values()) {
            if (status.getValue().equals(value))
                return status;
        }
        return null;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
