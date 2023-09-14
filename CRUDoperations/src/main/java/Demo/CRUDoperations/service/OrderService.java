package Demo.CRUDoperations.service;

import Demo.CRUDoperations.dto.request.OrderRequest;
import Demo.CRUDoperations.dto.response.OrderResponse;
import Demo.CRUDoperations.entity.Orders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    List<OrderResponse> getOrders();

    ResponseEntity<OrderResponse> createOrders(OrderRequest orderRequest);

    ResponseEntity<OrderResponse> getAllOrders(int id);

    ResponseEntity<OrderResponse> updateOrders(int id, OrderRequest orderRequest);

    void deleteOrders(int id);
}
