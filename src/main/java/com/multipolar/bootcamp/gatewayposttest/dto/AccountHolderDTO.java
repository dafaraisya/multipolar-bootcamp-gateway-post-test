package com.multipolar.bootcamp.gatewayposttest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountHolderDTO {
    private String nik;
    private String name;
    private String address;
}
