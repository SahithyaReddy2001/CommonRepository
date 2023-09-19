package com.RestTemplate.demo.service;

import com.RestTemplate.demo.constraints.Constrain;
import com.RestTemplate.demo.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class RestTemplateService {
    @Value("${RestTemplate.string.property}")
    private String reusableUrl;
    RestTemplate restTemplate = new RestTemplate();
    //private static final  String get_orders = "http://localhost:8080/orders";
    //private static final  String post_orders = "http://localhost:8080/orders";

    public ResponseEntity<String> allOrders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity("parameters",headers);
        //RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(reusableUrl, HttpMethod.GET,entity, String.class);
        return response;
    }

    public ResponseEntity<String> postOrder(Order order){
        return restTemplate.postForEntity(reusableUrl+ Constrain.resttemplate,order,String.class);
    }
}
