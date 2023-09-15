package Demo.CRUDoperations.apiresponse;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"statuscode","statusMessage","success","data"})
public class ApiResponse {
    int statuscode;
    Object data;
    String StatusMessage;

    Boolean success;


}
