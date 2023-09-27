package Demo.CRUDoperations.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateOrderRequest {
    public Integer orderId;
    public int[] product_ids;
}
