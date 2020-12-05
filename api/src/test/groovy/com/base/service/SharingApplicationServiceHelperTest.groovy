package com.base.service

import spock.lang.Specification

class SharingApplicationServiceHelperTest extends Specification {
    public static final int TOTAL_AMOUNT = 10000000

    def "액수를 인원수로 나눈 것들의 합이 원래의 금액이 되어야 한다."() {
        when:
        List<Long> separated = SharingApplicationServiceHelper.separateMoney(TOTAL_AMOUNT, 3)
        then:
        separated.sum() == TOTAL_AMOUNT
    }
}
