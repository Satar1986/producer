package org.example.service.dto;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateDTO {
    private String externalId;
    private String clientId;
    private String nameAccount;
    private Double sum;
    private String currency;
    private Double interestRate;
    private String interestIsPaid;
    private Double minRemainder;
    private Integer stateId;
    private String nameCompany;
    private Long inn;
    private Integer kpp;
    private Long ogrn;
    private String businessAddress;
    private String address;
    private Integer rcbic;
    private Double corrAss;
    private Double ass;
    private String bankName;
    private Integer requisitesId;
}
