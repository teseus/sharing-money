package com.base.util;

import org.apache.commons.lang3.StringUtils;

public class TokenEncoder {

    public static final String FILLING_LETTERS = "555";
    public static final int TOKEN_LENGTH = 3;

    public static String encode(long num) {
        return fillEmptySpace(Base62.fromBase10(num));
    }

    private static String fillEmptySpace(String encoded) {
        return StringUtils.overlay(FILLING_LETTERS, encoded, TOKEN_LENGTH - encoded.length(), TOKEN_LENGTH);
    }

    public static int decode(String encoded) {
        return Base62.toBase10(encoded);
    }
}
