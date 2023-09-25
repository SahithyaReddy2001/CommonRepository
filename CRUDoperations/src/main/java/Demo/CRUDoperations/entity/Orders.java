package Demo.CRUDoperations.entity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "product_id", referencedColumnName = "id") // This maps to the foreign key column
    private Product product;

    //private Long productId;
    private Float taxAmount;
    private Float nonTaxAmount;

    @Column(columnDefinition = "enum('ADDED','DELETED','ACTIVE','INACTIVE') default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    public Status status = Status.ACTIVE;

    public Orders(Integer id,Status status){
        this.id = id;
        //this.productId = productId;
        this.status = status;
    }

    public Orders(Integer id, float taxAmount, float nonTaxAMount, Status status){
        this.id = id;
        //this.productId = productId;
        this.taxAmount = taxAmount;
        this.nonTaxAmount = nonTaxAMount;
        this.status = status;
    }

    public Orders(float taxAmount, float nonTaxAMount){
        this.taxAmount = taxAmount;
        this.nonTaxAmount = nonTaxAMount;
        this.status = Status.ACTIVE;
    }

}
