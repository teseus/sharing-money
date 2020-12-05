package com.base.service;

import com.base.entity.Account;
import com.base.entity.Sharing;
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

    @Transactional
    public Sharing shareMoney(final long askedTotalMoney, final long separatedSize) {
        Preconditions.checkState(askedTotalMoney > separatedSize,
                "askedTotalMoney must be bigger than separatedSize");
        Sharing savedSharing = sharingRepository.save(Sharing.builder()
                .roomId("ABC")
                .totalAmount(10000)
                .build());

        List<Account> accounts = SharingApplicationServiceHelper.separate(askedTotalMoney, separatedSize).stream()
                .map(it -> Account.builder().amount(it).sharing(savedSharing).user(Optional.empty()).build())
                .collect(Collectors.toList());

        accountRepository.saveAll(accounts);

        return savedSharing;
    }

    public List<Account> getAccountsBySharing(final Sharing sharing){
        return accountRepository.findAccountBySharingId(sharing.getId());
    }
}