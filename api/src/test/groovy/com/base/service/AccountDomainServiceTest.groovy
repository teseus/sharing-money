package com.base.service

import com.base.entity.Account
import com.base.entity.Sharing
import com.base.entity.User
import com.base.repository.AccountRepository
import com.base.repository.SharingRepository
import com.base.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest
class AccountDomainServiceTest extends Specification {
    @Autowired
    SharingRepository sharingRepository
    @Autowired
    UserRepository userRepository
    @Autowired
    AccountRepository accountRepository
    @Autowired
    AccountDomainService accountDomainService
    @Shared
    def sharing

    def setup() {
        sharing= sharingRepository.save(Sharing.builder()
                .roomId("ABC")
                .user(userRepository.save(new User(1)))
                .totalAmount(10000)
                .build())
    }

    @Transactional
    def "account 를 저장하고, 이 카운트를 서비스에서 읽어 User 를 할당하면 Dirty 의 값이 증가해야 한다."(){
        given:
        def user1 = userRepository.save(new User(1L))
        def user2 = userRepository.save(new User(2L))
        def account = Account.builder().amount(3333).sharing(sharing).user(Optional.empty()).build()
        when:
        def saveAccount = accountRepository.save(account)
        def oldDirty = saveAccount.getDirty()
        then:
        saveAccount.getUser() == null
        oldDirty == 0
        when:
        def allocatedAccount = accountDomainService.allocateTo(saveAccount, user1)
        then:
        allocatedAccount.isPresent() == true
        allocatedAccount.get().with {
            getDirty() == 1
            getDirty() == oldDirty + 1
            getUser() == user1
        }
        when:
        allocatedAccount = accountDomainService.allocateTo(saveAccount, user2)
        then:
        allocatedAccount.isPresent() == false
    }
}
