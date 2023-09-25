package Demo.CRUDoperations.dto.request;

import Demo.CRUDoperations.entity.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderRequest {
    public Integer id;
    public Integer productId;


    @NotNull(message = "Tax Amount is Mandatory")
    @Min(1)
    public float taxAmount;
    @NotNull(message = "Non Tax Amount is Mandatory")
    @Min(1)
    public float nonTaxAmount;

    @Enumerated(EnumType.STRING)
    public Status status;

    /*public OrderRequest(Integer productId, float taxAmount, float nonTaxAmount, Status status) {
        this.productId = productId;
        this.taxAmount = taxAmount;
        this.nonTaxAmount = nonTaxAmount;
        this.status = status;
    }*/


}
