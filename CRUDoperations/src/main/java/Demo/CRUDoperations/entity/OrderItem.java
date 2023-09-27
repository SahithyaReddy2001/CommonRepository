package Demo.CRUDoperations.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "orderItem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(targetEntity = Order.class)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;


    public OrderItem(Order order, Product product) {
        this.order = order;
        this.product = product;
    }
}
