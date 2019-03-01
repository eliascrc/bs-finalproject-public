package cr.brainstation.bsfinalproject.db.dtos;

import cr.brainstation.bsfinalproject.model.CreditCard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

/**
 * A credit card registered by an user within the system.
 */
@Entity
@Table( name = "credit_card")
public class CreditCardDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private String brand; // The card's brand. e.g. Visa, Mastercard...

    @Column(nullable = false)
    private String expiration;

    @Column(nullable = false)
    private String lastFourDigits;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserDTO owner;

    public CreditCardDTO() {
    }

    public CreditCardDTO(CreditCard creditCard) {
        this.token = creditCard.getToken();
        this.brand = creditCard.getBrand();
        this.expiration = creditCard.getExpiration();
        this.lastFourDigits = creditCard.getLastFourDigits();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCardDTO that = (CreditCardDTO) o;
        return id == that.id;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getLastFourDigits() {
        return lastFourDigits;
    }

    public void setLastFourDigits(String lastFourDigits) {
        this.lastFourDigits = lastFourDigits;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }
}
