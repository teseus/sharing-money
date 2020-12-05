package com.base.service


import com.base.entity.Sharing
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Subject

@SpringBootTest
class SharingApplicationServiceTest extends Specification {
    public static final int SHARING_SIZE = 3
    public static final int TOTAL_MONEY = 10000
    @Subject
    @Autowired
    SharingApplicationService sharingApplicationService

    def "서비스에서 뿌리기를 수행하면 DB 에서 읽어 올 수 있어야 한다."() {
        when:
        Sharing sharing = sharingApplicationService.shareMoney(TOTAL_MONEY, SHARING_SIZE)
        then:
        sharing.getId() != null
        and:
        when:
        def accounts = sharingApplicationService.getAccountsBySharing(sharing)
        then:
        accounts.stream().mapToLong({ it -> it.getAmount() }).sum() == TOTAL_MONEY
    }
}
