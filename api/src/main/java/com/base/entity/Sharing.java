package com.base.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public final class Sharing extends CommonEntity {
    @Id
    private String token;
    private String roomId;
    private long totalAmount;
}
