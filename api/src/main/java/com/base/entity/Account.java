package com.base.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Account extends CommonEntity {
    @Id
    private long accountId;
    private long amount;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private User user;
}
