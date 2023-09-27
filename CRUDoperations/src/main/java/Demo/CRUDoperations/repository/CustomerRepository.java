package Demo.CRUDoperations.repository;

import Demo.CRUDoperations.entity.Customer;
import Demo.CRUDoperations.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
