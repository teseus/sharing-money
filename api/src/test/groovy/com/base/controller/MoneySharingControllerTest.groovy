package com.base.controller

import com.base.dto.SharingRequestDTO
import com.base.dto.TokenResponseDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import spock.lang.Specification

@WebFluxTest(MoneySharingController.class)
class MoneySharingControllerTest extends Specification {
    @Autowired
    WebTestClient webTestClient

    def "뿌리기 api 호출하면 3자리의 문자열로 토큰을 리턴한다."() {
        given:
        def userId = "1004"
        def roomId = "SuiteRoom"
        def request = new SharingRequestDTO(10000, 3)
        when:
        def body = webTestClient.post()
                .uri("/api/v1/money/share")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("X-USER-ID", userId)
                .header("X-ROOM-ID", roomId)
                .body(Mono.just(request), SharingRequestDTO.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TokenResponseDTO.class)
        then:
        body.returnResult().responseBody.token().size() == 3
    }
}
