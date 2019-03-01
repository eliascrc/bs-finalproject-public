package cr.brainstation.bsfinalproject.services;

import cr.brainstation.bsfinalproject.model.Rating;

import java.util.Set;

public interface RatingService {

    Rating create(Rating rating);
    Rating getRatingOfProductAndUser(int productId, String userId);

}
