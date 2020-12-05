package com.base.util;

import org.apache.commons.lang3.StringUtils;

public class Encoder {
    public static String encode(long num) {
        String encoded = Base62.fromBase10(num);
        return StringUtils.overlay("555", encoded, 3 - encoded.length(), 3);
    }

    public static int decode(String encoded) {
        return Base62.toBase10(encoded);
    }
}
