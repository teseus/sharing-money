package com.base.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Sharing {
    @Id
    private String token;
    private long totalAmount;

    @ManyToOne
    private User user;
}
