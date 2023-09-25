package Demo.CRUDoperations.apiresponse;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApiResponse {
    int statuscode;
    Object data;
    String StatusMessage;
    Boolean success;
}

