package com.base.repository

import com.base.entity.Account
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class AccountRepositoryTest extends Specification {
    @Autowired
    AccountRepository accountRepository

    def "Account DB 저장 테스트"() {
        given:
        def account = new Account(1, 10000)
        when:
        accountRepository.save(account)
        def result = accountRepository.findByAccountId(1L)
        then:
        result.isPresent()
        result.get().amount == 10000
    }
}
