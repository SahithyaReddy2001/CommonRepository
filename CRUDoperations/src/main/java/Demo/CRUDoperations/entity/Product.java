package Demo.CRUDoperations.entity;

import javax.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id;
    String name;
    float price;
    float tax;
    @Enumerated(EnumType.STRING)
    Status status;
    public Product(){}

    public Product(int id, String name, float price, float tax, Status status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.tax = tax;
        this.status = status;
    }

    public Product(String name, float price, float tax) {
        this.name = name;
        this.price = price;
        this.tax = tax;
        //this.status=status;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", tax=" + tax +
                ", status=" + status +
                '}';
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }


    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

}
