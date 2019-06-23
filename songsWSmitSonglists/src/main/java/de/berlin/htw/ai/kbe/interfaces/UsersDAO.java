package de.berlin.htw.ai.kbe.interfaces;

import de.berlin.htw.ai.kbe.entities.User;

import javax.ws.rs.core.Response;

public interface UsersDAO {

    Response authenticateUser(String userID, String password);

    String getUserFromToken(String t);

    User getUserById(String userID);
}
