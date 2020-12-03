package com.base.controller;

import com.base.dto.AllowanceResponseDTO;
import com.base.dto.SharingRequestDTO;
import com.base.dto.StatusResponseDTO;
import com.base.dto.TokenResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class MoneySharingController {
    @PostMapping("/money/share")
    public Mono<ResponseEntity<TokenResponseDTO>> createToken(
            @RequestHeader("X-USER-ID") long userId,
            @RequestHeader("X-ROOM-ID") String roomId,
            @RequestBody SharingRequestDTO sharingRequestDTO){
        log.debug("create a token with [{}], userId [{}], roomId [{}]\"", sharingRequestDTO, userId, roomId);
        return Mono.just(ResponseEntity.ok(new TokenResponseDTO("ABC")));
    }

    @GetMapping("/money/{receivedToken}")
    public Mono<ResponseEntity<AllowanceResponseDTO>> getMoney(
            @RequestHeader("X-USER-ID") long userId,
            @RequestHeader("X-ROOM-ID") String roomId,
            @PathVariable String receivedToken){
        log.debug("get the money with token [{}], userId [{}], roomId [{}]", receivedToken, userId, roomId);

        return Mono.just(ResponseEntity.ok(new AllowanceResponseDTO(1000)));
    }

    @GetMapping("/status/{receivedToken}")
    public Mono<ResponseEntity<StatusResponseDTO>> getStatus(
            @RequestHeader("X-USER-ID") long userId,
            @RequestHeader("X-ROOM-ID") String roomId,
            @PathVariable String receivedToken){
        log.debug("get the money with token [{}], userId [{}], roomId [{}]", receivedToken, userId, roomId);

        return Mono.just(ResponseEntity.ok(new StatusResponseDTO(LocalDateTime.now(), 10000, Collections.emptyList())));
    }
}
