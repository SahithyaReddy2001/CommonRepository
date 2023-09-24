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

    public  void sendReport(String email){
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper message=new MimeMessageHelper(mimeMessage,true);
            message.setTo("saieshwarmanchala42@gmail.com");
            message.setSubject("test subject");
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
        return data.toString().getBytes();
    }

    private List<ProductResponse> getProduts(){
        return productRepository.findByStatus()
                .stream().map(ProductResponse::new).collect(Collectors.toList());
    }

    /*public void emailServices(Email email,Object product){
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
            message.setTo("saieshwarmanchala42@gmail.com");
            Context context=new Context();
            String data;
            switch (email){
                case INSERT:
                    message.setSubject("Product is added to our list");
                    context.setVariable("product",product);
                    data=templateEngine.process("email-format",context);
                    message.setText(data,true);
                    break;
                case REPORT:
                    message.setSubject("Report on Procuts");
                    ByteArrayDataSource csvData=new ByteArrayDataSource(toCsvFile(),"text/csv");
                    message.setText("Report file",true);
                    message.addAttachment("report.csv",csvData);
                    break;
                case SCHEDULAR:
                    message.setSubject("schedular mail");
                    context.setVariable("products",product);
                    data=templateEngine.process("productstemplate",context);
                    message.setText(data,true);
                    break;
                default:
                    //return api response as badrequest
                    break;
            }
            javaMailSender.send(mimeMessage);

        }
        catch (Exception e){

        }
    }*/

}
