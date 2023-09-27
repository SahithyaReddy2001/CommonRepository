package Demo.CRUDoperations.dto.request;


import Demo.CRUDoperations.entity.Status;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
@Setter @Getter
@ToString @NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    public Integer id;
    @NotBlank
    public String name;
    @Min(1)
    public Float price;
    public Float tax;
    public Status status;
    public Integer customerId;


}
