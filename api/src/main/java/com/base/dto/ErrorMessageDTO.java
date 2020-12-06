package com.base.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

@NoArgsConstructor
public class ErrorMessageDTO {
    @JsonValue
    private final Map<String, String> messageMap = new LinkedHashMap<>();

    public void addMessage(String key, String value) {
        this.messageMap.put(key, value);
    }
}
