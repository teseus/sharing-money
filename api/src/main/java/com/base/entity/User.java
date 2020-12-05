package com.base.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class User extends CommonEntity {
    @Id
    private long userId;
}
