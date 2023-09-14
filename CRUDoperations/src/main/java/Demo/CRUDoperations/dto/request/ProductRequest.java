package Demo.CRUDoperations.dto.request;


import Demo.CRUDoperations.entity.Status;

public class ProductRequest {
    //public  Status status;
    public String name;
    public Float price;
    public Float tax;
    public Status status;
    public ProductRequest(){

    }

    public ProductRequest(String name, Float price, Float tax,Status status) {
        this.name = name;
        this.price = price;
        this.tax = tax;
        this.status=status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getTax() {
        return tax;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ProductRequest{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", tax=" + tax +
                ", status=" + status +
                '}';
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
