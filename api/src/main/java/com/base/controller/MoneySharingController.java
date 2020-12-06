package com.base.controller;

import com.base.dto.SharingRequestDTO;
import com.base.dto.TokenResponseDTO;
import com.base.entity.Sharing;
import com.base.service.SharingApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MoneySharingController {
    private final SharingApplicationService sharingApplicationService;

    @PostMapping("/money/share")
    public Mono<ResponseEntity<TokenResponseDTO>> createToken(
            @RequestHeader("X-USER-ID") long userId,
            @RequestHeader("X-ROOM-ID") String roomId,
            @RequestBody @Valid SharingRequestDTO sharingRequestDTO){
        log.debug("create a token with [{}], userId [{}], roomId [{}]", sharingRequestDTO, userId, roomId);

        Sharing sharing = sharingApplicationService.shareMoney(
                userId, roomId, sharingRequestDTO.totalMoney(), sharingRequestDTO.separatedSize());

        return Mono.just(ResponseEntity.ok(new TokenResponseDTO(sharing.getToken())));
    }
}
