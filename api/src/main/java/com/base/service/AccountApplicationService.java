package com.base.service;

import com.base.entity.Account;
import com.base.entity.User;
import com.base.repository.AccountRepository;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                                         final long sharingId) {
        List<Account> accounts = accountRepository.findAccountBySharingIdAndRoomId(sharingId); //todo accountDomainService 밑으로 넣는 것이 더 좋을듯
        validate(userId, roomId, accounts);
        User user = userDomainService.getUser(userId);
        return accounts.stream()
                .sorted(Comparator.comparing(Account::getAccountId))
                .filter(it -> accountDomainService.allocateTo(it, user).isPresent())
                .findAny();
    }

    private void validate(long userId, String roomId, List<Account> accounts) {
        Preconditions.checkState(accounts.size() > 0, "there is no available account");
        Preconditions.checkState(getOwnerId(accounts) != userId,
                "asking user[" + userId + "] cannot be owner [" + getOwnerId(accounts) +"]");
        Preconditions.checkState(getRoomId(accounts).equals(roomId),
                "received roomId[" + roomId + "] is different to Sharing RoomId[" + getRoomId(accounts) + "]");
    }

    private static long getOwnerId(List<Account> accounts){
        return accounts.get(0).getSharing().getUser().getUserId();
    }

    private static String getRoomId(List<Account> accounts){
        return accounts.get(0).getSharing().getRoomId();
    }
}
