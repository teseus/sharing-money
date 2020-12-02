package com.base.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.netty.util.internal.StringUtil;

public record GreetingDTO(@JsonProperty("greet") String greet){
    public static final String GREET_PREFIX = "Hello";

    static public GreetingDTO ofName(String name){
        return new GreetingDTO(GREET_PREFIX + StringUtil.SPACE + name);
    }
}

