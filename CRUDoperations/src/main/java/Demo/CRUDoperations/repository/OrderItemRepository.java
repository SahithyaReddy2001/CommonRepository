package Demo.CRUDoperations.repository;

import Demo.CRUDoperations.entity.Customer;
import Demo.CRUDoperations.entity.Order;
import Demo.CRUDoperations.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    void deleteByOrder(Order order);


    @Query(value = "select product_id from order_item where order_id=:id",nativeQuery = true)
    List<Integer> findByOrderId(@Param("id") int id);

}
