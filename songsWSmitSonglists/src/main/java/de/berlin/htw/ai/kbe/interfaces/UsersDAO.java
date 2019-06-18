package de.berlin.htw.ai.kbe.interfaces;

import javax.ws.rs.core.Response;

public interface UsersDAO {

    Response authenticateUser(String userID, String password);

}
