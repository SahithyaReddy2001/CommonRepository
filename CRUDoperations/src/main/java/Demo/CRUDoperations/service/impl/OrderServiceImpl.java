package Demo.CRUDoperations.service.impl;

import Demo.CRUDoperations.apiresponse.ApiResponse;
import Demo.CRUDoperations.download.ExcelDownload;
import Demo.CRUDoperations.dto.request.OrderRequest;
import Demo.CRUDoperations.dto.response.OrderResponse;
import Demo.CRUDoperations.entity.Orders;
import Demo.CRUDoperations.entity.Status;
import Demo.CRUDoperations.repository.OrdersRepository;
import Demo.CRUDoperations.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrdersRepository ordersRepository;

    //GET REQUEST
    public ApiResponse getOrders(){

        List<Orders> orders=ordersRepository.findByStatus(Status.ACTIVE);
        List<OrderResponse> orderResponses=new ArrayList<>();
        for (Orders o:orders) {
            orderResponses.add(new OrderResponse(o));
        }
        return new ApiResponse(HttpStatus.OK.value(),orderResponses,HttpStatus.OK.getReasonPhrase(),true);
    }

    //POST REQUEST
    public ApiResponse createOrders(OrderRequest orderRequest) {
        Orders orders=createEntity(orderRequest);
        ordersRepository.save(orders);
        return new ApiResponse(HttpStatus.OK.value(),new OrderResponse(orders),HttpStatus.CREATED.getReasonPhrase(),true);
    }

    //GET REQUEST BY ID
    public ApiResponse getAllOrders(int id) {
        Optional<Orders> orders = ordersRepository.findById(id);
        if(orders.isPresent()){
            return new ApiResponse(HttpStatus.OK.value(),new OrderResponse(orders.get()),HttpStatus.OK.getReasonPhrase(),true);
        }
        else{
            return new ApiResponse(HttpStatus.BAD_REQUEST.value(),null,"order not exist in system with given id "+id, false);
        }

    }

    //DELETE REQUEST
    public ApiResponse deleteOrders(int id) {
        /*Optional<Orders> orders = ordersRepository.findById(id);
        if(orders.isPresent()){
            ordersRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }*/
        ordersRepository.save(deleteOrder(id));
        return new ApiResponse(HttpStatus.OK.value(),ordersRepository.findAll().stream().map(n->new OrderResponse(n)).collect(Collectors.toList()),HttpStatus.OK.getReasonPhrase(),true);
    }

    private Orders deleteOrder(int id) {
        Orders order=ordersRepository.findById(id).get();
        order.setStatus(Status.INACTIVE);
        return order;
    }

    private Orders createEntity(OrderRequest orderRequest){
        return  new Orders(orderRequest.productId,orderRequest.nonTaxAmount,orderRequest.taxAmount,orderRequest.status);
    }

    //PUT REQUEST
    public ApiResponse updateOrders(OrderRequest orderRequest) {
        Orders orders = updateEntity(orderRequest);
        return new ApiResponse(HttpStatus.OK.value(),new OrderResponse(orders),HttpStatus.OK.getReasonPhrase(),true);

    }
    private Orders updateEntity(OrderRequest orderRequest) {
        Orders existingOrder = (null == orderRequest.getId() ? new Orders() : ordersRepository.getById(orderRequest.getId()));
        existingOrder.setProductId(orderRequest.getProductId());
        existingOrder.setNonTaxAmount(orderRequest.getNonTaxAmount());
        existingOrder.setTaxAmount(orderRequest.getTaxAmount());
        existingOrder.setStatus(Status.ACTIVE);
        ordersRepository.save(existingOrder);
        return existingOrder;
    }

    //DOWNLOAD ORDERS DETAILS TO EXCEL
    public ByteArrayInputStream downloadOrders() throws IOException {
        List<Orders> orders = ordersRepository.findAll();
        ByteArrayInputStream data = ExcelDownload.orderDownload(orders);
        return data;
    }
}
