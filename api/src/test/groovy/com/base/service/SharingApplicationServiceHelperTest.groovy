package com.base.service

import spock.lang.Specification

class SharingApplicationServiceHelperTest extends Specification {

    def "3자리의 알파벳 숫자 조합 토큰값을 생성한다"(){
        when:
        def token = SharingApplicationServiceHelper.generate3LengthToken()
        then:
        token.size() == 3
        println(token)
    }
}
