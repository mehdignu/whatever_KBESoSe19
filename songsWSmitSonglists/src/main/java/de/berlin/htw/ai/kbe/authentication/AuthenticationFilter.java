package de.berlin.htw.ai.kbe.authentication;

import de.berlin.htw.ai.kbe.errorhandler.GenericExceptionMapper;
import de.berlin.htw.ai.kbe.interfaces.Secured;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import javax.annotation.Priority;
import javax.naming.AuthenticationException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {


        // Get the Authorization header from the request
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Validate the Authorization header
        if (!isTokenBasedAuthentication(authorizationHeader)) {
            abortWithUnauthorized(requestContext);
            return;
        }

        // Extract the token from the Authorization header
        String token = authorizationHeader.trim();


        try {
            // Validate the token
            validateToken(token);

        } catch (Exception e) {
            abortWithUnauthorized(requestContext);
        }
    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) {

        // Check if the Authorization header is valid
        // The authentication scheme comparison must be case-insensitive
        return authorizationHeader != null;
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {

        // Abort the filter chain with a 401 status code response
        // The WWW-Authenticate header is sent along with the response
        //requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        requestContext.abortWith(new GenericExceptionMapper().toResponse(new NotAuthorizedException("")));
    }

    private void validateToken(String token) throws Exception {
        // Check if the token was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid

        try {

            Jwts.parser().setSigningKey(AuthenticationEndpoint.key).parseClaimsJws(token).getSignature();


        } catch (JwtException e) {
            throw new AuthenticationException("invalid token");
        }
    }
}



