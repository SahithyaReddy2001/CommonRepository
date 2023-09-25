package Demo.CRUDoperations.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateOrderRequest {
    public Integer order_id;
    public Integer product_id;
}
