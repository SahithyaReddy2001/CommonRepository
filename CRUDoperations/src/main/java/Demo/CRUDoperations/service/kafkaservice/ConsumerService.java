package Demo.CRUDoperations.service.kafkaservice;

import Demo.CRUDoperations.dto.response.ProductResponse;
import Demo.CRUDoperations.service.mailservice.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @Autowired
    MailService mailService;
    @KafkaListener(topics = "mail",groupId = "mailsender")
    public void mails(ProductResponse product){
        mailService.sendMail(product);

    }
}
