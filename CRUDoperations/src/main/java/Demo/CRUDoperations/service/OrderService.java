package Demo.CRUDoperations.service;

import Demo.CRUDoperations.apiresponse.ApiResponse;
import Demo.CRUDoperations.dto.request.OrderRequest;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
public interface OrderService {
    ApiResponse getOrders();

    ApiResponse createOrders(OrderRequest orderRequest);

    ApiResponse getAllOrders(int id);

    ApiResponse updateOrders(OrderRequest orderRequest);

    ApiResponse deleteOrders(int id);

    ByteArrayInputStream downloadOrders() throws IOException;
}
