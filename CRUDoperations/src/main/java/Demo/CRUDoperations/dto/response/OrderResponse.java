package Demo.CRUDoperations.dto.response;

import Demo.CRUDoperations.dto.request.OrderRequest;
import Demo.CRUDoperations.entity.Order;

public class OrderResponse extends OrderRequest {
    public OrderResponse(){
        super();
    }

    public ProductResponse productResponse;
    public OrderResponse(Order order){
        this.id = order.getId();
        this.customer= order.getCustomer();
        this.nonTaxAmount = order.getNonTaxAmount();
        this.taxAmount = order.getTaxAmount();
        this.status = order.getStatus();
    }


}




