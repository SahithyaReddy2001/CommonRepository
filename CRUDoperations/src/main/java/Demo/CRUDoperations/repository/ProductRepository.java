package Demo.CRUDoperations.repository;

import Demo.CRUDoperations.entity.Orders;
import Demo.CRUDoperations.entity.Product;
import Demo.CRUDoperations.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>{
    Optional<Product> findById(long productId);
}
