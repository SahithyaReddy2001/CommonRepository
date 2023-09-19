package Demo.CRUDoperations.service;

import Demo.CRUDoperations.apiresponse.ApiResponse;
import Demo.CRUDoperations.dto.request.ProductRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public interface ProductService {
    ApiResponse getProducts();
    ApiResponse upsert(ProductRequest productRequest) throws Exception;
    ApiResponse deleteProduct(int id);

    ByteArrayInputStream getFile()throws IOException;

    ApiResponse getProduct(int id) throws Exception;

    ApiResponse fileData(MultipartFile file) throws IOException;
}
