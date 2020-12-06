package com.base.controller;

import com.base.dto.AllowanceResponseDTO;
import com.base.dto.SharingRequestDTO;
import com.base.dto.StatusResponseDTO;
import com.base.dto.TokenResponseDTO;
import com.base.entity.Account;
import com.base.entity.Sharing;
import com.base.service.AccountApplicationService;
import com.base.service.SharingApplicationService;
import com.base.service.StatusApplicationService;
import com.base.util.TokenEncoder;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MoneySharingController {
    private final SharingApplicationService sharingApplicationService;
    private final AccountApplicationService accountApplicationService;
    private final StatusApplicationService statusApplicationService;

    @PostMapping("/money/share")
    public Mono<ResponseEntity<TokenResponseDTO>> createToken(
            @RequestHeader("X-USER-ID") long userId,
            @RequestHeader("X-ROOM-ID") String roomId,
            @RequestBody @Valid SharingRequestDTO sharingRequestDTO){
        log.debug("create a token with [{}], userId [{}], roomId [{}]\"", sharingRequestDTO, userId, roomId);

        Sharing sharing = sharingApplicationService.shareMoney(
                userId, roomId, sharingRequestDTO.totalMoney(), sharingRequestDTO.separatedSize());

        return Mono.just(ResponseEntity.ok(new TokenResponseDTO(TokenEncoder.encode(sharing.getId()))));
    }

    @GetMapping("/money/{receivedToken}")
    public Mono<ResponseEntity<AllowanceResponseDTO>> getMoney(
            @RequestHeader("X-USER-ID") long userId,
            @RequestHeader("X-ROOM-ID") String roomId,
            @PathVariable String receivedToken){
        log.debug("get the money with token [{}], userId [{}], roomId [{}]", receivedToken, userId, roomId);

        Optional<Account> account = accountApplicationService.takeAccount(
                userId, roomId, receivedToken, LocalDateTime.now());
        Preconditions.checkState(account.isPresent(), "[" + receivedToken + "] 토큰으로 할당에 실패했습니다.");
        return Mono.just(ResponseEntity.ok(new AllowanceResponseDTO(account.get().getAmount())));
    }

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
