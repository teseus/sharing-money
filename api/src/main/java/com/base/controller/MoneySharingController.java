package com.base.controller;

import com.base.dto.SharingRequestDTO;
import com.base.dto.TokenResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class MoneySharingController {
    @PostMapping("/money/share")
    public Mono<ResponseEntity<TokenResponseDTO>> createToken(
            @RequestHeader("X-USER-ID") Integer userId,
            @RequestHeader("X-ROOM-ID") String roomId,
            @RequestBody SharingRequestDTO sharingRequestDTO){
        log.debug("received {}", sharingRequestDTO);
        return Mono.just(ResponseEntity.ok(new TokenResponseDTO("ABC")));
    }
}
