package com.base.util


import spock.lang.Specification

class RandomCharacterTest extends Specification {
    def "3자리의 알파벳 숫자 조합 토큰값을 생성한다"(){
        when:
        def token = RandomCharacter.generate3LengthToken()
        then:
        token.size() == 3
        println(token)
    }
}


