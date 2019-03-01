package cr.brainstation.bsfinalproject.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import cr.brainstation.bsfinalproject.model.CustomResponse;
import cr.brainstation.bsfinalproject.model.UserDetails;
import cr.brainstation.bsfinalproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<CustomResponse> getUserDetails(@RequestAttribute("decodedJWT") DecodedJWT decodedJWT) {
        String username = decodedJWT.getClaim("cognito:username").asString();
        UserDetails userDetails = this.userService.getUserDetails(username);

        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new CustomResponse(userDetails), HttpStatus.OK);
    }

}
