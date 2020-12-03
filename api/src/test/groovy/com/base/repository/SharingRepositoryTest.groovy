package com.base.repository


import com.base.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@SpringBootTest
class SharingRepositoryTest extends Specification {
    @Autowired
    UserRepository userRepository

    @Transactional
    def "뿌리기테이블을 저장하고 찾을 수 있어야 한다."(){
        when:
        def user = userRepository.save(new User(123L))
        def foundUser = userRepository.findById(123L)
        then:
        foundUser.isPresent()
        assert user == foundUser.get()
    }
}
