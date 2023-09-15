package Demo.CRUDoperations.dto.response;

import Demo.CRUDoperations.entity.Product;
import Demo.CRUDoperations.dto.request.ProductRequest;
import lombok.*;

@Setter
@Getter
@ToString
public class ProductResponse extends ProductRequest {

    public ProductResponse(){
        super();
    }
    public ProductResponse(Product save) {
        this.id=save.getId();
        this.name= save.getName();
        this.price= save.getPrice();
        this.tax= save.getTax();
        this.status=save.getStatus();
    }
}
