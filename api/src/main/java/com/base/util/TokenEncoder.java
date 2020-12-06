package com.base.util;

import org.apache.commons.lang3.StringUtils;

public class TokenEncoder {

    public static final int TOKEN_LENGTH = 3;

    public static String encode(long num) {
        return fillEmptySpace(Base60.fromBase10(num));
    }

    private static String fillEmptySpace(String encoded) {
        return StringUtils.overlay(RandomCharacter.generate3LengthToken(), encoded, TOKEN_LENGTH - encoded.length(), TOKEN_LENGTH);
    }
}
