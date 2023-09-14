package Demo.CRUDoperations.service.impl;

import Demo.CRUDoperations.dto.request.OrderRequest;
import Demo.CRUDoperations.dto.response.OrderResponse;
import Demo.CRUDoperations.entity.Orders;
import Demo.CRUDoperations.entity.Status;
import Demo.CRUDoperations.repository.OrdersRepository;
import Demo.CRUDoperations.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrdersRepository ordersRepository;


    public List<OrderResponse> getOrders(){

        List<Orders> orders=ordersRepository.findAll();
        List<OrderResponse> orderResponses=new ArrayList<>();
        for (Orders o:orders) {
            orderResponses.add(new OrderResponse(o));
        }
        return orderResponses;
    }

    public ResponseEntity<OrderResponse> createOrders(OrderRequest orderRequest) {
        Orders orders=createEntity(orderRequest);
        return  new ResponseEntity<>(new OrderResponse(ordersRepository.save(orders)), HttpStatus.CREATED);
    }

    public ResponseEntity<OrderResponse> getAllOrders(int id) {
        Optional<Orders> orders = ordersRepository.findById(id);
        if(orders.isPresent()){
            return new ResponseEntity<>(new OrderResponse(orders.get()),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<OrderResponse> updateOrders(int id, OrderRequest orderRequest) {
       Orders orders=updateEntity(orderRequest,id);
        return new ResponseEntity<>(new OrderResponse(ordersRepository.save(orders)),HttpStatus.OK);

    }

    public void deleteOrders(int id) {
        /*Optional<Orders> orders = ordersRepository.findById(id);
        if(orders.isPresent()){
            ordersRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }*/
        System.out.println("okk");
        ordersRepository.save(deleteOrder(id));

    }

    private Orders deleteOrder(int id) {
        Orders order=ordersRepository.findById(id).get();
        order.setStatus(Status.INACTIVE);
        System.out.println(order);
        return order;
    }

    private Orders createEntity(OrderRequest orderRequest){
        return  new Orders(orderRequest.productId,orderRequest.nonTaxAmount,orderRequest.taxAmount,orderRequest.status);
    }

    private Orders updateEntity(OrderRequest productRequest,int id) {
        Orders existingOrder = ordersRepository.findById(id).get();
        existingOrder.setProductId(productRequest.getProductId());
        existingOrder.setNonTaxAmount(productRequest.getNonTaxAmount());
        existingOrder.setTaxAmount(productRequest.getTaxAmount());
        existingOrder.setStatus(productRequest.getStatus());
        ordersRepository.save(existingOrder);
        return existingOrder;

    }
}
