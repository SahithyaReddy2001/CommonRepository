package Demo.CRUDoperations.service;

import Demo.CRUDoperations.dto.request.ProductRequest;
import Demo.CRUDoperations.dto.response.ProductResponse;
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
