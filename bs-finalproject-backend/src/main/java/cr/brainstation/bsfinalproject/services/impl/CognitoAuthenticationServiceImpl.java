package cr.brainstation.bsfinalproject.services.impl;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClient;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import com.amazonaws.services.cognitoidp.model.SignUpResult;
import cr.brainstation.bsfinalproject.constants.CognitoConstants;
import cr.brainstation.bsfinalproject.constants.ResourceBundleConstants;
import cr.brainstation.bsfinalproject.i18n.ResourceBundleLoader;
import cr.brainstation.bsfinalproject.model.User;
import cr.brainstation.bsfinalproject.services.AuthenticationService;
import cr.brainstation.bsfinalproject.services.UserService;
import cr.brainstation.bsfinalproject.utils.exceptions.EmptyFieldException;
import cr.brainstation.bsfinalproject.utils.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.EMPTY_USER_EMAIL_MSG;
import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.EMPTY_USER_NAME_MSG;
import static cr.brainstation.bsfinalproject.constants.ResourceBundleConstants.EMPTY_USER_PASSWORD_MSG;

@Service("authenticationService")
@Transactional
public class CognitoAuthenticationServiceImpl implements AuthenticationService {

    private UserService userService;

    public CognitoAuthenticationServiceImpl() {
    }

    @Autowired
    public CognitoAuthenticationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    /**
     * @see AuthenticationService#signUp(User)
     */
    public void signUp(User user) {

        if (StringUtils.isEmpty(user.getEmail())) {
            throw new EmptyFieldException(ResourceBundleLoader.getDefaultBundle()
                    .getString(EMPTY_USER_EMAIL_MSG));
        }

        if (StringUtils.isEmpty(user.getPassword())) {
            throw new EmptyFieldException(ResourceBundleLoader.getDefaultBundle()
                    .getString(EMPTY_USER_PASSWORD_MSG));
        }

        if (StringUtils.isEmpty(user.getName())) {
            throw new EmptyFieldException(ResourceBundleLoader.getDefaultBundle()
                    .getString(EMPTY_USER_NAME_MSG));
        }

        if (this.userService.getUserByEmail(user.getEmail()) != null) {
            throw new UserAlreadyExistsException(ResourceBundleLoader.getDefaultBundle()
                    .getString(ResourceBundleConstants.USER_ALREADY_EXISTS_MSG));
        }

        // Builds a Cognito client for sign up and confirmation
        AWSCognitoIdentityProviderClientBuilder clientBuilder = AWSCognitoIdentityProviderClient.builder();
        clientBuilder.setRegion(CognitoConstants.COGNITO_REGION);
        AWSCognitoIdentityProvider idp = clientBuilder.build();

        // Builds a Sign Up Request
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername(user.getEmail());
        signUpRequest.setPassword(user.getPassword());
        signUpRequest.setClientId(CognitoConstants.CLIENT_ID);

        // Sends the sign up request to Cognito
        SignUpResult signUpResult = idp.signUp(signUpRequest);
        user.setId(signUpResult.getUserSub());
        this.userService.create(user);
    }

}
