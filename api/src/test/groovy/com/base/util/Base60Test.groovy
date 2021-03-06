package com.base.util

import spock.lang.Specification

class Base60Test extends Specification {
    def "숫자를 3자리의 토큰으로 변환하고 다시 재변환한 숫자가 같아야 한다."() {
        expect:
        for (i in [0, 10, 100, Base60.MAXIMUM_VALUE-1]) {
            def encoded = Base60.fromBase10(i)
            print("${i} encoded : ${encoded}, ")
            def decoded = Base60.toBase10(encoded)
            println("decoded : ${ decoded}")
            assert i == decoded
        }
    }

    def "최대 범위를 넘어간 경우 modular 연산을 적용한 나머지 값이 나와야 한다."() {
        expect:
        for (i in [Base60.MAXIMUM_VALUE+2, Base60.MAXIMUM_VALUE*2+100]) {
            def encoded = Base60.fromBase10(i)
            print("${i} encoded : ${encoded}, ")
            def decoded = Base60.toBase10(encoded)
            println("decoded : ${ decoded}")
            assert i%(Base60.MAXIMUM_VALUE) == decoded
        }
    }
}
