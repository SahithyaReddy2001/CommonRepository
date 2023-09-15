package Demo.CRUDoperations.controller;

import Demo.CRUDoperations.apiresponse.ApiResponse;
import Demo.CRUDoperations.dto.request.OrderRequest;
import Demo.CRUDoperations.dto.response.OrderResponse;
import Demo.CRUDoperations.entity.Orders;
import Demo.CRUDoperations.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderService orderServiceImps;

    @PostMapping
    public ApiResponse saveOrders(@Valid @RequestBody OrderRequest orderRequest){
        return  orderServiceImps.createOrders(orderRequest);
    }

    @GetMapping
    public ApiResponse getOrders(){
        return orderServiceImps.getOrders();
    }

    @GetMapping("/{id}")
    public ApiResponse getOrder(@PathVariable int id) {
        return orderServiceImps.getAllOrders(id);
    }

    @PutMapping("/{id}")
    public ApiResponse updateOrders(@PathVariable int id, @Valid @RequestBody OrderRequest orderRequest) {
        return orderServiceImps.updateOrders(id,orderRequest);
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteOrders(@PathVariable int id ){
        orderServiceImps.deleteOrders(id);
        return orderServiceImps.getOrders();
    }

}
