package org.example.service.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String client_id;
    private String name_account;
    private Double sum;
    private String currency;
    private Double interest_rate;
    private String interest_is_paid;
    private Double min_remainder;
    private Integer state_id;
    private String name_company;
    private Long inn;
    private Integer kpp;
    private Long ogrn;
    private String business_address;
    private String address;
    private Integer rcbic;
    private Double corr_ass;
    private Double ass;
    private String bank_name;
}
