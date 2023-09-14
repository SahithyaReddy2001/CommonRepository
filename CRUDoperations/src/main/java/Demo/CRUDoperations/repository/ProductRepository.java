package Demo.CRUDoperations.repository;

import Demo.CRUDoperations.entity.Product;
import Demo.CRUDoperations.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>{

    List<Product> findByStatus(Status status);
}
