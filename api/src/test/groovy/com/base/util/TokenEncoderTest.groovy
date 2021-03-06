package com.base.util

import org.apache.commons.lang3.StringUtils
import spock.lang.Specification

class TokenEncoderTest extends Specification {
    def "token은 3자리 문자열로 구성되며 예측이 불가능해야 한다."() {
        expect:
        for (i in [0, 10, 100, 238327, Base60.MAXIMUM_VALUE, Base60.MAXIMUM_VALUE+2, Base60.MAXIMUM_VALUE*2+100]) {
            String encoded = TokenEncoder.encode(i)
            encoded.size() == 3
            println("encoded : ${encoded}, i: ${i}")
        }
    }

    def "3자리를 채웠어도 실제 값은 같아야 한다."() {
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
