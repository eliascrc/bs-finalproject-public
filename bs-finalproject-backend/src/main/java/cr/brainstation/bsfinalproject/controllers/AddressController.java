package cr.brainstation.bsfinalproject.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import cr.brainstation.bsfinalproject.constants.CognitoConstants;
import cr.brainstation.bsfinalproject.model.Address;
import cr.brainstation.bsfinalproject.model.CustomResponse;
import cr.brainstation.bsfinalproject.services.AddressService;
import cr.brainstation.bsfinalproject.utils.exceptions.EmptyFieldException;
import cr.brainstation.bsfinalproject.utils.exceptions.NotAuthorizedException;
import cr.brainstation.bsfinalproject.utils.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static cr.brainstation.bsfinalproject.constants.CognitoConstants.USERNAME_CLAIM_KEY;
import static cr.brainstation.bsfinalproject.constants.CommonConstants.DECODED_JWT_ATTRIBUTE;

/**
 * Controller in charge of making the CRUD actions for an address.
 */
@RestController
@CrossOrigin
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * POST Endpoint for creating an address for an user.
     * @param address object sent by the user that follows {@link Address} specifications
     * @param decodedJWT decoded token sent by the {@link cr.brainstation.bsfinalproject.filters.AuthenticationFilter}
     * @return  400 if any required field of the address was invalid or empty.
     *          404 when the user was not found.
     *          200 if the address was created correctly.
     */
    @PostMapping("/address")
    public ResponseEntity<CustomResponse> createAddress(@RequestBody Address address,
                                                        @RequestAttribute(DECODED_JWT_ATTRIBUTE) DecodedJWT decodedJWT) {
        String userId = decodedJWT.getClaim(USERNAME_CLAIM_KEY).asString();

        Address response;
        try {
            response = this.addressService.create(address, userId);

        } catch (EmptyFieldException e) {
            return new ResponseEntity<>(new CustomResponse(e.getMessage()), HttpStatus.BAD_REQUEST);

        } catch (NotFoundException e) {
            return new ResponseEntity<>(new CustomResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new CustomResponse(response), HttpStatus.OK);
    }

    /**
     * DELETE Endpoint for deleting an address of an user.
     * @param addressId id sent by the user that that corresponds to an address in the database
     * @param decodedJWT decoded token sent by the {@link cr.brainstation.bsfinalproject.filters.AuthenticationFilter}
     * @return  403 if the address does not belong to the user
     *          404 when the user was not found.
     *          200 if the address was deleted correctly.
     */
    @DeleteMapping("/address/{addressId}")
    public ResponseEntity<CustomResponse> deleteAddress(@PathVariable int addressId,
                                                        @RequestAttribute(DECODED_JWT_ATTRIBUTE) DecodedJWT decodedJWT) {
        String userId = decodedJWT.getClaim(USERNAME_CLAIM_KEY).asString();

        try {
            this.addressService.delete(addressId, userId);

        } catch (NotAuthorizedException e) {
            return new ResponseEntity<>(new CustomResponse(e.getMessage()), HttpStatus.FORBIDDEN);

        } catch (NotFoundException e) {
            return new ResponseEntity<>(new CustomResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * PUT Endpoint for editing an address of an user.
     * @param address object sent by the user that follows {@link Address} specifications
     * @param decodedJWT decoded token sent by the {@link cr.brainstation.bsfinalproject.filters.AuthenticationFilter}
     * @return  403 if the address does not belong to the user
     *          404 when the user was not found.
     *          200 if the address was updated correctly.
     */
    @PutMapping("/address")
    public ResponseEntity<CustomResponse> editAddress(@RequestBody Address address,
                                                        @RequestAttribute(DECODED_JWT_ATTRIBUTE) DecodedJWT decodedJWT) {
        String userId = decodedJWT.getClaim(USERNAME_CLAIM_KEY).asString();

        Address response;
        try {
            response = this.addressService.edit(address, userId);

        } catch (NotAuthorizedException e) {
            return new ResponseEntity<>(new CustomResponse(e.getMessage()), HttpStatus.FORBIDDEN);

        } catch (NotFoundException e) {
            return new ResponseEntity<>(new CustomResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new CustomResponse(response), HttpStatus.OK);
    }

}
