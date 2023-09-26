package Demo.CRUDoperations.dto.response;
import Demo.CRUDoperations.dto.request.OrderRequest;
import Demo.CRUDoperations.dto.request.ProductRequest;
import Demo.CRUDoperations.dto.response.ProductResponse;
import Demo.CRUDoperations.entity.Status;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class JoinResponse{
    private Integer orderId;
    private String   orderStatus;
    private Integer productId;
    private String productName;
    private float price;
    private float tax;

}







