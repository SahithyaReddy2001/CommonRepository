package Demo.CRUDoperations.service.impl;

import Demo.CRUDoperations.entity.Orders;
import Demo.CRUDoperations.repository.OrdersRepository;
import Demo.CRUDoperations.service.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements OrderServices {
    @Autowired
    OrdersRepository ordersRepository;


    public List<Orders> getOrders(){
        System.out.println(ordersRepository.findAll());
        return ordersRepository.findAll();

    }

    public ResponseEntity<Orders> createOrders(Orders orders) {
        return  new ResponseEntity<>(ordersRepository.save(orders), HttpStatus.CREATED);
    }

    public ResponseEntity<Orders> getAllOrders(int id) {
        Optional<Orders> orders = ordersRepository.findById(id);
        if(orders.isPresent()){
            return new ResponseEntity<>(orders.get(),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<Orders> updateOrders(int id, Orders order) {
        Optional<Orders> orders = ordersRepository.findById(id);
        if(orders.isPresent()){
            orders.get().setProductId(order.getProductId());
            orders.get().setTaxAmount(order.getTaxAmount());
            orders.get().setNonTaxAmount(order.getNonTaxAmount());
            return new ResponseEntity<>(ordersRepository.save(orders.get()),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<Orders> deleteOrders(int id) {
        Optional<Orders> orders = ordersRepository.findById(id);
        if(orders.isPresent()){
            ordersRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
