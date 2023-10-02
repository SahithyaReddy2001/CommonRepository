package Demo.CRUDoperations.dto.request;

import lombok.Data;

@Data
public class ElasticOrderRequest {
    public String id;
    public String customerId;
    public String taxAmount;
    public String nonTaxAmount;
}
