package com.base.service;

import com.base.entity.Account;
import com.base.entity.User;
import com.base.repository.AccountRepository;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountApplicationService {
    private final AccountRepository accountRepository;
    private final AccountDomainService accountDomainService;
    private final UserDomainService userDomainService;

    public Optional<Account> takeAccount(final String roomId,
                            final long userId,
                            final long sharingId) {
        List<Account> accounts = accountRepository.findAccountBySharingId(sharingId, roomId); //todo accountDomainService 밑으로 넣는 것이 더 좋을듯
        Preconditions.checkState(accounts.size() > 0, "there is no available account");
        User user = userDomainService.getUser(userId);
        return accounts.stream()
                .filter(it -> accountDomainService.allocateTo(it, user).isPresent())
                .findAny();
    }
}
