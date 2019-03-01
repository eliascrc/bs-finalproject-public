package cr.brainstation.bsfinalproject.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import cr.brainstation.bsfinalproject.model.CustomResponse;
import cr.brainstation.bsfinalproject.model.Rating;
import cr.brainstation.bsfinalproject.services.RatingService;
import cr.brainstation.bsfinalproject.utils.exceptions.InvalidFieldException;
import cr.brainstation.bsfinalproject.utils.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/rating")
    public ResponseEntity<CustomResponse> createRanking(@RequestBody Rating rating,
                                                        @RequestAttribute("decodedJWT") DecodedJWT decodedJWT) {
        Rating savedRating;
        rating.setUserId(decodedJWT.getClaim("cognito:username").asString());

        try {
            savedRating = this.ratingService.create(rating);
        } catch (InvalidFieldException e) {
            return new ResponseEntity<>(new CustomResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new CustomResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new CustomResponse(savedRating), HttpStatus.OK);
    }

    @GetMapping("/my-rating/{id}")
    public ResponseEntity<CustomResponse> createRanking(@PathVariable int id,
                                                        @RequestAttribute("decodedJWT") DecodedJWT decodedJWT) {
        Rating userRating;
        String userId = decodedJWT.getClaim("cognito:username").asString();

        try {
            userRating = this.ratingService.getRatingOfProductAndUser(id, userId);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(new CustomResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new CustomResponse(userRating), HttpStatus.OK);
    }

}
