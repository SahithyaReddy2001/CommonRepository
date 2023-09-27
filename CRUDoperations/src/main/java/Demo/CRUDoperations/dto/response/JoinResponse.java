package Demo.CRUDoperations.dto.response;
import Demo.CRUDoperations.dto.request.OrderRequest;
import Demo.CRUDoperations.dto.request.ProductRequest;
import Demo.CRUDoperations.dto.response.ProductResponse;
import Demo.CRUDoperations.entity.Status;
import lombok.*;


public interface JoinResponse{

    //p.id as productId,
    Integer getProductId();
    // p.name as productName,
    String getProductName();
    // p.price as productPrice,
    Float getProductPrice();
    // p.tax as productTax,
    Float getProductTax();
    // p.customer_id as productProvider
    Integer getProductProvider();

    //o.id as orderId,
    Integer getOrderId();

    // o.tax_amount as totalAmount,
    Float getTotalAmount();

    // o.non_tax_amount as amountWithOutTax,
    Float getAmountWithOutTax();

    // o.customer_id as CustomerId,
    Integer getCustomerId();

    // c.name as customerName,
    String getCustomerName();

    // c.email as customerMail
    String getCustomerMail();

}







