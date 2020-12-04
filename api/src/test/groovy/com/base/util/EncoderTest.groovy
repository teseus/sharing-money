package com.base.util

import org.apache.commons.lang3.StringUtils
import spock.lang.Specification

class EncoderTest extends Specification {
    def "encode test"() {
        expect:
        for (i in 0..<238327) {
            String encoded = Encoder.encode(i)
            encoded.size() == 3
            int result = Encoder.decode(encoded)
            println("encoded : ${encoded}, result: ${result}, i: ${i}")
            assert result == i
        }
    }

    def "test1"() {
        expect:
        def a = "A"
        StringUtils.overlay("555", a, 3 - a.length(), 3) == "55A"
        def b = "AB"
        StringUtils.overlay("555", b, 3 - b.length(), 3) == "5AB"
        def c = "ABC"
        StringUtils.overlay("555", c, 3 - c.length(), 3) == "ABC"
        def d = "5"
        StringUtils.overlay("555", d, 3 - d.length(), 3) == "555"
    }
}
