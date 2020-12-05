package com.base.repository

import com.base.entity.DuplicationCheck
import com.base.entity.DuplicationCheckId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@SpringBootTest
class DuplicationCheckRepositoryTest extends Specification {
    @Autowired
    DuplicationCheckRepository duplicationCheckRepository

    @Transactional
    def "중복지급을 시도하면 에러를 발생시킨다."() {
        when:
        duplicationCheckRepository.save(new DuplicationCheck(new DuplicationCheckId(10, 100)))
        then:
        noExceptionThrown()
        when:
        duplicationCheckRepository.save(new DuplicationCheck(new DuplicationCheckId(10, 100)))
        then:
        thrown(DataIntegrityViolationException)
    }
}
