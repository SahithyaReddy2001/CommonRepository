package Demo.CRUDoperations.service.impl;

import Demo.CRUDoperations.apiresponse.ApiResponse;
import Demo.CRUDoperations.entity.Product;
import Demo.CRUDoperations.dto.request.ProductRequest;
import Demo.CRUDoperations.dto.response.ProductResponse;
import Demo.CRUDoperations.entity.Status;
import Demo.CRUDoperations.repository.ProductRepository;
import Demo.CRUDoperations.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    public ApiResponse<List<ProductResponse>> getProducts(){
       List<Product> products= productRepository.findByStatus(Status.ACTIVE);
       List<ProductResponse> productResponses = products.stream().map(ProductResponse::new).collect(Collectors.toList());
       return new ApiResponse<List<ProductResponse>>(HttpStatus.OK.value(), productResponses,HttpStatus.OK.getReasonPhrase());
    }
    public ProductResponse createProduct(ProductRequest productRequest){

        Product product=createEntity(productRequest);
        System.out.println(product);
       return new ProductResponse(productRepository.save(product));
    }

    public void deleteProduct(int id){
        productRepository.save(deleteProducts(id));
        //productRepository.deleteById(id);
    }

    private Product deleteProducts(int id) {
        // valida
        //

        Product product=productRepository.findById(id).get();

        product.setStatus(Status.INACTIVE);
        return product;
    }

    public ProductResponse updateProduct(int id,ProductRequest productRequest) {
        Product product=updateEntity(productRequest,id);
        return new ProductResponse(product);
    }

    public ApiResponse getProduct(int id) {
        ProductResponse productResponse=new ProductResponse(productRepository.findById(id).get());
         return new ApiResponse(HttpStatus.OK.value(),productResponse,HttpStatus.OK.getReasonPhrase());

    }
    private Product createEntity(ProductRequest productRequest){
        return  new Product(productRequest.name,productRequest.price,productRequest.tax,productRequest.status);
    }

    private Product updateEntity(ProductRequest productRequest,int id) {
        Product existingProduct = productRepository.findById(id).orElse(new Product());
            existingProduct.setName(productRequest.getName());
            existingProduct.setPrice(productRequest.getPrice());
            existingProduct.setTax(productRequest.getTax());
            existingProduct.setStatus(productRequest.getStatus());
            productRepository.save(existingProduct);
            return existingProduct;

    }
}
