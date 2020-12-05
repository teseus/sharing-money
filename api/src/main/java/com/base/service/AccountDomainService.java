package com.base.service;

import com.base.entity.Account;
import com.base.entity.User;
import com.base.repository.AccountRepository;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.LockTimeoutException;
import javax.persistence.PessimisticLockException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountDomainService {
    private final AccountRepository accountRepository;
    private final EntityManager entityManager;

    @Transactional
    public Optional<Account> allocateTo(final Account account, final User user) {
        try {
            Account foundAccount = allocateUser(account, user);
            return Optional.of(foundAccount);
        } catch (IllegalStateException | LockTimeoutException | PessimisticLockException ex) {
            log.warn("an allowance allocation Fail for user[{}]", user);
            return Optional.empty();
        }
    }

    private Account allocateUser(Account account, User user) {
        Account foundAccount = entityManager.find(Account.class, account.getAccountId());
        Preconditions.checkNotNull(foundAccount,
                "Cannot find the account :" + account.getAccountId());
        Preconditions.checkState(foundAccount.getDirty() == 0,
                "Already dirty account :" + account.getAccountId());
        entityManager.lock(foundAccount, LockModeType.PESSIMISTIC_FORCE_INCREMENT);
        foundAccount.allocateUser(user);
        entityManager.persist(foundAccount);
        Preconditions.checkState(foundAccount.getDirty() == 1,
                "Already dirty account :" + account.getAccountId());

        return foundAccount;
    }
}
