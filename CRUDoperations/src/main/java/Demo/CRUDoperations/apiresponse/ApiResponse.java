package Demo.CRUDoperations.apiresponse;

import Demo.CRUDoperations.dto.response.ProductResponse;
import Demo.CRUDoperations.entity.Product;

public class ApiResponse<T> {
    int statuscode;
    T data;
    String StatusMessage;

    public ApiResponse(){

    }

    public ApiResponse(int statuscode, T data, String statusMessage) {
        this.statuscode = statuscode;
        this.data = data;
        StatusMessage = statusMessage;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getStatusMessage() {
        return StatusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        StatusMessage = statusMessage;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "statuscode=" + statuscode +
                ", data=" + data +
                ", StatusMessage='" + StatusMessage + '\'' +
                '}';
    }
}
