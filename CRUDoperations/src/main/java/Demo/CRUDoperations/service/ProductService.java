package Demo.CRUDoperations.service;

import Demo.CRUDoperations.apiresponse.ApiResponse;
import Demo.CRUDoperations.dto.request.ProductRequest;
import Demo.CRUDoperations.dto.response.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProductService {
    ApiResponse<List<ProductResponse>>getProducts();
    ProductResponse createProduct(ProductRequest productRequest);
    void deleteProduct(int id);
    ProductResponse updateProduct(int id,ProductRequest productRequest);
    ApiResponse getProduct(int id);

}
