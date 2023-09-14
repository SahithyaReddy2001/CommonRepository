package Demo.CRUDoperations.controller;

import Demo.CRUDoperations.entity.Orders;
import Demo.CRUDoperations.service.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderServices orderServiceImps;

    @PostMapping
    public ResponseEntity<Demo.CRUDoperations.entity.Orders> saveOrders(@RequestBody Orders orders){
        return  orderServiceImps.createOrders(orders);
    }

    @GetMapping
    public List<Orders> saveOrders(){
         return orderServiceImps.getOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Demo.CRUDoperations.entity.Orders> getOrders(@PathVariable int id) {
        return orderServiceImps.getAllOrders(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orders> updateOrders(@PathVariable int id, @RequestBody Orders order) {
        return orderServiceImps.updateOrders(id,order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Orders> deleteOrders(@PathVariable int id ){
        return orderServiceImps.deleteOrders(id);
    }

}
