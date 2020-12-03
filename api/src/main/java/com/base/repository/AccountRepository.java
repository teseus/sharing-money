package com.base.repository;

import com.base.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountId(long accountId);
    @Query("select a from Account a join fetch a.sharing") //Join Fetch N+1 회피.
    List<Account> findAccountBySharingToken(String token);
}
