package cr.brainstation.bsfinalproject.model;

import cr.brainstation.bsfinalproject.db.dtos.RatingDTO;

public class Rating {

    private int id;
    private int givenScore;
    private int productId;
    private String userId;

    public Rating() {
    }

    public Rating(RatingDTO dto) {
        this.id = dto.getId();
        this.givenScore = dto.getGivenScore();
        this.productId = dto.getProduct().getId();
        this.userId = dto.getUser().getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGivenScore() {
        return givenScore;
    }

    public void setGivenScore(int givenScore) {
        this.givenScore = givenScore;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
