package Demo.CRUDoperations.controller;

import Demo.CRUDoperations.apiresponse.ApiResponse;
import Demo.CRUDoperations.dto.request.OrderRequest;
import Demo.CRUDoperations.dto.response.OrderResponse;
import Demo.CRUDoperations.entity.Orders;
import Demo.CRUDoperations.service.OrderService;
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

    @PutMapping
    public ApiResponse updateOrders(@Valid @RequestBody OrderRequest orderRequest) {
        return orderServiceImps.updateOrders(orderRequest);
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
