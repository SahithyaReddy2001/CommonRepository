package Demo.CRUDoperations.services;

import Demo.CRUDoperations.model.Entities.Product;
import Demo.CRUDoperations.model.Entities.dto.request.ProductRequest;
import Demo.CRUDoperations.model.Entities.dto.response.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProductService {
    List<ProductResponse> getProducts();
    ProductResponse createProduct(ProductRequest productRequest);
    void deleteProduct(int id);
    ProductResponse updateProduct(int id,ProductRequest productRequest);
    ProductResponse getProduct(int id);

}
