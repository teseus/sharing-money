package com.base.service;

import com.base.dto.ReceivedInfoDTO;
import com.base.dto.StatusResponseDTO;
import com.base.entity.Account;
import com.base.entity.Sharing;
import com.base.entity.User;
import com.base.repository.AccountRepository;
import com.base.repository.SharingRepository;
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
    private final SharingRepository sharingRepository;
    private final UserDomainService userDomainService;
    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public StatusResponseDTO getStatus(final long userId, final String token) {
        User user = userDomainService.getUser(userId);
        List<Account> accounts = accountRepository.findAccountByUserAndTokenAndDate(
                user,
                token,
                LocalDateTime.now().minusDays(SEVEN_DAYS_AGO),
                LocalDateTime.now());

        Preconditions.checkState(accounts.size() > 0,
                "there is no no search result for Token(" + token + "), UserId (" + userId + ")");

        List<ReceivedInfoDTO> infos = accounts.stream()
                .map(it -> new ReceivedInfoDTO(it.getUser().getUserId(), it.getAmount()))
                .collect(Collectors.toList());
        Sharing sharing = accounts.get(0).getSharing();

        return new StatusResponseDTO(
                sharing.getCreatedAt(),
                sharing.getTotalAmount(),
                infos.stream().mapToLong(ReceivedInfoDTO::allocatedAmount).sum(),
                infos);
    }
}
