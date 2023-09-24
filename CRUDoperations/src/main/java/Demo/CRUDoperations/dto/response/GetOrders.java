package Demo.CRUDoperations.dto.response;

import lombok.*;
/*
@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString
public class GetOrders {

 //    p.id,p.name,p.price,p.tax,o.id as 'orders',o.product_id
    Integer id;
    String name;
    Float price;
    Float tax;
    Integer orders;
    Integer productId;
}*/
//select p.id as id,p.name as name,p.price as price,p.tax as tax,o.id as orders,o.product_id as productId
// from product p left join orders o on p.id=o.product_id where p.id=:id
public interface GetOrders {
   Integer getId();
   String getName();
   Float getPrice();
   Float getTax();
   Integer getOrders();
   Integer getProductId();
}