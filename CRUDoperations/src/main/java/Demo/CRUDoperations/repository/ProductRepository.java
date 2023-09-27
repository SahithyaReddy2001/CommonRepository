package Demo.CRUDoperations.repository;

import Demo.CRUDoperations.dto.response.Data;
import Demo.CRUDoperations.dto.response.GetOrders;
import Demo.CRUDoperations.entity.Product;
import Demo.CRUDoperations.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>{

    @Query(value = "select * from product where status like 'ACTIVE'",nativeQuery = true)
    List<Product> findByStatus();

    @Query(value = "select * from product where id=:id",nativeQuery = true)
    Product findById(@Param("id")int id);
/*    @Query(value="select p.id as id,p.name as name,p.price as price,p.tax as tax,o.id as orders,o.product_id as productId from product p left join orders o on p.id=o.product_id where p.id=:id",nativeQuery = true)
    List<GetOrders> getByProductId(int id);*/

    @Query(value = "select p.id as productId,p.name as productName,p.price as productPrice,p.tax as productTax,p.customer_id as productProvider,o.id as orderId,o.tax_amount as totalAmount,o.non_tax_amount as amountWithOutTax,o.customer_id as customerId,c.name as customerName,c.email as customerMail from orderItem oi  left join product p on oi.product_id=p.id right join orders o on o.id=oi.order_id left join customer c on o.customer_id=c.id order by o.id",nativeQuery = true)
    List<Data> getByOrderItem();
}
