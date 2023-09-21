package Demo.CRUDoperations.service.mailservice;


import Demo.CRUDoperations.dto.response.ProductResponse;
import Demo.CRUDoperations.entity.Status;
import Demo.CRUDoperations.repository.ProductRepository;
import Demo.CRUDoperations.service.ProductService;
import Demo.CRUDoperations.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MailService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;
    //@Scheduled(fixedDelay = 10000)
    public void scheuler(){
        System.out.println("schedular");
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper message=new MimeMessageHelper(mimeMessage,"UTF-8");
            message.setTo("saieshwarmanchala42@gmail.com");
            message.setSubject("test subject");
            List<ProductResponse> products=getProduts();
            Context context=new Context();
            context.setVariable("products",products);
            String data=templateEngine.process("productstemplate",context);
            message.setText(data,true);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void sendMail(ProductResponse product) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper message=new MimeMessageHelper(mimeMessage,"UTF-8");
            message.setTo("saieshwarmanchala42@gmail.com");
            message.setSubject("test subject");

            Context context=new Context();
            context.setVariable("product",product);
            String data=templateEngine.process("email-format",context);
            message.setText(data,true);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    //@Scheduled(fixedRate = 10000)
    public  void sendReport(){
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper message=new MimeMessageHelper(mimeMessage,true);
            message.setTo("saieshwarmanchala42@gmail.com");
            message.setSubject("test subject");
            //InputStreamResource csvData=new InputStreamResource(new ByteArrayInputStream(toCsvFile()));
            ByteArrayDataSource csvData=new ByteArrayDataSource(toCsvFile(),"text/csv");
            message.setText("Report file",true);
            message.addAttachment("report.csv",csvData);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    public byte[] toCsvFile(){
        List<ProductResponse> products=getProduts();
        StringBuilder data=new StringBuilder();
        data.append("id,name,price,tax,status\n");
        for (ProductResponse product:products)
            {
            data.append(product.getId()).append(",");
            data.append(product.getName()).append(",");
            data.append(product.getPrice()).append(",");
            data.append(product.getTax()).append(",");
            data.append(product.getStatus()).append("\n");
        }
        //System.out.println(data.toString());
        return data.toString().getBytes();
    }

    private List<ProductResponse> getProduts(){
        return productRepository.findByStatus(Status.ACTIVE)
                .stream().map(ProductResponse::new).collect(Collectors.toList());
    }
}
