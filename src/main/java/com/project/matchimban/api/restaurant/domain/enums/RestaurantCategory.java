package com.project.matchimban.api.restaurant.domain.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RestaurantCategory {
    KOREA, JAPAN, CHINA
//    KOREA("한식");
//
//    private final String value;
//
//    @JsonCreator
//    public static RestaurantCategory from(String value) {
//        for (RestaurantCategory category : RestaurantCategory.values()) {
//            if (category.getValue().equals(value))
//                return category;
//        }
//        return null;
//    }
//
//    @JsonValue
//    public String getValue() {
//        return value;
//    }
}
