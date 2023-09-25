package Demo.CRUDoperations.controller;

import Demo.CRUDoperations.apiresponse.ApiResponse;
import Demo.CRUDoperations.dto.request.UpdateOrderRequest;
import Demo.CRUDoperations.dto.response.OrderResponse;
import Demo.CRUDoperations.entity.Orders;
import Demo.CRUDoperations.entity.Status;
import Demo.CRUDoperations.service.OrderService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderService orderServiceImps;

    @PostMapping("/{id}")
    public void saveOrders(@PathVariable int id){
        orderServiceImps.createOrders(id);
    }

    @GetMapping
    public ApiResponse getOrders(){
        return orderServiceImps.getOrders();
    }

    @GetMapping("/{id}")
    public ApiResponse getOrder(@PathVariable int id) {
        return orderServiceImps.getAllOrders(id);
    }

    @GetMapping("{offset}/{pageSize}/{field}")
    public ApiResponse paginationAndSorting(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field){
        return orderServiceImps.findOrdersWIthPaginationAndSorting(offset,pageSize,field);
    }

    @PutMapping
    public ApiResponse updateOrders(@Valid @RequestBody UpdateOrderRequest updateOrderRequest) {
        return orderServiceImps.updateOrders(updateOrderRequest);
    }


    @DeleteMapping("/{id}")
    public ApiResponse deleteOrders(@PathVariable int id ){
        orderServiceImps.deleteOrders(id);
        return orderServiceImps.getOrders();
    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> download() throws IOException {
        String fileName = "Orders.xlsx";
        ByteArrayInputStream inputStream = orderServiceImps.downloadOrders();
        InputStreamResource response = new InputStreamResource(inputStream);
        ResponseEntity<InputStreamResource> responseEntity =ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachement;filename="+fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(response);
        return responseEntity;
    }


}
