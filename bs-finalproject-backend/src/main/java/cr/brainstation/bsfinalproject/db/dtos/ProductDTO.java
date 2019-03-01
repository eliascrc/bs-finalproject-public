package cr.brainstation.bsfinalproject.db.dtos;

import cr.brainstation.bsfinalproject.model.Product;
import cr.brainstation.bsfinalproject.enums.ProductCategory;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

/**
 * A product in the e commerce of the system.
 */
@Entity
@Table( name="product" )
public class ProductDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "Text")
    private String description;

    @Column(nullable = false)
    private String amount;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int inStock;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductCategory category;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private boolean featured;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product")
    private Set<ProductOrderDTO> orders;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product", fetch = FetchType.EAGER)
    private Set<RatingDTO> ratings;

    @Transient
    private int averageRating;

    public ProductDTO() {
    }

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.amount = product.getAmount();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.inStock = product.getInStock();
        this.category = product.getCategory();
        this.imageUrl = product.getImageUrl();
        this.featured = product.isFeatured();
    }

    /**
     * Calculates the average rating of a product.
     */
    @PostLoad
    private void postLoad() {

        if (this.ratings.size() == 0) {
            this.averageRating = 0;
        } else {
            int total = 0;
            for (RatingDTO rating : this.ratings) {
                total += rating.getGivenScore();
            }
            this.averageRating = total / this.ratings.size();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDTO productDTO = (ProductDTO) o;
        return Objects.equals(id, productDTO.id);
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

    public Set<ProductOrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(Set<ProductOrderDTO> orders) {
        this.orders = orders;
    }

    public int getAverageRating() {
        return averageRating;
    }

    public Set<RatingDTO> getRatings() {
        return ratings;
    }

    public void setRatings(Set<RatingDTO> ratings) {
        this.ratings = ratings;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }
}
