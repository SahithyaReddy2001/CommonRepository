package Demo.CRUDoperations.service;

import Demo.CRUDoperations.apiresponse.ApiResponse;
import Demo.CRUDoperations.dto.request.ProductRequest;
import Demo.CRUDoperations.dto.response.Data;
import Demo.CRUDoperations.dto.response.GetOrders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public interface ProductService {
    ApiResponse getProducts();
    //ApiResponse upsert(ProductRequest productRequest) throws Exception;

    ApiResponse consumer(ProductRequest productRequest);

    ApiResponse deleteProduct(int id);

    ByteArrayInputStream getFile() throws IOException;

    ApiResponse getProduct(int id) throws Exception;

    ApiResponse fileData(MultipartFile file) throws IOException;

/*    ApiResponse getProductOrders(int id);*/

    ApiResponse getProductsByOffset(int offset, int limit);

    ApiResponse sortByName(String sort);

    ApiResponse forEmailReport(String email);

    List<Data> complerereport();

    ByteArrayInputStream getReport() throws IOException;
}
