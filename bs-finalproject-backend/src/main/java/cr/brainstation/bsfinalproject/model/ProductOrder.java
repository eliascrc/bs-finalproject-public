package cr.brainstation.bsfinalproject.model;

import cr.brainstation.bsfinalproject.db.dtos.ProductOrderDTO;

public class ProductOrder {

    private Integer id;
    private Integer quantity;
    private Float price;
    private Float total;
    private String amount;
    private int productId;
    private String productName;
    private Integer orderId;
    private String imageUrl;

    public ProductOrder() {
    }

    public ProductOrder(ProductOrderDTO dto) {
        this.id = dto.getId();
        this.quantity = dto.getQuantity();
        this.price = dto.getPrice().floatValue();
        this.total = dto.getTotal().floatValue();
        this.amount = dto.getAmount();
        this.productId = dto.getProduct().getId();
        this.productName = dto.getProduct().getName();
        this.orderId = dto.getOrder() == null? null : dto.getOrder().getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
