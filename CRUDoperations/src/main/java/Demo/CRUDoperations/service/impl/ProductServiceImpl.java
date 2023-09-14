package Demo.CRUDoperations.service.impl;

import Demo.CRUDoperations.entity.Product;
import Demo.CRUDoperations.dto.request.ProductRequest;
import Demo.CRUDoperations.dto.response.ProductResponse;
import Demo.CRUDoperations.repository.ProductRepository;
import Demo.CRUDoperations.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    public List<ProductResponse> getProducts(){
       List<Product> products= productRepository.findAll();
       List<ProductResponse> productResponses=new ArrayList<>();
       for(Product p:products){
           productResponses.add(new ProductResponse(p));
       }
       return productResponses;
    }
    public ProductResponse createProduct(ProductRequest productRequest){

        Product product=createEntity(productRequest);
       return new ProductResponse(productRepository.save(product));
    }

    public void deleteProduct(int id){
        deleteProducts(id);
        //productRepository.deleteById(id);
    }

    private void deleteProducts(int id) {
        Product product=productRepository.findById(id).get();
    }

    public ProductResponse updateProduct(int id,ProductRequest productRequest) {
        Product product=updateEntity(productRequest,id);
        return new ProductResponse(product);
    }

    public ProductResponse getProduct(int id) {

         return new ProductResponse(productRepository.findById(id).get());

    }
    private Product createEntity(ProductRequest productRequest){
        return  new Product(productRequest.name,productRequest.price,productRequest.tax);
    }

    private Product updateEntity(ProductRequest productRequest,int id) {
        Product existingProduct = productRepository.findById(id).get();
            existingProduct.setName(productRequest.getName());
            existingProduct.setPrice(productRequest.getPrice());
            existingProduct.setTax(productRequest.getTax());
            productRepository.save(existingProduct);
            return existingProduct;

    }
}
