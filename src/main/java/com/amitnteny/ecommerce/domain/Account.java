package com.amitnteny.ecommerce.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Account {
    private Long accountId;
    private String name;
    private Long phoneNumber;
    private String address;
}
