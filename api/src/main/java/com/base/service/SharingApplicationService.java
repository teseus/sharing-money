package com.base.service;

import com.base.entity.Account;
import com.base.entity.Sharing;
import com.base.entity.User;
import com.base.repository.AccountRepository;
import com.base.repository.SharingRepository;
import com.base.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Transactional
    public Sharing shareMoney(final long userId, final String roomId,
                              final long askedTotalMoney, final long separatedSize) {
        Preconditions.checkState(askedTotalMoney > separatedSize,
                "askedTotalMoney must be bigger than separatedSize");

        final Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            return makeSharing(user.get(), roomId, askedTotalMoney, separatedSize);
        }

        return makeSharingNUser(userId, roomId, askedTotalMoney, separatedSize);
    }

    private Sharing makeSharingNUser(final long userId, final String roomId,
                                     final long askedTotalMoney, final long separatedSize) {
        return makeSharing(userRepository.save(new User(userId)), roomId, askedTotalMoney, separatedSize);
    }

    private Sharing makeSharing(final User user, final String roomId,
                                final long askedTotalMoney, final long separatedSize) {
        Sharing savedSharing = sharingRepository.save(Sharing.builder()
                .user(user)
                .roomId(roomId)
                .totalAmount(10000)
                .build());

        List<Account> accounts = SharingApplicationServiceHelper.separate(askedTotalMoney, separatedSize).stream()
                .map(it -> Account.builder().amount(it).sharing(savedSharing).user(Optional.empty()).build())
                .collect(Collectors.toList());

        accountRepository.saveAll(accounts);
        return savedSharing;
    }

    public List<Account> getAccountsBySharing(final Sharing sharing){
        return accountRepository.findAccountBySharingId(sharing.getId(), sharing.getRoomId());
    }
}