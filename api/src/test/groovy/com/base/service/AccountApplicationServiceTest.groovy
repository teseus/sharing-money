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
    public static final int USER3_ID = 3
    public static final int USER4_ID = 4
    public static final int USER5_ID = 6

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

    def "빈 어카운트를 찾아 유저에게 할당해 주어야 한다. 할당이 끝났는데도 요청이 또 들어오면 익셉션을 발생시킨다."(){
        given:
        userDomainService.getUser(USER2_ID)
        when:
        def amount = accountApplicationService.takeAccount(USER2_ID, ROOM_ID, sharing.getId())
        amount = accountApplicationService.takeAccount(USER3_ID, ROOM_ID, sharing.getId())
        amount = accountApplicationService.takeAccount(USER4_ID, ROOM_ID, sharing.getId())
        then:
        amount.isPresent()
        amount.get().getAmount() == 6668
        and:
        when: "모두 소진되어 익셉션이 발생되어야 한다."
        amount = accountApplicationService.takeAccount(USER5_ID, ROOM_ID, sharing.getId())
        then:
        def ex = thrown(IllegalStateException)
        println(ex.getMessage())
    }

    def "두번이상 같은 유저가 같은 방에서 할당 요청을 하면 에러를 발생시킨다."() {
        given:
        userDomainService.getUser(USER2_ID)
        when:
        accountApplicationService.takeAccount(USER2_ID, ROOM_ID, sharing.getId())
        then:
        noExceptionThrown()
        and:
        when:
        accountApplicationService.takeAccount(USER2_ID, ROOM_ID, sharing.getId())
        then:
        def ex = thrown(DataIntegrityViolationException)
        println(ex.getMessage())
    }

    def "뿌리기의 주인이 할당요청을 하면 익셉션을 던진다."(){
        when:
        accountApplicationService.takeAccount(USER1_ID, ROOM_ID, sharing.getId())
        then:
        def ex = thrown(IllegalStateException)
        println(ex.getMessage())
    }

    def "뿌리기의 룸아이디가 다르다면 익셉션을 던져야 한다."(){
        when:
        accountApplicationService.takeAccount(USER2_ID, "OTHER_ROOM", sharing.getId())
        then:
        def ex = thrown(IllegalStateException)
        println(ex.getMessage())
    }
}
