package com.base.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor
public final class Account extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountId;
    private long amount;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Sharing sharing;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private User user;

    @Version
    private int dirty;

    @Builder
    public Account(final long amount,
                   final Sharing sharing,
                   final Optional<User> user){
        this.amount = amount;
        this.sharing = sharing;
        user.ifPresent(it->this.user = it);
    }

    public Account allocateUser(User user){
        this.user = user;
        return this;
    }
}
