package de.berlin.htw.ai.kbe;

import de.berlin.htw.ai.kbe.entities.User;
import de.berlin.htw.ai.kbe.interfaces.UsersDAO;
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
import javax.ws.rs.core.Response;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

public class DBUserDAO implements UsersDAO {

    protected Map<String, String> userTokenList;

    public static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    @Inject
    private static EntityManagerFactory emf;
    private EntityManager em;
    private User user;

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    private static DBUserDAO instance = null;

    public synchronized static DBUserDAO getInstance (){
        if (instance == null) {
            instance = new DBUserDAO();
        }
        return instance;
    }

    @Inject
    public DBUserDAO(EntityManagerFactory emf) {
        this.emf = emf;
        this.em = this.emf.createEntityManager();
        this.userTokenList = new HashMap<>();
    }

    public void inject(EntityManagerFactory emf){
        this.emf = emf;
    }

    @Inject
    public DBUserDAO() {
    }

    public String getUserFromToken(String t){

        return userTokenList.get(t);

    }

    public User getUserById(String userID){
         user = new User();
        em = getEntityManager();
        em.getTransaction().begin();
        try {
            user = em.find(User.class, userID);
            if (user != null) {
                System.out.println("getUserById: Returning user for id " + userID);
                return user;
            } else {
                return null;
            }
        } finally {
            em.getTransaction().commit();
            em.close();
        }
    }

    public Response authenticateUser(String userID, String password) {
        try {

            // Authenticate the user using the credentials provided
            authenticate(userID, password);

            // Issue a token for the user
            String token = issueToken(userID);

            //hier wird das Token abgespeicher. If already existed, the value will be updated
            userTokenList.put(token, userID);


            // Return the token on the response
            return Response.ok("\n --------- \n Your Token : \n" + token + " \n --------- \n").build();

        } catch (Exception e) {

            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    private String issueToken(String userID) {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token

        return Jwts.builder().setIssuer(userID).setSubject("songDB-PU").signWith(key).compact();
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
     * der Benutzer gibt immer umgekehrter Passwort und hiermit kann er enschluesselt werden
     *
     * @param passAsString password des Benutzers
     * @return reversedString as String
     */
    private String reverseString(String passAsString) {
        String reversedString = "";
        for (int i = passAsString.length() - 1; i >= 0; i--) {
            reversedString = reversedString + passAsString.charAt(i);
        }
        return reversedString;
    }
}
