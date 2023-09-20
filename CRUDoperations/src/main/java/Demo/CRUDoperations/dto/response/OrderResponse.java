package Demo.CRUDoperations.dto.response;

import Demo.CRUDoperations.dto.request.OrderRequest;
import Demo.CRUDoperations.dto.request.ProductRequest;
import Demo.CRUDoperations.entity.Orders;
import Demo.CRUDoperations.entity.Product;

public class OrderResponse extends OrderRequest {

    public OrderResponse(){
        super();
    }
    public OrderResponse(Orders orders){
        this.id = orders.getId();
        this.product= orders.getProduct();
        this.nonTaxAmount = orders.getNonTaxAmount();
        this.taxAmount = orders.getTaxAmount();
        this.status = orders.getStatus();

    }
}




