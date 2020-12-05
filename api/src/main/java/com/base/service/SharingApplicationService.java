package com.base.service;

import com.base.entity.Account;
import com.base.entity.Sharing;
import com.base.entity.User;
import com.base.repository.AccountRepository;
import com.base.repository.SharingRepository;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SharingApplicationService {
    private final SharingRepository sharingRepository;
    private final AccountRepository accountRepository;
    private final UserDomainService userDomainService;

    @Transactional
    public Sharing shareMoney(final long userId, final String roomId,
                              final long askedTotalMoney, final long separatedSize) {
        Preconditions.checkState(askedTotalMoney > separatedSize,
                "askedTotalMoney must be bigger than separatedSize");

        return makeSharing(userDomainService.getUser(userId), roomId, askedTotalMoney, separatedSize);
    }

    private Sharing makeSharing(final User user, final String roomId,
                                final long askedTotalMoney, final long separatedSize) {
        Sharing sharing = sharingRepository.save(Sharing.builder()
                .user(user)
                .roomId(roomId)
                .totalAmount(askedTotalMoney)
                .build());

        List<Account> accounts = SharingApplicationServiceHelper
                .separateMoney(askedTotalMoney, separatedSize).stream()
                .map(it -> allocateSeparatedMoneyToEachAccount(sharing, it))
                .collect(Collectors.toList());

        accountRepository.saveAll(accounts);
        return sharing;
    }

    private Account allocateSeparatedMoneyToEachAccount(Sharing savedSharing, Long it) {
        return Account.builder().amount(it).sharing(savedSharing).user(Optional.empty()).build();
    }

    public List<Account> getAccountsBySharing(final Sharing sharing){
        return accountRepository.findAccountBySharingIdAndRoomId(sharing.getId());
    }
}