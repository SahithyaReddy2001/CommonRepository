package Demo.CRUDoperations.elasticEntity;

import Demo.CRUDoperations.entity.Customer;
import Demo.CRUDoperations.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.util.Date;

@Document(indexName = "elasticorder")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElasticOrder {
    @Id
    private Integer id;
    private int[] orderItem;
    private Double taxAmount;
    private Double nonTaxAmount;
    private Status status = Status.ACTIVE;
    private Customer customer;
    private Date createdDate;
    private Date updatedDate;


}