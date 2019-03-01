package cr.brainstation.bsfinalproject.controllers;

import com.amazonaws.services.cognitoidp.model.InvalidParameterException;
import com.amazonaws.services.cognitoidp.model.InvalidPasswordException;
import cr.brainstation.bsfinalproject.model.CustomResponse;
import cr.brainstation.bsfinalproject.model.User;
import cr.brainstation.bsfinalproject.services.AuthenticationService;
import cr.brainstation.bsfinalproject.utils.exceptions.EmptyFieldException;
import cr.brainstation.bsfinalproject.utils.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
/**
 * Controller in charge of signing up a user to the system.
 */
@CrossOrigin
@RestController
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * POST Endpoint for creating a new user within the system and cognito.
     * @param user object sent by the user that follows {@link User} specifications
     * @return  400 if any required field of the address was invalid or empty.
     *          404 when the user was not found.
     *          200 if the address was created correctly.
     */
    @PostMapping("/join")
    public ResponseEntity<CustomResponse> signUp(@RequestBody User user) {

        try {
            this.authenticationService.signUp(user);

        } catch (InvalidPasswordException | InvalidParameterException e) {
            return new ResponseEntity<>(new CustomResponse(e.getErrorMessage()), HttpStatus.BAD_REQUEST);

        } catch (EmptyFieldException e) {
            return new ResponseEntity<>(new CustomResponse(e.getMessage()), HttpStatus.BAD_REQUEST);

        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
