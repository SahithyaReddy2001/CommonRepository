package com.RestTemplate.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Order {
    public Integer id;
    @NotNull(message = "product Id is Mandatory")
    public int productId;
    @NotNull(message = "Tax Amount is Mandatory")
    @Min(1)
    public float taxAmount;
    @NotNull(message = "Non Tax Amount is Mandatory")
    @Min(1)
    public float nonTaxAmount;
    enum Status{ADDED,DELETED,ACTIVE,INACTIVE};
    public Status status;
    public Order(int productId, float taxAmount, float nonTaxAmount, Status status) {
        this.productId = productId;
        this.taxAmount = taxAmount;
        this.nonTaxAmount = nonTaxAmount;
        this.status = status;
    }

}
