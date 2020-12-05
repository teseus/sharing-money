package com.base.util;

public class Base62 {

    private static final String ALPHABET = "5N8mABTtnJuLv4w9Hqck1DWU70FrZgbR3VlM2OaozxQPSyKiseYEIXf6GhpdjC";

    private static final int BASE = ALPHABET.length();

    private Base62() {}

    public static String fromBase10(int i) {
        StringBuilder sb = new StringBuilder("");
        if (i == 0) {
            return String.valueOf(ALPHABET.charAt(0));
        }
        while (i > 0) {
            i = fromBase10(i, sb);
        }
        return sb.reverse().toString();
    }

    private static int fromBase10(int i, final StringBuilder sb) {
        int rem = i % BASE;
        sb.append(ALPHABET.charAt(rem));
        return i / BASE;
    }

    public static int toBase10(String str) {
        return toBase10(new StringBuilder(str).reverse().toString().toCharArray());
    }

    private static int toBase10(char[] chars) {
        int n = 0;
        for (int i = chars.length - 1; i >= 0; i--) {
            n += toBase10(ALPHABET.indexOf(chars[i]), i);
        }
        return n;
    }

    private static int toBase10(int n, int pow) {
        return n * (int) Math.pow(BASE, pow);
    }
}