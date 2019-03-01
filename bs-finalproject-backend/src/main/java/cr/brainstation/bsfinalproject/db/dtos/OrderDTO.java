package cr.brainstation.bsfinalproject.db.dtos;

import cr.brainstation.bsfinalproject.enums.OrderStatus;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * A shopping order made by an user within the system after checking out.
 */
@Entity
@Table( name="shopping_order" )
public class OrderDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private LocalDateTime shoppedAt;

    private BigDecimal total;

    @OrderBy("id ASC")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "order", fetch = FetchType.EAGER)
    private Set<ProductOrderDTO> productOrders;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private UserDTO buyer;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private AddressDTO address;

    private boolean emailSent;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private String chargeId;

    public OrderDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return id == orderDTO.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        this.productOrders.forEach(product ->
                sb.append("- ").append(product.getProduct().getName())
                .append(", Amount: ").append(product.getQuantity())
                .append(", Size of each one: ").append(product.getAmount()).append("<br/>"));

        return sb.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getShoppedAt() {
        return shoppedAt;
    }

    public void setShoppedAt(LocalDateTime shoppedAt) {
        this.shoppedAt = shoppedAt;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Set<ProductOrderDTO> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(Set<ProductOrderDTO> productOrders) {
        this.productOrders = productOrders;
    }

    public UserDTO getBuyer() {
        return buyer;
    }

    public void setBuyer(UserDTO buyer) {
        this.buyer = buyer;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public boolean isEmailSent() {
        return emailSent;
    }

    public void setEmailSent(boolean emailSent) {
        this.emailSent = emailSent;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }

}
