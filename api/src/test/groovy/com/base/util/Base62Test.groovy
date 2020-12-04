package com.base.util

import spock.lang.Specification

class Base62Test extends Specification {
    def "test1"() {
        expect:
        for (i in 0..<238327) {
            def encoded = Base62.fromBase10(i)
            print("${i} encoded : ${encoded}, ")
            def decoded = Base62.toBase10(encoded)
            println("decoded : ${ decoded}")
            assert i == decoded
        }
    }

    def "test2"() {
        expect:
        Base62.toBase10("555") == Base62.toBase10("5")
        Base62.toBase10("55A") == Base62.toBase10("A")
        Base62.toBase10("5ZB") == Base62.toBase10("ZB")
    }
}
