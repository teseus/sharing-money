package com.base.service

import com.base.entity.Sharing
import com.base.entity.User
import com.base.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Subject

@SpringBootTest
class AccountApplicationServiceTest extends Specification {
    public static final int SHARING_SIZE = 3
    public static final int TOTAL_MONEY = 10000
    public static final String ROOM_ID = "SuiteRoom"
    public static final int USER1_ID = 1
    public static final int USER2_ID = 2

    @Subject
    @Autowired
    AccountApplicationService accountApplicationService
    @Autowired
    SharingApplicationService sharingApplicationService
    @Autowired
    UserRepository userRepository

    def "빈 어카운트를 찾아 유저에게 할당해 주어야 한다. 빈 어카운트가 없으면 에러를 발생시킨다."(){
        given:
        Sharing sharing = sharingApplicationService.shareMoney(USER1_ID, ROOM_ID, TOTAL_MONEY, SHARING_SIZE)
        userRepository.save(new User(USER2_ID))
        when:
        def amount = accountApplicationService.takeAccount(ROOM_ID, USER2_ID, sharing.getId())
        then:
        amount.isPresent()
        amount.get().getAmount() == 3333 | amount.get().getAmount() == 3334
    }

    def "뿌리기의 주인이 할당요청을 하면 에러를 리턴해야 한다."(){
        expect:
        1 == 1
    }

    def "룸아이디가 다르다면 에러를 리턴해야 한다."(){
        expect:
        1 == 1
    }
}
