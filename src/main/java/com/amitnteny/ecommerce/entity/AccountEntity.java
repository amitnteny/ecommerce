package com.amitnteny.ecommerce.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "ACCOUNT")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ACCOUNT_ID")
    private Long accountId;
    @Column(name = "NAME")
    private String name;
    @Column(name = "PHONE_NUMBER")
    private Long phoneNumber;
    @Column(name = "ADDRESS")
    private String address;
}
