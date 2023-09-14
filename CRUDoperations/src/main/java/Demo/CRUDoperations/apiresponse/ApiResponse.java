package Demo.CRUDoperations.apiresponse;

import Demo.CRUDoperations.entity.Status;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class ApiResponse {
    @Enumerated(EnumType.STRING)
    public Status status;
    private int id;
    private int productId;
    private float taxAmount;
    private float nonTaxAmount;

    public ApiResponse() {
    }

    public ApiResponse(Status status, int id, int productId, float taxAmount, float nonTaxAmount) {
        this.status = status;
        this.id = id;
        this.productId = productId;
        this.taxAmount = taxAmount;
        this.nonTaxAmount = nonTaxAmount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "ApiResponse{" +
                "status=" + status +
                ", id=" + id +
                ", productId=" + productId +
                ", taxAmount=" + taxAmount +
                ", nonTaxAmount=" + nonTaxAmount +
                '}';
    }
}
