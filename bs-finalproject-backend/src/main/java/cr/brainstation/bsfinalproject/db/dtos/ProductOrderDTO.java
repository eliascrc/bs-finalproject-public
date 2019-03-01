package cr.brainstation.bsfinalproject.db.dtos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A product order registered by an user within the system at the time of making an order.
 */
@Entity
@Table( name="product_order" )
public class ProductOrderDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private BigDecimal price;

    @Transient
    private BigDecimal total;

    @Column(nullable = false)
    private String amount;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductDTO product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderDTO order;

    public ProductOrderDTO() {
    }

    /**
     * Calculates the total of a product order
     */
    @PostLoad
    private void postLoad() {
        BigDecimal preciseTotal = this.price.multiply(new BigDecimal(quantity));
        this.total = preciseTotal.setScale(2, BigDecimal.ROUND_FLOOR);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOrderDTO that = (ProductOrderDTO) o;
        return id == that.id &&
                quantity == that.quantity &&
                Objects.equals(price, that.price) &&
                Objects.equals(total, that.total) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(product, that.product) &&
                Objects.equals(order, that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, price, total, amount, product, order);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
