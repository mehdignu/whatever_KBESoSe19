package de.berlin.htw.ai.kbe.authentication;

import de.berlin.htw.ai.kbe.entities.User;
import de.berlin.htw.ai.kbe.errorhandler.GenericExceptionMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.inject.Inject;
import javax.naming.AuthenticationException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Key;

@Path("/auth")
public class AuthenticationEndpoint {

    private EntityManager em;
    private EntityManagerFactory emf;

    @Inject
    public AuthenticationEndpoint(EntityManagerFactory emf) {
        this.emf = emf;
        em = emf.createEntityManager();
    }

    protected static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response authenticateUser(@QueryParam("userId") String userID,
                                     @QueryParam("secret") String password) {


        try {

            // Authenticate the user using the credentials provided
            authenticate(userID, password);

            // Issue a token for the user
            String token = issueToken();

            // Return the token on the response
            return Response.ok("\n --------- \n Your Token : \n" + token + " \n --------- \n").build();

        } catch (Exception e) {
            //return Response.status(Response.Status.FORBIDDEN).build();
            return new GenericExceptionMapper().toResponse(new ForbiddenException());
        }
    }


    private String issueToken() {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token

        return Jwts.builder().setSubject("songDB-PU").signWith(key).compact();
    }


    /**
     * check it the user exist in the database
     *
     * @param userId
     * @param pass
     * @throws Exception
     */
    private void authenticate(String userId, String pass) throws Exception {

        //hier liefert entschluesselter Passwort zurueck

        pass = reverseString(pass);
        System.out.println(pass);
        CriteriaBuilder criteriaBuilder = emf.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);

        Predicate predicateForUserID
                = criteriaBuilder.equal(userRoot.get("userId"), userId);

        Predicate predicateForPass
                = criteriaBuilder.equal(userRoot.get("password"), pass);

        Predicate finalPredicate
                = criteriaBuilder.and(predicateForUserID, predicateForPass);
        criteriaQuery.where(finalPredicate);


        if (em.createQuery(criteriaQuery).getResultList().size() != 1)
            throw new AuthenticationException("User does not exists in the database");

    }

    /**
     *  der Benutzer gibt immer umgekehrter Passwort und hiermit kann er enschluesselt werden
     * @param passAsString password des Benutzers
     * @return reversedString as String
     */
    private String reverseString(String passAsString) {
        String reversedString ="";
        for (int i = passAsString.length() - 1; i >= 0; i--) {
            reversedString = reversedString + passAsString.charAt(i);
        }
        return reversedString;
    }

}