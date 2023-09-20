package Demo.CRUDoperations.entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = Product.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id") // This maps to the foreign key column
    private Product product;

    //private Integer productId;
    private Float taxAmount;
    private Float nonTaxAmount;

    @Column(columnDefinition = "enum('ADDED','DELETED','ACTIVE','INACTIVE') default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    public Status status = Status.ACTIVE;

    /*public Orders() {
    }*/

    public Orders(int id, Product product, float taxAmount, float nonTaxAmount, Status status){
        this.id = id;
        //this.productId = productId;
        this.product = product;
        this.taxAmount = taxAmount;
        this.nonTaxAmount = nonTaxAmount;
        this.status = status;
    }

    public Orders(Product product, float taxAmount, float nonTaxAmount, Status status){
        //this.productId = productId;
        this.product = product;
        this.taxAmount = taxAmount;
        this.nonTaxAmount = nonTaxAmount;
        //this.status = status;
    }


   /*public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
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


    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", taxAmount=" + taxAmount +
                ", nonTaxAmount=" + nonTaxAmount +
                ", status=" + status +
                '}';
    }*/
}
