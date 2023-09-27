package Demo.CRUDoperations.service.impl;

import Demo.CRUDoperations.apiresponse.ApiResponse;
import Demo.CRUDoperations.download.ExcelDownload;
import Demo.CRUDoperations.dto.request.PostRequest;
import Demo.CRUDoperations.dto.request.UpdateOrderRequest;
import Demo.CRUDoperations.dto.response.JoinResponse;
import Demo.CRUDoperations.dto.response.OrderResponse;
import Demo.CRUDoperations.entity.*;
import Demo.CRUDoperations.repository.CustomerRepository;
import Demo.CRUDoperations.repository.OrderItemRepository;
import Demo.CRUDoperations.repository.OrdersRepository;
import Demo.CRUDoperations.repository.ProductRepository;
import Demo.CRUDoperations.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    JavaMailSender mailSender;

    //GET REQUEST
    public ApiResponse getOrders(){
        List<JoinResponse> joinResponse=ordersRepository.findByStatus();
        return new ApiResponse(HttpStatus.OK.value(),joinResponse,HttpStatus.OK.getReasonPhrase(),true);
    }

    //GET REQUEST BY ID
    public ApiResponse getAllOrders(int id) {
        List<JoinResponse> joinResponse = ordersRepository.singleOrder(id);
        if(joinResponse != null){
            return new ApiResponse(HttpStatus.OK.value(),joinResponse,HttpStatus.OK.getReasonPhrase(),true);
        }
        else{
            return new ApiResponse(HttpStatus.BAD_REQUEST.value(),null,"order not exist in system with given id "+id, false);
        }
    }

    // PAGINATION AND SORTING
    /*public ApiResponse findOrdersWIthPaginationAndSorting(int offset, int pageSize, String field){
        List<JoinResponse> joinResponse=new ArrayList<>();
        Page<Order> order = ordersRepository.findAll(PageRequest.of(offset,pageSize).withSort(Sort.by(field)));
        for(Order o:order){
            System.out.println(o);
            JoinResponse
            joinResponse1.setOrderId(o.getId());
            joinResponse1.setOrderStatus(String.valueOf(o.getProduct().getStatus()));
            joinResponse1.setProductId(o.getProduct().getId());
            joinResponse1.setProductName(o.getProduct().getName());
            joinResponse1.setTax((int) o.getProduct().getTax());
            joinResponse1.setPrice((int) o.getProduct().getPrice());
            System.out.println(joinResponse1);
            joinResponse.add(joinResponse1);
        }
        return new ApiResponse(HttpStatus.OK.value(),joinResponse,HttpStatus.OK.getReasonPhrase(),true);
    }*/

    //DELETE REQUEST
    public ApiResponse deleteOrders(int id) {
        ordersRepository.deleteById(id);
        return getAllOrders(id);
    }

    //POST REQUEST
    @Autowired
    KafkaTemplate<String,PostRequest> kaftaTemplate;

    public void createOrders(PostRequest postRequest){
        kaftaTemplate.send("NewOrder",postRequest);
    }
    @KafkaListener(topics = "NewOrder",groupId = "CreateOrder")
    public ApiResponse kafkaListener(PostRequest postRequest) {
        // SAVE CUSTOMER
        Customer customer = new Customer();
        customer.setName(postRequest.getName());
        customer.setEmail(postRequest.getEmail());
        customerRepository.save(customer);
        Double taxAmount = (double) 0;
        Double nonTaxAmount = (double) 0;
        for(Integer i: postRequest.getProductId()){
            Product product = productRepository.findById(i).get();
            taxAmount += product.getTax();
            nonTaxAmount += product.getPrice();
        }
        //SAVE ORDER
        Order order = new Order(nonTaxAmount,taxAmount,Status.ACTIVE,customer);
        order = ordersRepository.save(order);
        for(Integer i: postRequest.getProductId()){
            Product product = productRepository.findById(i).get();
            OrderItem orderItem = new OrderItem(order,product);
            orderItemRepository.save(orderItem);
        }
        sendMail("sahithyavadiyala@gmail.com","ORDER PLACED SUCCESFULLY", "Your order placed succesfully and order details are order id : "+order.getId()+", tax amount : "+order.getTaxAmount()+", non tax amount : "+order.getNonTaxAmount());
        return new ApiResponse(HttpStatus.OK.value(),new OrderResponse(order),HttpStatus.CREATED.getReasonPhrase(),true);
    }


    //PUT REQUEST
    @Transactional
   public ApiResponse updateOrders(UpdateOrderRequest updateOrderRequest) {
       Order order = ordersRepository.findById(updateOrderRequest.getOrderId()).get();
       if (order == null) {
           return new ApiResponse(HttpStatus.BAD_REQUEST.value(), null, "order not exist in system with given id " + updateOrderRequest.getOrderId(), false);
       } else {
           //orderItemRepository.deleteByOrderId(updateOrderRequest.getOrderId());
           orderItemRepository.deleteByOrder(order);
           Double taxAmount = (double) 0;
           Double nonTaxAmount = (double) 0;
           for(Integer i: updateOrderRequest.product_ids){
               Product product = productRepository.findById(i).get();
               taxAmount += product.getTax();
               nonTaxAmount += product.getPrice();
           }
           order.setNonTaxAmount(nonTaxAmount);
           order.setTaxAmount(taxAmount);
           ordersRepository.save(order);
           for(Integer i: updateOrderRequest.product_ids){
               Product product = productRepository.findById(i).get();
               OrderItem orderItem = new OrderItem(order,product);
               orderItemRepository.save(orderItem);
           }
           return new ApiResponse(HttpStatus.OK.value(), new OrderResponse(order), HttpStatus.CREATED.getReasonPhrase(), true);
       }
   }

    //DOWNLOAD ORDERS DETAILS TO EXCEL
    public ByteArrayInputStream downloadOrders() throws IOException {
        List<Order> orders = ordersRepository.allOrders();
        ByteArrayInputStream data = ExcelDownload.orderDownload(orders);
        return data;
    }

    public void sendMail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("saieshwar42manchala@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
    }
}
