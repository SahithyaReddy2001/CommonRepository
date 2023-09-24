package Demo.CRUDoperations.controller;

import Demo.CRUDoperations.apiresponse.ApiResponse;
import Demo.CRUDoperations.dto.request.ProductRequest;
import Demo.CRUDoperations.service.ProductService;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;


@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping(value = "/file")
    public ApiResponse fileUpload(@RequestBody MultipartFile file) throws IOException {
        return productService.fileData(file);

    }
    @GetMapping("/file")
    public ResponseEntity<InputStreamResource> fileDownload() throws IOException{
        ByteArrayInputStream data=productService.getFile();
        InputStreamResource inputStreamResource=new InputStreamResource(data);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachement;filename=products.xlsx").contentType( MediaType.parseMediaType("application/vnd.ms-excel")).body(inputStreamResource);
        //new ApiResponse(HttpStatus.OK.value(), ,"File is downloaded",true);
    }

    @GetMapping
    public ApiResponse getProducts() {
        return productService.getProducts();
    }

    @GetMapping(value = "/{id}")
    public ApiResponse getProductById(@Valid @PathVariable int id) throws Exception {
        return productService.getProduct(id);
    }

    @PostMapping
    public ApiResponse creatingProduct(@Valid @RequestBody ProductRequest productRequest) throws Exception {

        return productService.consumer(productRequest);
    }

    @DeleteMapping(value = "/{id}")
    public ApiResponse deleteProduct(@PathVariable int id) throws Exception {

        productService.deleteProduct(id);
        return productService.getProducts();
    }

    @PutMapping
    public ApiResponse updateProduct(@Valid @RequestBody ProductRequest productRequest) throws Exception {
        return productService.consumer(productRequest);
    }

    @GetMapping(value = "orders/{id}")
    public ApiResponse getOrdersByProduct(@PathVariable int id){
        return productService.getProductOrders(id);
    }


    @GetMapping("/{offset}/{limit}")
    public ApiResponse getProductsByOffset(@PathVariable int offset,@PathVariable int limit){
        return productService.getProductsByOffset(offset,limit);
    }

    @GetMapping("/sort/{sort}")
    public  ApiResponse sortByName(@PathVariable String sort){
        return productService.sortByName(sort);
    }

    @GetMapping("/report")
    public  ApiResponse forEmailReport(@RequestParam String email){
        return productService.forEmailReport(email);
    }

}
