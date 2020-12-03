package com.base.repository

import com.base.entity.Account
import com.base.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@SpringBootTest
class AccountRepositoryTest extends Specification {
    @Autowired
    AccountRepository accountRepository
    @Autowired
    UserRepository userRepository

    def "유저 없이 Account를 저장하고 찾을 수 있어야 한다."() {
        given:
        def account = new Account(1, 10000, null)
        when:
        accountRepository.save(account)
        def result = accountRepository.findByAccountId(1L)
        then:
        result.isPresent()
        result.get().with {
            amount == 10000
            user == null
        }
    }

    @Transactional
    def "유저 포함 Account를 저장하고 찾을 수 있어야 한다."(){
        given:
        def user = userRepository.save(new User(1L))
        def account = new Account(1, 10000, user)
        when:
        accountRepository.save(account)
        def result = accountRepository.findByAccountId(1L)
        then:
        result.isPresent()
        result.get().with {
            amount == 10000
            user != null
        }
    }
}
