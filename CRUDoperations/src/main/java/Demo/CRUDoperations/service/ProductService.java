package Demo.CRUDoperations.service;

import Demo.CRUDoperations.apiresponse.ApiResponse;
import Demo.CRUDoperations.dto.request.ProductRequest;
import Demo.CRUDoperations.dto.response.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProductService {
    ApiResponse getProducts();
    ApiResponse upsert(ProductRequest productRequest) throws Exception;
    ApiResponse deleteProduct(int id) throws Exception;

    ApiResponse getProduct(int id) throws Exception;

}
