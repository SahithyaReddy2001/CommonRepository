package Demo.CRUDoperations.repository;

import Demo.CRUDoperations.entity.Order;
import Demo.CRUDoperations.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    void deleteByOrder(Order order);

}
