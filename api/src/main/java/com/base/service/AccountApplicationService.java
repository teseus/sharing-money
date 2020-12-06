package com.base.service;

import com.base.entity.Account;
import com.base.entity.User;
import com.base.repository.AccountRepository;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountApplicationService {
    private final AccountRepository accountRepository;
    private final AccountDomainService accountDomainService;
    private final UserDomainService userDomainService;

    public Optional<Account> takeAccount(final long userId,
                                         final String roomId,
                                         final String token,
                                         final LocalDateTime takingTime) {
        List<Account> accounts = findAccounts(roomId, token);
        validate(userId, roomId, token, takingTime, accounts);

        return tryToTakeOne(accounts, getUser(userId));
    }

    private User getUser(long userId) {
        return userDomainService.getUser(userId);
    }

    private Optional<Account> tryToTakeOne(List<Account> accounts, User user) {
        return accounts.stream()
                .sorted(Comparator.comparing(Account::getAccountId))
                .filter(it -> accountDomainService.allocateTo(it, user).isPresent())
                .findAny();
    }

    private List<Account> findAccounts(String roomId, String token) {
        return accountRepository.findAccountByTokenAndRoomId(token, roomId);
    }

    private void validate(long userId, String roomId, String token,
                          final LocalDateTime takingTime, List<Account> accounts) {
        Preconditions.checkState(accounts.size() > 0,
                "[" + roomId + "]방에 토큰[" + token + "]으로는 더이상 할당 받을 수 없습니다.");
        Preconditions.checkState(getOwnerId(accounts) != userId,
                "뿌린 사람[" + getOwnerId(accounts) + "], 자신은 할당 받을 수 없습니다.");
        Preconditions.checkState(accounts.get(0).getSharing().getCreatedAt().isAfter(
                takingTime.minusMinutes(10)
        ), "뿌린지 10분이 초과되었습니다.");
    }

    private static long getOwnerId(List<Account> accounts){
        return accounts.get(0).getSharing().getUser().getUserId();
    }
}
