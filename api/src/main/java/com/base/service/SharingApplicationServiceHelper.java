package com.base.service;

import java.util.Random;

public class SharingApplicationServiceHelper {
    private static final int TOKEN_SIZE = 3;
    private static final int ASCII_ZERO = 48;
    private static final int ASCII_SMALL_Z = 122;

    public static String generate3LengthToken() {
        return new Random().ints(ASCII_ZERO, ASCII_SMALL_Z + 1)
                .filter(SharingApplicationServiceHelper::isAlphabetOrNumer)
                .limit(TOKEN_SIZE)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private static boolean isAlphabetOrNumer(int i) {
        return (isUnderMaxNumeric(i) || isOverA(i)) && (isUnderZ(i) || isOverLowerA(i));
    }

    private static boolean isOverLowerA(int i) {
        return i >= 97;
    }

    private static boolean isUnderZ(int i) {
        return i <= 90;
    }

    private static boolean isOverA(int i) {
        return i >= 65;
    }

    private static boolean isUnderMaxNumeric(int i) {
        return i <= 57;
    }
}
