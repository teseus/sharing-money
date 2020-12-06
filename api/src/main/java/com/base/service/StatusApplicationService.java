package com.base.service;

import com.base.dto.ReceivedInfoDTO;
import com.base.dto.StatusResponseDTO;
import com.base.entity.Account;
import com.base.entity.Sharing;
import com.base.repository.AccountRepository;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatusApplicationService {
    public static final int SEVEN_DAYS_AGO = 7;
    private final UserDomainService userDomainService;
    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public StatusResponseDTO getStatus(final long userId, final String token) {
        List<Account> accounts = getAccountsIn7Days(token, userId);
        validate(userId, token, accounts);

        return assembleStatusDto(getSharing(accounts), getReceiveCompleteInfo(accounts));
    }

    private StatusResponseDTO assembleStatusDto(Sharing sharing, List<ReceivedInfoDTO> receiveCompletes) {
        return new StatusResponseDTO(
                sharing.getCreatedAt(),
                sharing.getTotalAmount(),
                sumReceiveCompleteAmount(receiveCompletes),
                receiveCompletes);
    }

    private long sumReceiveCompleteAmount(List<ReceivedInfoDTO> receiveCompletes) {
        return receiveCompletes.stream().mapToLong(ReceivedInfoDTO::allocatedAmount).sum();
    }

    private Sharing getSharing(List<Account> accounts) {
        return accounts.get(0).getSharing();
    }

    private List<ReceivedInfoDTO> getReceiveCompleteInfo(List<Account> accounts) {
        return accounts.stream()
                .filter(it -> it.getUser() != null)
                .map(it -> new ReceivedInfoDTO(it.getUser().getUserId(), it.getAmount()))
                .collect(Collectors.toList());
    }

    private void validate(long userId, String token, List<Account> accounts) {
        Preconditions.checkState(accounts.size() > 0,
                "[" + token + "]토큰 과 [" + userId + "]유저로는 조회 정보가 없습니다.");
    }

    private List<Account> getAccountsIn7Days(String token, long userId) {
        return accountRepository.findAccountByUserAndTokenAndDate(
                userDomainService.getUser(userId),
                token,
                LocalDateTime.now().minusDays(SEVEN_DAYS_AGO),
                LocalDateTime.now());
    }
}
