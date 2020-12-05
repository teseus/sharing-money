package com.base.repository

import com.base.entity.Sharing
import com.base.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@SpringBootTest
class SharingRepositoryTest extends Specification {
    @Autowired
    SharingRepository sharingRepository
    @Autowired
    UserRepository userRepository

    @Transactional
    def "뿌리기에 저장하고 저장한 것을 찾을 수 있어야 한다."() {
        given:
        def newUser = userRepository.save(new User(123))
        when:
        def sharing = sharingRepository.save(Sharing.builder()
                .roomId("123")
                .totalAmount(10000)
                .user(newUser)
                .build())

        def foundSharing = sharingRepository.findById(sharing.getId())
        then:
        foundSharing.isPresent()
        foundSharing.get().with {
            sharing == it
            newUser == getUser()
        }
    }
}
