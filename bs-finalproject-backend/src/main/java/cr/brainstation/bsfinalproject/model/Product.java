package cr.brainstation.bsfinalproject.model;

import cr.brainstation.bsfinalproject.db.dtos.ProductDTO;
import cr.brainstation.bsfinalproject.enums.ProductCategory;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    private int id;
    private String name;
    private String description;
    private String amount;
    private BigDecimal price;
    private int inStock;
    private ProductCategory category;
    private String imageUrl;
    private int averageRating;
    private boolean featured;

    public Product() {
    }

    public Product(ProductDTO dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.amount = dto.getAmount();
        this.price = dto.getPrice();
        this.inStock = dto.getInStock();
        this.category = dto.getCategory();
        this.imageUrl = dto.getImageUrl();
        this.averageRating = dto.getAverageRating();
        this.featured = dto.isFeatured();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
