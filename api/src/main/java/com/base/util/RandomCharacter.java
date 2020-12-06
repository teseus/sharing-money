package com.base.util;

import java.util.Random;

public class RandomCharacter {
    private static final int TOKEN_SIZE = 3;
    private static final String SPECIAL_CHAR = "5N8B";

    public static String generate3LengthToken() {
        return new Random().ints(0, SPECIAL_CHAR.length())
                .limit(TOKEN_SIZE)
                .collect(StringBuilder::new, (x, y) -> x.append(SPECIAL_CHAR.charAt(y)), StringBuilder::append)
                .toString();
    }
}

