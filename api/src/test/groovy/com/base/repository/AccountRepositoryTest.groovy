package com.base.repository

import com.base.entity.Account
import com.base.entity.Sharing
import com.base.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest
class AccountRepositoryTest extends Specification {
    @Autowired
    AccountRepository accountRepository
    @Autowired
    UserRepository userRepository
    @Autowired
    SharingRepository sharingRepository
    @Shared
    def sharing

    def setup() {
        sharing = sharingRepository.save(new Sharing("ABC", "SuiteRoom", 10000L))
    }

    @Transactional
    def "Token 으로 Account 들을 찾을 수 있어야한다."() {
        given:
        def targets = [new Account(1, 3333, sharing, null),
                       new Account(2, 3333, sharing, null),
                       new Account(3, 3334, sharing, null)]

        accountRepository.saveAll(targets)
        when:
        def accounts = accountRepository.findAccountBySharingToken("ABC")
        then:
        accounts.size() == 3
    }

    @Transactional
    def "유저 없이 Account 를 저장하고 찾을 수 있어야 한다."() {
        given:
        def account = new Account(1, 10000, sharing, null)
        when:
        accountRepository.save(account)
        def result = accountRepository.findByAccountId(1L)
        then:
        result.isPresent()
        result.get().with {
            getAmount() == 10000
            user == null
            sharing.with{
                token == "ABC"
                roomId == "SuiteRoom"
            }
        }
    }

    @Transactional
    def "유저 포함 Account 를 저장하고 찾을 수 있어야 한다."(){
        given:
        def user = userRepository.save(new User(1L))
        def account = new Account(1, 10000, sharing, user)
        when:
        accountRepository.save(account)
        def result = accountRepository.findByAccountId(1L)
        then:
        result.isPresent()
        result.get().with {
            amount == 10000
            user != null
            sharing.with{
                token == "ABC"
                roomId == "SuiteRoom"
            }
        }
    }
}
