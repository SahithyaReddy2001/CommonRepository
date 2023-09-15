package Demo.CRUDoperations.service.impl;

import Demo.CRUDoperations.apiresponse.ApiResponse;
import Demo.CRUDoperations.dto.request.ProductRequest;
import Demo.CRUDoperations.dto.response.ProductResponse;
import Demo.CRUDoperations.entity.Product;
import Demo.CRUDoperations.entity.Status;
import Demo.CRUDoperations.repository.ProductRepository;
import Demo.CRUDoperations.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    public ApiResponse getProducts() {
        List<ProductResponse> productResponses = productRepository.findByStatus(Status.ACTIVE)
                .stream().map(ProductResponse::new).collect(Collectors.toList());
        return new ApiResponse(HttpStatus.OK.value(), productResponses, HttpStatus.OK.getReasonPhrase(), true);
    }


    public ApiResponse deleteProduct(int id)  {

        Optional<Product> optionalProduct = Optional.of(getProductById(id));
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setStatus(Status.INACTIVE);
            productRepository.save(product);
            return getProducts();
        }

        return new ApiResponse(HttpStatus.BAD_REQUEST.value(), null, HttpStatus.BAD_REQUEST.getReasonPhrase(), false);
    }


    public ApiResponse getProduct(int id)  {
        Product product = getProductById(id);
        ProductResponse productResponse = new ProductResponse(product);
        return new ApiResponse(HttpStatus.OK.value(), productResponse, HttpStatus.OK.getReasonPhrase(), Boolean.TRUE);
    }


    public ApiResponse upsert(ProductRequest productRequest)  {

        Optional<Product> optionalProduct = Optional.empty();

        if (!ObjectUtils.isEmpty(productRequest.getId())) {
            optionalProduct = Optional.of(getProductById(productRequest.id));
        }

        Product product = optionalProduct.orElse(new Product());
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setTax(productRequest.getTax());
        product.setStatus(Status.ACTIVE);
        productRepository.save(product);
        return new ApiResponse(HttpStatus.CREATED.value(), new ProductResponse(product), HttpStatus.CREATED.getReasonPhrase(), true);
    }


    private Product getProductById(Integer id)  {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(!optionalProduct.isPresent())
            throw new NoSuchElementException("Inavlid product id");
        return optionalProduct.get();
    }

}
