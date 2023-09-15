package Demo.CRUDoperations.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
@Getter @Setter @NoArgsConstructor
@ToString
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @NotBlank(message = "name should not be empty")
            @Column(unique = true)
    String name;
    @Min(value = 1,message = "price should br greater than 0")
    Float price;
    Float tax;
    @Enumerated(EnumType.STRING)
    Status status;

    public Product(int id, String name, float price, float tax, Status status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.tax = tax;
        this.status = status;
    }

    public Product(String name, float price, float tax,Status status) {
        this.name = name;
        this.price = price;
        this.tax = tax;
        this.status=status;
    }



}
