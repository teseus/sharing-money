package com.base.controller;

import com.base.dto.StatusResponseDTO;
import com.base.service.StatusApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MoneyStatusController {
    private final StatusApplicationService statusApplicationService;

    @GetMapping("/status/{receivedToken}")
    public Mono<ResponseEntity<StatusResponseDTO>> getStatus(
            @RequestHeader("X-USER-ID") long userId,
            @RequestHeader("X-ROOM-ID") String roomId,
            @PathVariable String receivedToken){
        log.debug("get the status with token [{}], userId [{}], roomId [{}]", receivedToken, userId, roomId);

        StatusResponseDTO status = statusApplicationService.getStatus(userId, receivedToken);

        return Mono.just(ResponseEntity.ok(status));
    }
}
