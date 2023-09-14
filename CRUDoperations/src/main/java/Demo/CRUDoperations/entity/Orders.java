package Demo.CRUDoperations.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int productId;
    private float taxAmount;
    private float nonTaxAmount;

    public Orders() {
    }

    public Orders(int id, int productId, float taxAmount, float nonTaxAmount ){
        this.id = id;
        this.productId = productId;
        this.taxAmount = taxAmount;
        this.nonTaxAmount = nonTaxAmount;
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public float getTaxAmount() {
        return taxAmount;
    }

    public float getNonTaxAmount() {
        return nonTaxAmount;
    }

    public void setNonTaxAmount(float nonTaxAmount) {
        this.nonTaxAmount = nonTaxAmount;
    }

    public void setTaxAmount(float taxAmount) {
        this.taxAmount = taxAmount;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "orders{" +
                "id=" + id +
                ", itemsId=" + productId +
                ", amount=" + taxAmount +
                '}';
    }
}
