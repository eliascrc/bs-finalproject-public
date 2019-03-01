package cr.brainstation.bsfinalproject.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import cr.brainstation.bsfinalproject.model.CreditCard;
import cr.brainstation.bsfinalproject.model.CustomResponse;
import cr.brainstation.bsfinalproject.services.CreditCardService;
import cr.brainstation.bsfinalproject.utils.exceptions.EmptyFieldException;
import cr.brainstation.bsfinalproject.utils.exceptions.InvalidFieldException;
import cr.brainstation.bsfinalproject.utils.exceptions.InvalidPaymentInformationException;
import cr.brainstation.bsfinalproject.utils.exceptions.NotAuthorizedException;
import cr.brainstation.bsfinalproject.utils.exceptions.NotFoundException;
import cr.brainstation.bsfinalproject.utils.exceptions.PaymentChargeIncompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static cr.brainstation.bsfinalproject.constants.CognitoConstants.USERNAME_CLAIM_KEY;
import static cr.brainstation.bsfinalproject.constants.CommonConstants.DECODED_JWT_ATTRIBUTE;

/**
 * Controller in charge of making the create and delete actions for a credit card.
 */
@RestController
@CrossOrigin
public class CreditCardController {

    private final CreditCardService creditCardService;

    @Autowired
    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    /**
     * POST Endpoint for creating a payment for an user.
     * @param creditCard object sent by the user that follows {@link CreditCard} specifications
     * @param decodedJWT decoded token sent by the {@link cr.brainstation.bsfinalproject.filters.AuthenticationFilter}
     * @return  400 if any required field of the payment was invalid or empty.
     *          404 when the user was not found.
     *          200 if the payment was created correctly.
     */
    @PostMapping("/payment")
    public ResponseEntity<CustomResponse> createCreditCard(@RequestBody CreditCard creditCard,
                                                           @RequestAttribute(DECODED_JWT_ATTRIBUTE) DecodedJWT decodedJWT) {
        CreditCard savedCreditCard;
        String userId = decodedJWT.getClaim(USERNAME_CLAIM_KEY).asString();

        try {
            savedCreditCard = this.creditCardService.create(creditCard, userId);

        } catch (EmptyFieldException | InvalidFieldException e) {
            return new ResponseEntity<>(new CustomResponse(e.getMessage()), HttpStatus.BAD_REQUEST);

        } catch (NotFoundException e) {
            return new ResponseEntity<>(new CustomResponse(e.getMessage()), HttpStatus.NOT_FOUND);

        } catch (PaymentChargeIncompleteException | InvalidPaymentInformationException e) {
            return new ResponseEntity<>(new CustomResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<>(new CustomResponse(savedCreditCard), HttpStatus.OK);
    }

    /**
     * DELETE Endpoint for deleting a payment for an user.
     * @param creditCardId id sent by the user that corresponds to a credit card in the database
     * @param decodedJWT decoded token sent by the {@link cr.brainstation.bsfinalproject.filters.AuthenticationFilter}
     * @return  400 if any required field of the payment was invalid or empty.
     *          404 when the user was not found.
     *          200 if the payment was deleted correctly.
     */
    @DeleteMapping("/payment/{creditCardId}")
    public ResponseEntity<CustomResponse> deleteCreditCard(@PathVariable int creditCardId,
                                                           @RequestAttribute(DECODED_JWT_ATTRIBUTE) DecodedJWT decodedJWT) {
        String userId = decodedJWT.getClaim(USERNAME_CLAIM_KEY).asString();
        try {
            this.creditCardService.delete(creditCardId, userId);

        } catch (NotAuthorizedException e) {
            return new ResponseEntity<>(new CustomResponse(e.getMessage()), HttpStatus.FORBIDDEN);

        } catch (NotFoundException e) {
            return new ResponseEntity<>(new CustomResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
