package Demo.CRUDoperations.service.impl;

import Demo.CRUDoperations.apiresponse.ApiResponse;
import Demo.CRUDoperations.download.ExcelDownload;
import Demo.CRUDoperations.dto.request.PostRequest;
import Demo.CRUDoperations.dto.request.UpdateOrderRequest;
import Demo.CRUDoperations.dto.response.JoinResponse;
import Demo.CRUDoperations.dto.response.OrderResponse;
import Demo.CRUDoperations.elasticEntity.ElasticOrder;
import Demo.CRUDoperations.entity.*;
import Demo.CRUDoperations.repository.*;
import Demo.CRUDoperations.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.SearchHit;



import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    public static final String HASH_KEY = "orderDetails";

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

    @Autowired
    ElasticRepository elasticRepository;

    @Autowired
     ElasticsearchOperations elasticsearchOperations;


    //GET REQUEST
    public ApiResponse getOrders(){
        List<JoinResponse> joinResponse=ordersRepository.findByStatus();
        return new ApiResponse(HttpStatus.OK.value(),joinResponse,HttpStatus.OK.getReasonPhrase(),true);
    }

    public List<ElasticOrder> elasticOrders(String category) {
        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        Query searchQuery = null;
        if("id".equals(category)){
            searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).withSort(SortBuilders.fieldSort("id").order(SortOrder.ASC)).build();
        }
        else if ("taxAmount".equals(category)){
             searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).withSort(SortBuilders.fieldSort("taxAmount").order(SortOrder.ASC)).build();
        }
        else if ("nonTaxAmount".equals(category)){
            searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).withSort(SortBuilders.fieldSort("nonTaxAmount").order(SortOrder.ASC)).build();
        }
        else if ("customerId".equals(category)){
            searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).withSort(SortBuilders.fieldSort("customer.id").order(SortOrder.ASC)).build();
        }
        SearchHits<ElasticOrder> orderHits = elasticsearchOperations.search(searchQuery, ElasticOrder.class, IndexCoordinates.of("elasticorder"));
        return orderHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }


    //GET REQUEST BY ID
    @Cacheable(key="#id",value = "orderDetails")
    public ApiResponse getAllOrders(int id) {
        System.out.println("Fetching from database");
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
    @CacheEvict(key="#id",value = "orderDetails")
    public ApiResponse deleteOrders(int id) {
        ordersRepository.deleteById(id);
        elasticRepository.deleteById(id);
        return getAllOrders(id);
    }

    //POST REQUEST
    /*@Autowired
    KafkaTemplate<String,PostRequest> kaftaTemplate;

    public void createOrders(PostRequest postRequest){
        kaftaTemplate.send("NewOrder",postRequest);
    }*/
    //@KafkaListener(topics = "NewOrder",groupId = "CreateOrder")
    public ApiResponse createOrders(PostRequest postRequest) {
        // SAVE CUSTOMER
        Customer customer = null;
        if(customerRepository.findByEmail(postRequest.getEmail()) == null) {
            customer = new Customer();
            customer.setName(postRequest.getName());
            customer.setEmail(postRequest.getEmail());
            customerRepository.save(customer);
        }
        else{
            customer = customerRepository.findByEmail(postRequest.getEmail());
        }
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
            // SAVE ORDERITEM
            OrderItem orderItem = new OrderItem(order,product);
            orderItemRepository.save(orderItem);
        }
        // SAVE IN ELASTIC SEARCH
        ElasticOrder elasticOrder = new ElasticOrder(order.getId(),postRequest.productId,taxAmount,nonTaxAmount,Status.ACTIVE,customer,order.getCreatedDate(),order.getUpdatedDate());
        elasticRepository.save(elasticOrder);
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
           for(Integer i: updateOrderRequest.productIds){
               Product product = productRepository.findById(i).get();
               taxAmount += product.getTax();
               nonTaxAmount += product.getPrice();
           }
           order.setNonTaxAmount(nonTaxAmount);
           order.setTaxAmount(taxAmount);
           ordersRepository.save(order);
           for(Integer i: updateOrderRequest.productIds){
               Product product = productRepository.findById(i).get();
               OrderItem orderItem = new OrderItem(order,product);
               orderItemRepository.save(orderItem);
           }
           elasticRepository.deleteById(updateOrderRequest.getOrderId());
           ElasticOrder elasticOrder = new ElasticOrder(updateOrderRequest.getOrderId(),updateOrderRequest.productIds,taxAmount,nonTaxAmount,Status.ACTIVE, customerRepository.findById(order.getCustomer().getId()).get(),order.getCreatedDate(),order.getUpdatedDate());
           elasticRepository.save(elasticOrder);
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
