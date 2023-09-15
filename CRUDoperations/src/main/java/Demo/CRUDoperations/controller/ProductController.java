package Demo.CRUDoperations.controller;

import Demo.CRUDoperations.apiresponse.ApiResponse;
import Demo.CRUDoperations.dto.request.ProductRequest;
import Demo.CRUDoperations.dto.response.ProductResponse;
import Demo.CRUDoperations.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public ApiResponse getProducts() {
        return  productService.getProducts();
    }

    @GetMapping(value = "/{id}")
    public ApiResponse getProductById(@Valid @PathVariable int id) throws Exception {
        return productService.getProduct(id);
    }

    @PostMapping
    public ApiResponse creatingProduct(@Valid @RequestBody ProductRequest productRequest) throws Exception {
        return productService.upsert(productRequest);
    }

    @DeleteMapping(value = "/{id}")
    public ApiResponse deleteProduct(@PathVariable int id) throws Exception {
        productService.deleteProduct(id);
        return productService.getProducts();
    }

    @PutMapping
    public ApiResponse updateProduct(@Valid @RequestBody ProductRequest productRequest) throws Exception {
        return productService.upsert(productRequest);
    }


}
