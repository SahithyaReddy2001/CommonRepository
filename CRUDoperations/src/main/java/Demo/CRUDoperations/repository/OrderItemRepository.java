package Demo.CRUDoperations.repository;

import Demo.CRUDoperations.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {
}
