package Demo.CRUDoperations.service.kafkaservice;

import Demo.CRUDoperations.dto.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    @Autowired
    KafkaTemplate<String, ProductResponse> kafkaTemplate;

    public void productConformation(ProductResponse product){
        kafkaTemplate.send("mail",product);
    }
}
