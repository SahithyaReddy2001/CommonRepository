package Demo.CRUDoperations.service;

import Demo.CRUDoperations.entity.Orders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderServices {
    List<Demo.CRUDoperations.entity.Orders> getOrders();

    ResponseEntity<Orders> createOrders(Demo.CRUDoperations.entity.Orders orders);

    ResponseEntity<Orders> getAllOrders(int id);

    ResponseEntity<Orders> updateOrders(int id, Orders order);

    ResponseEntity<Orders> deleteOrders(int id);
}
