package Demo.CRUDoperations.service.impl;

import Demo.CRUDoperations.apiresponse.ApiResponse;
import Demo.CRUDoperations.download.ExcelDownload;
import Demo.CRUDoperations.dto.request.OrderRequest;
import Demo.CRUDoperations.dto.request.UpdateOrderRequest;
import Demo.CRUDoperations.dto.response.JoinResponse;
import Demo.CRUDoperations.dto.response.OrderResponse;
import Demo.CRUDoperations.entity.Orders;
import Demo.CRUDoperations.entity.Product;
import Demo.CRUDoperations.entity.Status;
import Demo.CRUDoperations.repository.OrdersRepository;
import Demo.CRUDoperations.repository.ProductRepository;
import Demo.CRUDoperations.service.OrderService;
import io.swagger.models.auth.In;
import org.hibernate.criterion.Order;
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

    @Autowired
    ProductRepository productRepository;

    @Autowired
    JavaMailSender mailSender;

    //GET REQUEST
    public ApiResponse getOrders(){
        List<JoinResponse> joinResponse=new ArrayList<>();
        List<Object[]> orders = ordersRepository.findByStatus();
        for (Object[] o:orders) {
            JoinResponse join = new JoinResponse((Integer)(o[0]),(String)(o[1]),(Integer)(o[2]),(String)(o[3]),(Float)(o[4]),(Float)(o[5]));
            joinResponse.add(join);
        }
        return new ApiResponse(HttpStatus.OK.value(),joinResponse,HttpStatus.OK.getReasonPhrase(),true);
    }

    //GET REQUEST BY ID
    public ApiResponse getAllOrders(int id) {
        List<Object[]> joinResponse = ordersRepository.singleOrder(id);
        if(joinResponse != null){
            return new ApiResponse(HttpStatus.OK.value(),new JoinResponse((Integer) joinResponse.get(0)[0], (String) joinResponse.get(0)[1],(Integer) joinResponse.get(0)[2], (String) joinResponse.get(0)[3], (Float)joinResponse.get(0)[4],(Float)joinResponse.get(0)[5]),HttpStatus.OK.getReasonPhrase(),true);
        }
        else{
            return new ApiResponse(HttpStatus.BAD_REQUEST.value(),null,"order not exist in system with given id "+id, false);
        }
    }

    // PAGINATION AND SORTING
    public ApiResponse findOrdersWIthPaginationAndSorting(int offset, int pageSize, String field){
        List<JoinResponse> joinResponse=new ArrayList<>();
        Page<Orders> order = ordersRepository.findAll(PageRequest.of(offset,pageSize).withSort(Sort.by(field)));
        for(Orders o:order){
            System.out.println(o);
            JoinResponse joinResponse1 = new JoinResponse();
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

    }

    //DELETE REQUEST
    public ApiResponse deleteOrders(int id) {
        Orders order= ordersRepository.findById(id).get();
        order.setStatus(Status.INACTIVE);
        ordersRepository.save(order);
        return getAllOrders(id);
    }

    //POST REQUEST
    @Autowired
    KafkaTemplate<String,Integer> kaftaTemplate;

    public void createOrders(Integer productId){
        kaftaTemplate.send("NewOrder",productId);
    }
    @KafkaListener(topics = "NewOrder",groupId = "CreateOrder")
    public ApiResponse listenToTopic(Integer productId) {
        Orders ord = new Orders();
        Product product = productRepository.findById(productId).get();
        ord.setProduct(product);
        ord.setStatus(Status.ACTIVE);
        ord.setTaxAmount(product.getTax());
        ord.setNonTaxAmount(product.getPrice());
        ord = ordersRepository.save(ord);
        sendMail("sahithyavadiyala@gmail.com","ORDER PLACED SUCCESFULLY", "Your order placed succesfully and order details are order id : "+ord.getId()+", tax amount : "+ord.getTaxAmount()+", non tax amount : "+ord.getNonTaxAmount());
        return new ApiResponse(HttpStatus.OK.value(),new OrderResponse(ord),HttpStatus.CREATED.getReasonPhrase(),true);
    }


    //PUT REQUEST
   public ApiResponse updateOrders(UpdateOrderRequest updateOrderRequest) {
       Orders order = ordersRepository.findById(updateOrderRequest.getOrder_id()).get();
       if (order == null) {
           return new ApiResponse(HttpStatus.BAD_REQUEST.value(), null, "order not exist in system with given id " + updateOrderRequest.getOrder_id(), false);
       } else {
           Product product = productRepository.findById(updateOrderRequest.getProduct_id()).get();
           order.setProduct(product);
           order.setTaxAmount(product.getTax());
           order.setNonTaxAmount(product.getPrice());
           ordersRepository.save(order);
           return new ApiResponse(HttpStatus.OK.value(), new OrderResponse(order), HttpStatus.CREATED.getReasonPhrase(), true);
       }
   }

    //DOWNLOAD ORDERS DETAILS TO EXCEL
    public ByteArrayInputStream downloadOrders() throws IOException {
        List<Orders> orders = ordersRepository.allOrders();
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
