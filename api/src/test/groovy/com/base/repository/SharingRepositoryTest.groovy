package com.base.repository

import com.base.entity.Sharing
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@SpringBootTest
class SharingRepositoryTest extends Specification {
    public static final String TOKEN = "ABC"
    @Autowired
    SharingRepository sharingRepository

    @Transactional
    def "뿌리기에 저장하고 저장한 것을 찾을 수 있어야 한다."(){
        when:
        def sharing = sharingRepository.save(new Sharing(TOKEN, "SuiteRoom", 10000L))
        def foundSharing = sharingRepository.findById(TOKEN)
        then:
        foundSharing.isPresent()
        sharing == foundSharing.get()
    }
}
