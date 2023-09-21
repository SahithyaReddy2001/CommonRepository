package Demo.CRUDoperations.service.mailservice;

import Demo.CRUDoperations.dto.request.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    JavaMailSender javaMailSender;

    TemplateEngine templateEngine=new TemplateEngine();

    public void sendMail(ProductRequest product) {
        try {
/*    SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@baeldung.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);*/
            //SimpleMailMessage message = new SimpleMailMessage();
            //message.setFrom("noreply@baeldung.com");
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper message=new MimeMessageHelper(mimeMessage,true);
            message.setTo("saieshwarmanchala42@gmail.com");
            message.setSubject("test subject");
            Context context=new Context();
            context.setVariable("name",product.getName());
            context.setVariable("price",product.getPrice());
            context.setVariable("tax",product.getTax());
            String data=templateEngine.process("emailformat",context);
           // System.out.println("HTML Content: " + );

            message.setText(data,true);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
