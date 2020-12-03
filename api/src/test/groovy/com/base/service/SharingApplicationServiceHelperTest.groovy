package com.base.service

import spock.lang.Specification

class SharingApplicationServiceHelperTest extends Specification {

    def "생성한 토큰의 자리수는 3이 되어야 한다."(){
        when:
        def token = SharingApplicationServiceHelper.generate3LengthToken()
        then:
        token.size() == 3
        println(token)
    }
}
