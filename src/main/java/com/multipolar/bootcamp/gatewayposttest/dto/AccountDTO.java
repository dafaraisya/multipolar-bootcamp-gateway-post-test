package com.multipolar.bootcamp.gatewayposttest.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AccountDTO {
    private String id;
    private String accountNumber;
    private AccountTypeDTO accountType;
    private AccountStatusDTO accountStatus;
    private AccountHolderDTO accountHolder;
    private double balance;
    private LocalDateTime openingDate = LocalDateTime.now();
    private LocalDate closingDate;
}
