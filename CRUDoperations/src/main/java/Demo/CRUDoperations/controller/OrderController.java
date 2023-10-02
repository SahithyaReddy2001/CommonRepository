package Demo.CRUDoperations.controller;

import Demo.CRUDoperations.apiresponse.ApiResponse;
import Demo.CRUDoperations.dto.request.PostRequest;
import Demo.CRUDoperations.dto.request.UpdateOrderRequest;
import Demo.CRUDoperations.elasticEntity.ElasticOrder;
import Demo.CRUDoperations.entity.Order;
import Demo.CRUDoperations.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.cache.annotation.Cacheable;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/orders")
@EnableCaching
public class OrderController {
    @Autowired
    OrderService orderServiceImps;

    @PostMapping()
    public ApiResponse saveOrders(@RequestBody PostRequest postRequest){
        return orderServiceImps.createOrders(postRequest);
    }

    @GetMapping
    public ApiResponse getOrders(){
        return orderServiceImps.getOrders();
    }

    @GetMapping("/{id}")
    public ApiResponse getOrder(@PathVariable int id) {
        return orderServiceImps.getAllOrders(id);
    }

    @GetMapping("/elastic/{category}")
    public List<ElasticOrder> elasticOrders(@PathVariable String category){
        return orderServiceImps.elasticOrders(category);
    }

    /*@GetMapping("{offset}/{pageSize}/{field}")
    public ApiResponse paginationAndSorting(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field){
        return orderServiceImps.findOrdersWIthPaginationAndSorting(offset,pageSize,field);
    }*/

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
