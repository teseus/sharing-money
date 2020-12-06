package com.base.repository

import com.base.entity.Account
import com.base.entity.Sharing
import com.base.entity.User
import com.base.util.TokenEncoder
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
        sharing= sharingRepository.save(Sharing.builder()
                .roomId("ABC")
                .user(userRepository.save(new User(1)))
                .totalAmount(10000)
                .build())
        sharing.changeToken(TokenEncoder.encode(sharing.getId()))
        sharingRepository.save(sharing)
    }

    @Transactional
    def "뿌리기 테이블 id로 할당되지 않은 Account 들 찾을 수 있어야한다."() {
        given:
        def targets = [Account.builder().amount(3333).sharing(sharing).user(Optional.empty()).build(),
                       Account.builder().amount(3333).sharing(sharing).user(Optional.empty()).build(),
                       Account.builder().amount(3334).sharing(sharing).user(Optional.empty()).build()]

        accountRepository.saveAll(targets)
        when:
        def accounts = accountRepository.findAccountByTokenAndRoomId(sharing.getToken(), sharing.getRoomId())
        then:
        accounts.size() == 3
    }

    @Transactional
    def "유저 없이 Account 를 저장하고 찾을 수 있어야 한다."() {
        given:
        def account = Account.builder().amount(3333).sharing(sharing).user(Optional.empty()).build()
        when:
        def savedAccount = accountRepository.save(account)
        def result = accountRepository.findById(savedAccount.getAccountId())
        then:
        result.isPresent()
        result.get().with {
            getAmount() == 10000
            user == null
            sharing.with{
                roomId == "SuiteRoom"
            }
        }
    }

    @Transactional
    def "유저 포함하여 Account 를 저장하고 찾을 수 있어야 한다."(){
        given:
        def user = userRepository.save(new User(1L))
        def account = Account.builder().amount(3333).sharing(sharing).user(Optional.of(user)).build()
        accountRepository.save(account)
        when:
        def result = accountRepository.findAccountByUserAndTokenAndRoomId(user, sharing.getToken(),sharing.getRoomId())
        then:
        result.size() > 0
        result.get(0).with {
            amount == 10000
            user != null
            sharing.with{
                roomId == "SuiteRoom"
            }
        }
    }
}
