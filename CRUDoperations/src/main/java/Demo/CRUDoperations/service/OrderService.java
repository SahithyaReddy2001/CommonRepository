package Demo.CRUDoperations.service;

import Demo.CRUDoperations.apiresponse.ApiResponse;
import Demo.CRUDoperations.dto.request.PostRequest;
import Demo.CRUDoperations.dto.request.UpdateOrderRequest;
import Demo.CRUDoperations.elasticEntity.ElasticOrder;
import Demo.CRUDoperations.entity.Order;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public interface OrderService {
    ApiResponse getOrders();

    ApiResponse createOrders(PostRequest postRequest);

    ApiResponse getAllOrders(int id);

    ApiResponse updateOrders(UpdateOrderRequest updateOrderRequest);

    ApiResponse deleteOrders(int id);

    List<ElasticOrder> elasticOrders(String category);

    ByteArrayInputStream downloadOrders() throws IOException;

    void sendMail(String toEmail, String Subject, String body);

    //ApiResponse findOrdersWIthPaginationAndSorting(int offset,int pageSize,String field);


}
