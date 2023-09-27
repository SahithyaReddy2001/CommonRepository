package Demo.CRUDoperations.repository;

import Demo.CRUDoperations.dto.response.JoinResponse;
import Demo.CRUDoperations.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Integer> {

    // FIND BY STATUS
    @Query(value = "select p.id as productId,p.name as productName,p.price as productPrice,p.tax as productTax,p.customer_id as productProvider,o.id as orderId,o.tax_amount as totalAmount,o.non_tax_amount as amountWithOutTax,o.customer_id as customerId,c.name as customerName,c.email as customerMail from order_item oi  left join product p on oi.product_id=p.id left join orders o on o.id=oi.order_id left join customer c on o.customer_id=c.id order by o.id",nativeQuery = true)
    List<JoinResponse> findByStatus();

    // GET SINGLE ELEMENT
    @Query(value = "select p.id as productId,p.name as productName,p.price as productPrice,p.tax as productTax,p.customer_id as productProvider,o.id as orderId,o.tax_amount as totalAmount,o.non_tax_amount as amountWithOutTax,o.customer_id as customerId,c.name as customerName,c.email as customerMail from order_item oi  left join product p on oi.product_id=p.id left join orders o on o.id=oi.order_id left join customer c on o.customer_id=c.id where o.customer_id=:id",nativeQuery = true)
    List<JoinResponse> singleOrder(@Param("id") int id);

    // GET ALL ELEMENTS
    @Query(value = "SELECT * FROM orders", nativeQuery = true)
    List<Order> allOrders();


}

