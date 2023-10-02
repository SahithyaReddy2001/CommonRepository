package Demo.CRUDoperations.repository;

import Demo.CRUDoperations.dto.response.JoinResponse;
import Demo.CRUDoperations.entity.Customer;
import Demo.CRUDoperations.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query(value = "select * from customer where email=:email",nativeQuery = true)
    Customer findByEmail(@Param("email") String email);
}
