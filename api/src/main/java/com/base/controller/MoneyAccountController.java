package com.base.controller;

import com.base.dto.AllowanceResponseDTO;
import com.base.entity.Account;
import com.base.service.AccountApplicationService;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MoneyAccountController {
    private final AccountApplicationService accountApplicationService;

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
}
