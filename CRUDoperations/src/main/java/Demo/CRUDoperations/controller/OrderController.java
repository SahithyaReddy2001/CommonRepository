package Demo.CRUDoperations.controller;

import Demo.CRUDoperations.dto.request.OrderRequest;
import Demo.CRUDoperations.dto.response.OrderResponse;
import Demo.CRUDoperations.entity.Orders;
import Demo.CRUDoperations.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderService orderServiceImps;

    @PostMapping
    public ResponseEntity<OrderResponse> saveOrders(@RequestBody OrderRequest orderRequest){
        return  orderServiceImps.createOrders(orderRequest);
    }

    @GetMapping
    public List<OrderResponse> getOrders(){
         return orderServiceImps.getOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable int id) {
        return orderServiceImps.getAllOrders(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrders(@PathVariable int id, @RequestBody OrderRequest orderRequest) {
        return orderServiceImps.updateOrders(id,orderRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<OrderResponse>> deleteOrders(@PathVariable int id ){
        orderServiceImps.deleteOrders(id);
        return ResponseEntity.status(HttpStatus.OK).body(orderServiceImps.getOrders());
    }

}
