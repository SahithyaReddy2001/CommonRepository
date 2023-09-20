package Demo.CRUDoperations.dto.request;

import Demo.CRUDoperations.entity.Product;
import Demo.CRUDoperations.entity.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/*@Getter
@Setter
@NoArgsConstructor
@ToString*/
public class OrderRequest {
    public Integer id;
    /*@NotNull(message = "product Id is Mandatory")
    public int productId;*/
    //@ManyToOne(targetEntity = Product.class, cascade = CascadeType.ALL)
    //@JoinColumn(name = "productId", referencedColumnName = "id") // This maps to the foreign key column
    public Product product;

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
    public OrderRequest(Product product, float taxAmount, float nonTaxAmount, Status status) {
        this.product = product;
        this.taxAmount = taxAmount;
        this.nonTaxAmount = nonTaxAmount;
        this.status = status;
    }

   public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
