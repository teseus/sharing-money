package com.base.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Base60 {
    private static final String RANDOM_NUMERIC_ALPHABET = "5tmABTnJuLv4w9Hqck1DWU70FrZgbR3VlM2OaozxQPSyKiseYEIXf6GhpdjC";

    private static final long BASE = RANDOM_NUMERIC_ALPHABET.length();
    public static final int MAXIMUM_VALUE = 216000;

    public static String fromBase10(long i) {
        long modular = i % MAXIMUM_VALUE;
        StringBuilder sb = new StringBuilder();
        if (modular == 0) {
            return String.valueOf(RANDOM_NUMERIC_ALPHABET.charAt(0));
        }
        while (modular > 0) {
            modular = fromBase10(modular, sb);
        }
        return sb.reverse().toString();
    }

    private static long fromBase10(long i, final StringBuilder sb) {
        int rem = Long.valueOf(i % BASE).intValue();
        sb.append(RANDOM_NUMERIC_ALPHABET.charAt(rem));
        return i / BASE;
    }

    public static int toBase10(String str) {
        return toBase10(new StringBuilder(str).reverse().toString().toCharArray());
    }

    private static int toBase10(char[] chars) {
        int n = 0;
        for (int i = chars.length - 1; i >= 0; i--) {
            n += toBase10(RANDOM_NUMERIC_ALPHABET.indexOf(chars[i]), i);
        }
        return n;
    }

    private static int toBase10(int n, int pow) {
        return n * (int) Math.pow(BASE, pow);
    }
}