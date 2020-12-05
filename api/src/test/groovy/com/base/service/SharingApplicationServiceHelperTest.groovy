package com.base.service

import spock.lang.Specification

class SharingApplicationServiceHelperTest extends Specification {
    public static final int TOTAL_AMOUNT = 10000000

    def "생성한 토큰의 자리수는 3이 되어야 한다."(){
        when:
        def token = SharingApplicationServiceHelper.generate3LengthToken()
        then:
        token.size() == 3
        println(token)
    }

    def "액수를 인원수로 나눈 것들의 합이 원래의 금액이 되어야 한다."() {
        when:
        List<Long> separated = SharingApplicationServiceHelper.separate(TOTAL_AMOUNT, 3)
        then:
        separated.sum() == TOTAL_AMOUNT
    }
}
