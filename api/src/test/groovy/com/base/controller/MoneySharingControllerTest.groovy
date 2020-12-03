package com.base.controller

import com.base.dto.AllowanceResponseDTO
import com.base.dto.SharingRequestDTO
import com.base.dto.StatusResponseDTO
import com.base.dto.TokenResponseDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import spock.lang.Shared
import spock.lang.Specification

@WebFluxTest(MoneySharingController.class)
class MoneySharingControllerTest extends Specification {
    //api 호출과 리턴이 잘되는 지만 테스트한다. 내용은 각 유닛테스트에서 체크 한다.
    @Autowired
    WebTestClient webTestClient

    @Shared
    def sharingUserId
    @Shared
    def roomId
    @Shared
    def tokenCreationRequest
    @Shared
    def body

    def setup() {
        sharingUserId = "1004"
        roomId = "SuiteRoom"
        tokenCreationRequest = new SharingRequestDTO(10000, 3)
    }

    def "금액을 뿌리고, 뿌린 금액을 받고, 현재 상황을 리턴해야 한다."() {
        given:
        def request = new SharingRequestDTO(10000, 3)
        when: "뿌릴 금액, 뿌릴 인원으로 뿌리기 api 호출하면 3자리의 문자열로 토큰을 리턴한다."
        body = webTestClient.post()
                .uri("/api/v1/money/share")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("X-USER-ID", sharingUserId)
                .header("X-ROOM-ID", roomId)
                .body(Mono.just(request), SharingRequestDTO.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TokenResponseDTO.class)

        then:"리턴된 토큰의 길이는 3이어야 한다."
        body.returnResult().responseBody.token().size() == 3

        and:"이전 테스트에서 받은 바디에서 토큰을 취한"
        def receivedToken = body.returnResult().responseBody.token()
        when: "받은 token 으로 받기 API 를 요청하면 할당된 금액들 중 하나를 리턴한다." +
                "토큰은 같되 아이디는 달라야 한다."
        def otherId = "4000"
        body = webTestClient.get()
                .uri("/api/v1/money/${receivedToken}")
                .accept(MediaType.APPLICATION_JSON)
                .header("X-USER-ID", otherId)
                .header("X-ROOM-ID", roomId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AllowanceResponseDTO.class)
        then: "금액이 0원 이상이어야 한다."
        body.returnResult().responseBody.amount() > 0

        and:
        when: "받은 token 으로 조회 API 를 요청하면, 현재 상태를 리턴한다." +
                "토큰은 같되 아이디는 뿌린사람과 같아야 한다." +
                "뿌린 시각, 뿌린 금액, 받기 완료된 금액, 받기 완료된 정보 ([받은 금액, 받은사용자 아이디] 리스트)"
        body = webTestClient.get()
                .uri("/api/v1/money/status/${receivedToken}")
                .accept(MediaType.APPLICATION_JSON)
                .header("X-USER-ID", sharingUserId)
                .header("X-ROOM-ID", roomId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(StatusResponseDTO.class)
        then:
        body.returnResult().responseBody.sharedTotal() > 0
    }
}
