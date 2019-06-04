package de.htw.ai.kbe.resource;

import de.htw.ai.kbe.entities.User;

import javax.ws.rs.core.Response;
import java.util.List;

public class UserResource extends ManageResource<User> {

    @Override
    protected List getListOfRecords() {
        return null;
    }

    @Override
    protected Response getSingleRecord(Integer id) {
        return null;
    }

    @Override
    protected Response createSingleRecord(User t) {
        return null;
    }

    @Override
    protected Response deleteSingleRecord(Integer id) {
        return null;
    }

    @Override
    protected Response updateSingleRecord(Integer id, User t) {
        return null;
    }

}
