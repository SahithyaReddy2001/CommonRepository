package Demo.CRUDoperations.controller;

import Demo.CRUDoperations.apiresponse.ApiResponse;
import Demo.CRUDoperations.dto.request.ProductRequest;
import Demo.CRUDoperations.dto.response.ProductResponse;
import Demo.CRUDoperations.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public ApiResponse<List<ProductResponse>> getProducts() {
        return  productService.getProducts();
    }

    @GetMapping(value = "/{id}")
    public ApiResponse getPaticularProduct(@PathVariable int id) {
        return productService.getProduct(id);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> creatingProduct(@RequestBody ProductRequest productRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productRequest));
    }

    @DeleteMapping(value = "/{id}")
    public ApiResponse<List<ProductResponse>> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return productService.getProducts();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable int id, @RequestBody ProductRequest productRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(id, productRequest));
    }


}
