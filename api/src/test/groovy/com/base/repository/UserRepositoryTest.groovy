package com.base.repository

import com.base.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@SpringBootTest
class UserRepositoryTest extends Specification {
    public static final long USER_ID = 123L

    @Autowired
    UserRepository userRepository

    @Transactional
    def "유저를 저장하고 저장한 유저를 찾을 수 있어야 한다."() {
        when:
        def user = userRepository.save(new User(USER_ID))
        def foundUser = userRepository.findById(USER_ID)
        then:
        foundUser.isPresent()
        user == foundUser.get()
    }
}
