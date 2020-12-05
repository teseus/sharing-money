package com.base.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Sharing extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomId;
    private long totalAmount;
    @ManyToOne
    private User user;
    @Builder
    public Sharing(final String roomId, final User user, final long totalAmount){
        this.roomId = roomId;
        this.user = user;
        this.totalAmount = totalAmount;
    }
}
