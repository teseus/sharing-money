package com.base.repository

import com.base.entity.Sharing
import com.base.entity.User
import com.base.util.TokenEncoder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import java.time.LocalDateTime

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
        when: "유저, 룸아이디, 토큰으로 찾을때"
        def sharing = sharingRepository.save(Sharing.builder()
                .roomId("123")
                .totalAmount(10000)
                .user(newUser)
                .build())
        sharing.changeToken(TokenEncoder.encode(sharing.getId()))
        sharingRepository.save(sharing)

        def foundSharing = sharingRepository.findByUserAndRoomIdAndToken(
                sharing.getUser(), sharing.getRoomId(), sharing.getToken())
        then:
        foundSharing.size() == 1
        foundSharing.get(0).with {
            sharing == it
            newUser == getUser()
            token = TokenEncoder.encode(getId())
        }

        when: "유저와 7일이내로 찾을 때"
        foundSharing = sharingRepository.findByUserAndCreatedAtBetween(newUser,
                LocalDateTime.now().minusDays(7), LocalDateTime.now())
        then:
        foundSharing.size() == 1

        when: "유저와 8일 이전으로 찾을때"
       foundSharing = sharingRepository.findByUserAndCreatedAtBetween(newUser,
                LocalDateTime.now().minusDays(8), LocalDateTime.now().minusDays(1))
        then:
        foundSharing.size() == 0
    }
}
