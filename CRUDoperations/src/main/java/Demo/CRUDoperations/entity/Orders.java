package Demo.CRUDoperations.entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
/*@Getter
@Setter
@NoArgsConstructor
@ToString*/
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Integer productId;
    private Float taxAmount;
    private Float nonTaxAmount;

    @Column(columnDefinition = "enum('ADDED','DELETED','ACTIVE','INACTIVE') default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    public Status status = Status.ACTIVE;

    public Orders() {
    }

    public Orders(int id, int productId, float taxAmount, float nonTaxAmount, Status status){
        this.id = id;
        this.productId = productId;
        this.taxAmount = taxAmount;
        this.nonTaxAmount = nonTaxAmount;
        this.status = status;
    }

    public Orders(int productId, float taxAmount, float nonTaxAmount, Status status){
        this.productId = productId;
        this.taxAmount = taxAmount;
        this.nonTaxAmount = nonTaxAmount;
        //this.status = status;
    }


   public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public Float getTaxAmount() {
        return taxAmount;
    }

    public Float getNonTaxAmount() {
        return nonTaxAmount;
    }

    public void setNonTaxAmount(float nonTaxAmount) {
        this.nonTaxAmount = nonTaxAmount;
    }

    public void setTaxAmount(float taxAmount) {
        this.taxAmount = taxAmount;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", productId=" + productId +
                ", taxAmount=" + taxAmount +
                ", nonTaxAmount=" + nonTaxAmount +
                ", status=" + status +
                '}';
    }
}
