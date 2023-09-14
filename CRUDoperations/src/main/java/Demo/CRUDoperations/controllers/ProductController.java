package Demo.CRUDoperations.controllers;

import Demo.CRUDoperations.model.Entities.dto.request.ProductRequest;
import Demo.CRUDoperations.model.Entities.dto.response.ProductResponse;
import Demo.CRUDoperations.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts() {
        return  ResponseEntity.status(HttpStatus.OK).body(productService.getProducts());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductResponse> getPaticularProduct(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProduct(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> creatingProduct(@RequestBody ProductRequest productRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productRequest));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<List<ProductResponse>> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProducts());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable int id, @RequestBody ProductRequest productRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(id, productRequest));
    }


}
