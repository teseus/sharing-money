package com.base.controller

import com.base.dto.GreetingDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification

@WebFluxTest(ApiController.class)
class ApiControllerTest extends Specification {
    @Autowired
    WebTestClient webTestClient

    def "a returned name should be same with the name sent via api"() {
        given:
        def name = "teseus"
        when:
        def body = webTestClient.get()
                .uri("/api/v1/greet/${name}")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GreetingDTO.class)
        then:
        body.returnResult().responseBody.greet() == "Hello ${name}"
    }
}
