package cr.brainstation.bsfinalproject.filters;

import com.auth0.jwk.GuavaCachedJwkProvider;
import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.interfaces.RSAKey;

import static cr.brainstation.bsfinalproject.constants.CognitoConstants.JWKS_TOKEN_ISSUER_PROPERTY;
import static cr.brainstation.bsfinalproject.constants.CognitoConstants.JWKS_URL_PROPERTY;
import static cr.brainstation.bsfinalproject.constants.CommonConstants.AUTHORIZATION_HEADER;

@Component
public class AuthenticationFilter implements Filter {

    private static final Logger LOG = LogManager.getLogger(AuthenticationFilter.class);

    @Value(JWKS_URL_PROPERTY)
    private String JWKS_URL;

    @Value(JWKS_TOKEN_ISSUER_PROPERTY)
    private String JWKS_TOKEN_ISSUER;

    private DecodedJWT decodedJWT;

    /**
     * Verifies the JWT Token sent by the user against Cognito's user pool.
     * If success, attaches the decoded jwt to the request's attributes.
     * If error, returns an unauthorized error code to the client.
     */
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String token = req.getHeader(AUTHORIZATION_HEADER);
        boolean validToken;

        if (StringUtils.isEmpty(token)) {
            LOG.error("An empty Authorization token was sent, the request is unauthorized.");
            validToken = false;

        } else {

            try {
                validToken = this.isTokenValid(token);
            } catch (JwkException e) {
                LOG.error("Exception while validating the Authorization token, the token was invalid.");
                validToken = false;
            } catch (JWTDecodeException e) {
                LOG.error("Exception while decoding the Authorization token, format was not the expected one.");
                validToken = false;
            }

        }

        if (validToken) {
            String tokenUse = this.decodedJWT.getClaim("token_use").asString();
            if (!tokenUse.equals("id")) {
                LOG.error("Exception while verifying Authorization token, it is not meant for identification, so it is invalid");
                validToken = false;

            }
        }

        if (validToken) {
            LOG.info("The Authorization Token is completely valid, granting access.");
            request.setAttribute("decodedJWT", this.decodedJWT);
            chain.doFilter(request, response);
        } else {
            LOG.error("An invalid Authorization token was sent, the request is unauthorized.");
            res.sendError(HttpStatus.UNAUTHORIZED.value());
        }
    }

    /**
     * Verifies the ID Token against Cognito's User Pool.
     *
     * @param token the ID Token to validate.
     * @return True if valid, false if invalid or expired.
     * @throws JwkException An exceptional case where the token is invalid or malformed.
     */
    private boolean isTokenValid(String token) throws JwkException, JWTDecodeException {
        boolean tokenValid = true;

        this.decodedJWT = JWT.decode(token);
        String kid = this.decodedJWT.getKeyId();

        UrlJwkProvider provider;

        try {
            provider = new UrlJwkProvider(new URL(JWKS_URL));
        } catch (MalformedURLException e) {
            throw new Error("A fatal unexpected error occurred, the JWKS Url is malformed");
        }

        GuavaCachedJwkProvider cachedProvider = new GuavaCachedJwkProvider(provider);
        Jwk jwk = cachedProvider.get(kid);
        RSAKey rsaKey = (RSAKey) jwk.getPublicKey();

        Algorithm algorithm = Algorithm.RSA256(rsaKey);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(JWKS_TOKEN_ISSUER)
                .build();

        try {
            this.decodedJWT = verifier.verify(token);
        } catch (Exception e) {
            LOG.error("There was an exception while verifying the Authorization Token");
            LOG.error(e.getMessage());
            tokenValid = false;
        }

        return tokenValid;
    }

}
