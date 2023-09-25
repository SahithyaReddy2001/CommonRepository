package Demo.CRUDoperations.repository;

import Demo.CRUDoperations.dto.response.JoinResponse;
import Demo.CRUDoperations.dto.response.OrderResponse;
import Demo.CRUDoperations.entity.Orders;
import Demo.CRUDoperations.entity.Product;
import Demo.CRUDoperations.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    // FIND BY STATUS
    @Query(value = "SELECT ord.id as order_id, ord.status, p.id as product_id, p.name, p.price, p.tax as productName FROM orders ord INNER JOIN product p ON ord.product_id=p.id and ord.status='ACTIVE' order by ord.id desc", nativeQuery = true)
    List<Object[]> findByStatus();

    // GET SINGLE ELEMENT
    @Query(value = "SELECT ord.id as order_id, ord.status, p.id as product_id, p.name as productName ,p.price,p.tax FROM orders ord INNER JOIN product p ON ord.product_id=p.id and ord.id= :id", nativeQuery = true)
    List<Object[]> singleOrder(@Param("id") int id);

    // GET ALL ELEMENTS
    @Query(value = "SELECT * FROM orders", nativeQuery = true)
    List<Orders> allOrders();


}

