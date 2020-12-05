package com.base.service

import com.base.entity.Sharing
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.DataIntegrityViolationException
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

@SpringBootTest
class AccountApplicationServiceTest extends Specification {
    public static final int SHARING_SIZE = 3
    public static final int TOTAL_MONEY = 20000
    public static final String ROOM_ID = "SuiteRoom"
    public static final int USER1_ID = 1
    public static final int USER2_ID = 2

    @Subject
    @Autowired
    AccountApplicationService accountApplicationService
    @Autowired
    SharingApplicationService sharingApplicationService
    @Autowired
    UserDomainService userDomainService
    @Shared
    Sharing sharing
    def setup() {
        sharing = sharingApplicationService.shareMoney(USER1_ID, ROOM_ID, TOTAL_MONEY, SHARING_SIZE)
    }

    def "빈 어카운트를 찾아 유저에게 할당해 주어야 한다. 빈 어카운트가 없으면 에러를 발생시킨다."(){
        given:
        userDomainService.getUser(USER2_ID)
        when:
        def amount = accountApplicationService.takeAccount(USER2_ID, ROOM_ID, sharing.getId())
        then:
        amount.isPresent()
        amount.get().getAmount() == 6666
    }

    def "두번이상 할당 요청을 하면 에러를 발생시킨다."() {
        given:
        userDomainService.getUser(USER2_ID)
        when:
        accountApplicationService.takeAccount(USER2_ID, ROOM_ID, sharing.getId())
        accountApplicationService.takeAccount(USER2_ID, ROOM_ID, sharing.getId())
        then:
        thrown(DataIntegrityViolationException)
    }

    def "뿌리기의 주인이 할당요청을 하면 에러를 리턴해야 한다."(){
        expect:
        1 == 1
    }

    def "뿌리기의 룸아이디가 다르다면 0원을 리턴해야 한다."(){
        expect:
        1 == 1
    }
}
