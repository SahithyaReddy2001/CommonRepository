package Demo.CRUDoperations.entity;
import io.swagger.models.auth.In;
import lombok.*;
import java.util.List;

import javax.persistence.*;

@Entity(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItem;

    private Double taxAmount;
    private Double nonTaxAmount;

    @Column(columnDefinition = "enum('ADDED','DELETED','ACTIVE','INACTIVE') default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    public Status status = Status.ACTIVE;

    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    public Order(Integer id, Status status){
        this.id = id;
        this.status = status;
    }

    public Order(Double taxAmount, Double nonTaxAMount, Status status,Customer customer){
        this.taxAmount = taxAmount;
        this.nonTaxAmount = nonTaxAMount;
        this.status = status;
        this.customer = customer;
    }

    public Order(Double taxAmount, Double nonTaxAMount){
        this.taxAmount = taxAmount;
        this.nonTaxAmount = nonTaxAMount;
        this.status = Status.ACTIVE;
    }

}
