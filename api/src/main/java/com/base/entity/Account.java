package com.base.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public final class Account extends CommonEntity {
    @Id
    private long accountId;
    private long amount;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Sharing sharing;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private User user;
}
