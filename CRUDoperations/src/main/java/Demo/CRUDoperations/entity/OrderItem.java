package Demo.CRUDoperations.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor@NoArgsConstructor@Setter@Getter@ToString
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    Integer orderId;
    Integer productId;


}
