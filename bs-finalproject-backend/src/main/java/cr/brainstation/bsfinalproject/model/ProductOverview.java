package cr.brainstation.bsfinalproject.model;

import cr.brainstation.bsfinalproject.db.dtos.ProductDTO;
import cr.brainstation.bsfinalproject.enums.ProductCategory;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductOverview {

    private int id;
    private String name;
    private BigDecimal price;
    private ProductCategory category;
    private String imageUrl;
    private int averageRating;
    private boolean featured;

    public ProductOverview() {
    }

    public ProductOverview(ProductDTO dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.price = dto.getPrice();
        this.category = dto.getCategory();
        this.imageUrl = dto.getImageUrl();
        this.featured = dto.isFeatured();
        this.averageRating = dto.getAverageRating();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOverview productOverview = (ProductOverview) o;
        return Objects.equals(id, productOverview.id);
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }


}
