package Demo.CRUDoperations.service;

import Demo.CRUDoperations.apiresponse.ApiResponse;
import Demo.CRUDoperations.dto.request.OrderRequest;
import Demo.CRUDoperations.dto.response.OrderResponse;
import Demo.CRUDoperations.entity.Orders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    ApiResponse getOrders();

    ApiResponse createOrders(OrderRequest orderRequest);

    ApiResponse getAllOrders(int id);

    ApiResponse updateOrders(int id, OrderRequest orderRequest);

    ApiResponse deleteOrders(int id);
}
