package Demo.CRUDoperations.apiresponse;

import Demo.CRUDoperations.dto.response.ProductResponse;
import Demo.CRUDoperations.entity.Product;

public class ApiResponse {
    int statuscode;
    Object data;
    String StatusMessage;

    Boolean success;
    public ApiResponse(){

    }

    public ApiResponse(int statuscode, Object data, String statusMessage,Boolean success) {
        this.statuscode = statuscode;
        this.data = data;
        StatusMessage = statusMessage;
        this.success=success;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getStatusMessage() {
        return StatusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        StatusMessage = statusMessage;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "statuscode=" + statuscode +
                ", data=" + data +
                ", StatusMessage='" + StatusMessage + '\'' +
                ", success=" + success +
                '}';
    }
}
