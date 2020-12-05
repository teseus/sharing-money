package com.base.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class User extends CommonEntity {
    @Id
    private long userId;
}
