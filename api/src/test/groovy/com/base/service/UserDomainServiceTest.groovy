package com.base.service


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class UserDomainServiceTest extends Specification {
    public static final int USER_ID = 123
    @Autowired
    UserDomainService userDomainService

    def "유저가 없으면 생성하고 있으면 있는 것을 갖고 온다."() {
        when:
        def user = userDomainService.getUser(USER_ID)
        then:
        user != null
    }
}
