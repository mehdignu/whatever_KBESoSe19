package de.berlin.htw.ai.kbe.interfaces;

//import de.htw.ai.kbe.PermissionController.Secured;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

//@Secured
public interface SongsDAO<T> {


    List<T> getAllRecords();


    Response getSingleRowRecord(Integer id);


    Response createRecord(T t);


    Response updateRecord(Integer id, T t);


    Response deleteRecord(Integer id);

}
