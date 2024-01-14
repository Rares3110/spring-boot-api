package com.rares.articlehub.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CommentVisibility {
    VISIBLE, HIDDEN;

    @JsonCreator
    public static CommentVisibility fromString(String value) {
        return valueOf(value.toUpperCase());
    }
}
