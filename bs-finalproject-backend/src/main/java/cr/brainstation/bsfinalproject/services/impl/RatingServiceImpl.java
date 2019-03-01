package cr.brainstation.bsfinalproject.services.impl;

import cr.brainstation.bsfinalproject.db.daos.ProductDAO;
import cr.brainstation.bsfinalproject.db.daos.RatingDAO;
import cr.brainstation.bsfinalproject.db.daos.UserDAO;
import cr.brainstation.bsfinalproject.db.dtos.ProductDTO;
import cr.brainstation.bsfinalproject.db.dtos.RatingDTO;
import cr.brainstation.bsfinalproject.db.dtos.UserDTO;
import cr.brainstation.bsfinalproject.model.Product;
import cr.brainstation.bsfinalproject.model.Rating;
import cr.brainstation.bsfinalproject.services.RatingService;
import cr.brainstation.bsfinalproject.utils.exceptions.InvalidFieldException;
import cr.brainstation.bsfinalproject.utils.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.stream.Collectors;

@Service("ratingService")
@Transactional
public class RatingServiceImpl implements RatingService {

    private RatingDAO ratingDAO;
    private UserDAO userDAO;
    private ProductDAO productDAO;

    public RatingServiceImpl() {
    }

    @Autowired
    public RatingServiceImpl(RatingDAO ratingDAO, UserDAO userDAO, ProductDAO productDAO) {
        this.ratingDAO = ratingDAO;
        this.userDAO = userDAO;
        this.productDAO = productDAO;
    }

    @Override
    public Rating create(Rating rating) {

        if (rating.getGivenScore() < 1 || rating.getGivenScore() > 5) {
            throw new InvalidFieldException("The rating needs to be a number from 1 to 5");
        }

        if (rating.getProductId() < 1) {
            throw new InvalidFieldException("The rating's product id is not valid");
        }

        if (StringUtils.isEmpty(rating.getUserId())) {
            throw new InvalidFieldException("The rating's user id is not valid");
        }

        ProductDTO savedProduct = this.productDAO.findById(rating.getProductId()).orElse(null);
        if (savedProduct == null) {
            throw new NotFoundException("The rating's product was not found");
        }

        UserDTO savedUser = this.userDAO.findById(rating.getUserId()).orElse(null);
        if (savedUser == null) {
            throw new NotFoundException("The rating's user was not found");
        }

        RatingDTO savedRating = this.ratingDAO.findByUserAndProduct(savedUser, savedProduct);
        RatingDTO newRating;
        if (savedRating == null) {
            newRating = new RatingDTO();
            newRating.setUser(savedUser);
            newRating.setProduct(savedProduct);

        } else {
            newRating = savedRating;
        }

        newRating.setGivenScore(rating.getGivenScore());
        newRating = this.ratingDAO.save(newRating);

        if (savedRating == null) {
            savedUser.getRatings().add(newRating);
            this.userDAO.save(savedUser);

            savedProduct.getRatings().add(newRating);
            this.productDAO.save(savedProduct);
        }

        return new Rating(newRating);
    }

    @Override
    public Rating getRatingOfProductAndUser(int productId, String userId) {
        ProductDTO savedProduct = this.productDAO.findById(productId).orElse(null);
        if (savedProduct == null) {
            throw new NotFoundException("The rating's product was not found");
        }

        UserDTO savedUser = this.userDAO.findById(userId).orElse(null);
        if (savedUser == null) {
            throw new NotFoundException("The rating's user was not found");
        }

        RatingDTO savedRating = this.ratingDAO.findByUserAndProduct(savedUser, savedProduct);
        Rating response = new Rating();
        if (savedRating == null) {
            response.setGivenScore(0);
        } else {
            response.setGivenScore(savedRating.getGivenScore());
        }

        return response;
    }
}
