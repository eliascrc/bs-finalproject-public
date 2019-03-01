package cr.brainstation.bsfinalproject.model;

import cr.brainstation.bsfinalproject.db.dtos.CreditCardDTO;

public class CreditCard {

    private int id;
    private String token;
    private String brand;
    private String expiration;
    private String lastFourDigits;

    public CreditCard() {
    }

    public CreditCard(CreditCardDTO dto) {
        this.id = dto.getId();
        this.token = dto.getToken();
        this.brand = dto.getBrand();
        this.expiration = dto.getExpiration();
        this.lastFourDigits = dto.getLastFourDigits();
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
}
