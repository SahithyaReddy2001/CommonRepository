package Demo.CRUDoperations.dto.request;

import Demo.CRUDoperations.entity.Status;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderRequest {
    public int id;
    @NotNull(message = "product Id is Mandatory")
    public int productId;
    @NotNull(message = "Tax Amount is Mandatory")
    @Min(1)
    public float taxAmount;
    @NotNull(message = "Non Tax Amount is Mandatory")
    @Min(1)
    public float nonTaxAmount;

    @Enumerated(EnumType.STRING)
    public Status status;
    public OrderRequest() {

    }
    public OrderRequest(int productId, float taxAmount, float nonTaxAmount, Status status) {
        this.productId = productId;
        this.taxAmount = taxAmount;
        this.nonTaxAmount = nonTaxAmount;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public float getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(float taxAmount) {
        this.taxAmount = taxAmount;
    }

    public float getNonTaxAmount() {
        return nonTaxAmount;
    }

    public void setNonTaxAmount(float nonTaxAmount) {
        this.nonTaxAmount = nonTaxAmount;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "productId=" + productId +
                ", taxAmount=" + taxAmount +
                ", nonTaxAmount=" + nonTaxAmount +
                ", status=" + status +
                '}';
    }
}
