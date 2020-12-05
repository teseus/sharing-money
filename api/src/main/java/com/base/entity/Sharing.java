package com.base.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Sharing extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomId;
    private long totalAmount;
    @Builder
    public Sharing(final String roomId, final long totalAmount){
        this.roomId = roomId;
        this.totalAmount = totalAmount;
    }
}
