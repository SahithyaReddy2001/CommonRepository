package Demo.CRUDoperations.model.Entities.dto.request.response;

import Demo.CRUDoperations.model.Entities.Product;
import Demo.CRUDoperations.model.Entities.dto.request.ProductRequest;

public class ProductResponse extends ProductRequest {
    public int id;
    public ProductResponse(Product save) {
        this.id=save.getId();
        this.name= save.getName();
        this.price= save.getPrice();
        this.tax= save.getTax();
    }
}
