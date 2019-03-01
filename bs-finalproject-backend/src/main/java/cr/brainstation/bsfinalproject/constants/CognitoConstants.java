package cr.brainstation.bsfinalproject.constants;

/**
 * Constants strings that are necessary to validate tokens through the Cognito User Pool
 */
public interface CognitoConstants {

    String JWKS_URL_PROPERTY = "${cognito.jwks.url}";
    String JWKS_TOKEN_ISSUER_PROPERTY = "${cognito.jwks.tokenissuer}";
    String CLIENT_ID = "7hb0aauqkteeh165sbo18fhb7r";
    String COGNITO_REGION = "us-east-2";
    String USER_POOL_ID = "us-east-2_TXm03ukKg";
    String USERNAME_CLAIM_KEY = "cognito:username";

}
