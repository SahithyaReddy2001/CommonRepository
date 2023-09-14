package Demo.CRUDoperations.services;

import Demo.CRUDoperations.Entities.Product;
import Demo.CRUDoperations.repositotries.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductRepository productRepository;
    public List<Product> getProducts(){
        return productRepository.findAll();
    }
    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    public void deleteProduct(int id){
        productRepository.deleteById(id);
    }

    public Product updateProcuct(int id,Product product) {
        Optional<Product> optional = productRepository.findById(id);
        Product existingProduct;
        if (optional.isPresent()) {
            existingProduct = optional.get();
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setTax(product.getTax());
            productRepository.save(product);
            return existingProduct;
        }
        return null;
    }

    public Product getProduct(int id) {
        //Optional<Product> optionalProduct=
         return productRepository.findById(id).get();
        /*if(optionalProduct.isPresent())
            return optionalProduct.get();
        else{
            //ResponseEntity.status(404).body("Id doesn't exist");
            return null;}*/
    }
}
