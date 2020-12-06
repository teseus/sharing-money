package com.base.repository;

import com.base.entity.Account;
import com.base.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("""
        select a from Account a 
        join fetch a.sharing s
        where s.token = :token
        and s.roomId = :roomId
        and a.user is null
    """) //Join Fetch N+1 회피.
    List<Account> findAccountByTokenAndRoomId(final String token, final String roomId);

    @Query("""
        select a from Account a 
        join fetch a.sharing s
        where a.user = :user
        and s.token = :token
        and s.roomId = :roomId
    """) //Join Fetch N+1 회피.
    List<Account> findAccountByUserAndTokenAndRoomId(final User user, final String token, final String roomId);
}
