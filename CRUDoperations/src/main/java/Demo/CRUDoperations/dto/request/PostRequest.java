package Demo.CRUDoperations.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostRequest {
    public String name;
    public String email;
    public int[] productId;
}
