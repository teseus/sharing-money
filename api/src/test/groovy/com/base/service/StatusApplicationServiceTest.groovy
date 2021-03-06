package com.base.service

import com.base.dto.StatusResponseDTO
import com.base.entity.Sharing
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

@SpringBootTest
class StatusApplicationServiceTest extends Specification {
    public static final int SHARING_SIZE = 4
    public static final int TOTAL_MONEY = 40000
    public static final String ROOM_ID = "PerfectRoom"
    public static final int USER1_ID = 1
    public static final int USER2_ID = 2
    public static final int USER3_ID = 3
    public static final int USER4_ID = 4
    public static final int USER5_ID = 5
    public static final LocalDateTime NOW = LocalDateTime.now()

    @Subject
    @Autowired
    StatusApplicationService statusApplicationService
    @Autowired
    SharingApplicationService sharingApplicationService
    @Autowired
    AccountApplicationService accountApplicationService
    @Shared
    Sharing sharing

    def setup() {
        sharing = sharingApplicationService.shareMoney(USER1_ID, ROOM_ID, TOTAL_MONEY, SHARING_SIZE)
    }

    @Transactional
    def "토큰을 주면 상태정보를 리턴해야 한다."(){
        given:
        accountApplicationService.takeAccount(USER2_ID, ROOM_ID, sharing.getToken(), NOW)
        accountApplicationService.takeAccount(USER3_ID, ROOM_ID, sharing.getToken(), NOW)
        accountApplicationService.takeAccount(USER4_ID, ROOM_ID, sharing.getToken(), NOW)
        def account = accountApplicationService.takeAccount(USER5_ID, ROOM_ID, sharing.getToken(), NOW)
        println("last amount ${account.get()}")
        when:
        StatusResponseDTO status = statusApplicationService.getStatus(USER1_ID, sharing.getToken())
        then:
        noExceptionThrown()
        status.receivedInfoDTOs().size() == 4
    }
}
