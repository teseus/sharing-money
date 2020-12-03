package com.base.repository

import com.base.entity.Sharing
import com.base.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@SpringBootTest
class SharingRepositoryTest extends Specification {
    public static final long USER_ID = 123L
    public static final String TOKEN = "ABC"
    @Autowired
    SharingRepository sharingRepository
    @Autowired
    UserRepository userRepository

    @Transactional
    def "뿌리기테이블을 저장하고 찾을 수 있어야 한다."(){
        when: "유저를 저장하고 저장한 유저를 찾을 수 있어야 한다."
        def user = userRepository.save(new User(USER_ID))
        def foundUser = userRepository.findById(USER_ID)
        then:
        foundUser.isPresent()
        assert user == foundUser.get()

        and:
        when: "뿌리기에 저장하고 저장한 것을 찾을 수 있어야 한다."
        def sharing = sharingRepository.save(new Sharing(TOKEN, 10000L))
        def foundSharing = sharingRepository.findById(TOKEN).get()
        then:
        sharing == foundSharing

    }
}
