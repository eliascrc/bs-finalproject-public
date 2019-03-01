package cr.brainstation.bsfinalproject.model;

import cr.brainstation.bsfinalproject.db.dtos.OrderDTO;
import cr.brainstation.bsfinalproject.utils.formatters.DateFormatterLoader;

import java.util.Set;
import java.util.stream.Collectors;

public class Order {

    private Integer id;
    private String shoppedAt;
    private Float total;
    private Set<ProductOrder> productOrders;
    private String buyerId;
    private Address address;
    private CreditCard creditCard;

    public Order() {
    }

    public Order(OrderDTO dto) {
        this.id = dto.getId();
        this.shoppedAt = dto.getShoppedAt().format(DateFormatterLoader.getDateTimeFormatter());
        this.total = dto.getTotal().floatValue();
        this.productOrders = dto.getProductOrders().stream().map(ProductOrder::new).collect(Collectors.toSet());
        this.buyerId = dto.getBuyer().getId();
        this.address = new Address(dto.getAddress());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShoppedAt() {
        return shoppedAt;
    }

    public void setShoppedAt(String shoppedAt) {
        this.shoppedAt = shoppedAt;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Set<ProductOrder> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(Set<ProductOrder> productOrders) {
        this.productOrders = productOrders;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }
}