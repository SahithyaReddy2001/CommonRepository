package com.RestTemplate.demo.controller;

import com.RestTemplate.demo.constraints.Constrain;
import com.RestTemplate.demo.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.RestTemplate.demo.service.RestTemplateService;

@RestController
@RequestMapping(Constrain.resttemplate)
public class RestTemplateController {
    @Autowired
    // RestTemplateService restTemplateService;
    RestTemplateService restTemplateService;

    @GetMapping
    public ResponseEntity<String> getAllOrders(){
        return restTemplateService.allOrders();
    }
    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order){
        return restTemplateService.postOrder(order);
    }
}
