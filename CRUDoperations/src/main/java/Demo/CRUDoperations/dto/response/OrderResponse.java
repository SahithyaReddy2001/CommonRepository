package Demo.CRUDoperations.dto.response;

import Demo.CRUDoperations.dto.request.OrderRequest;
import Demo.CRUDoperations.entity.Orders;
import Demo.CRUDoperations.entity.Status;

public class OrderResponse extends OrderRequest {
    public OrderResponse(){
        super();
    }

    public ProductResponse productResponse;
    public OrderResponse(Orders orders){
        this.id = orders.getId();
        this.productId= orders.getProduct().getId();
        this.nonTaxAmount = orders.getNonTaxAmount();
        this.taxAmount = orders.getTaxAmount();
        this.status = orders.getStatus();
    }


}




