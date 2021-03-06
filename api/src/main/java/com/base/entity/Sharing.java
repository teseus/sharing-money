package com.base.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Entity
@ToString
@NoArgsConstructor
@Table(indexes = {@Index(columnList="token")})
public final class Sharing extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
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

    public Sharing changeToken(final String token){
        this.token = token;
        return this;
    }
}
