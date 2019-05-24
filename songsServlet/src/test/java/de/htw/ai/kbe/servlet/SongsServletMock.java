package de.htw.ai.kbe.servlet;

/**
 * wir haben versucht so gut wie möglich die Voraussetzungen abzudecken, was auch schon in unserer Quellcode
 * gut gesehen ist. Letzter Schritt gab bisschen Schwirigkeiten unser Test mit der Quellcode zu verbinden
 * ich hatte staendig Probleme mit Deprecated Annotation und damit die init-Methode nicht vollstaendig initialisieren konnnte
 */

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class SongsServletMock {

    private static SongsServlet songsservlet;
    private static MockHttpServletRequest request;
    private static MockHttpServletResponse response;
    private static final String EXPECTED_RESPONSE_FOR_ALL = "[{\"id\":10,\"title\":\"7 Years\",\"artist\":\"Lukas Graham\",\"album\":\"Lukas Graham (Blue Album)\",\"released\":2015},{\"id\":9,\"title\":\"Private Show\",\"artist\":\"Britney Spears\",\"album\":\"Glory\",\"released\":2016},{\"id\":8,\"title\":\"No\",\"artist\":\"Meghan Trainor\",\"album\":\"Thank You\",\"released\":2016},{\"id\":7,\"title\":\"i hate u, i love u\",\"artist\":\"Gnash\",\"album\":\"Top Hits 2017\",\"released\":2017},{\"id\":6,\"title\":\"I Took a Pill in Ibiza\",\"artist\":\"Mike Posner\",\"album\":\"At Night, Alone.\",\"released\":2016},{\"id\":5,\"title\":\"Bad Things\",\"artist\":\"Camila Cabello, Machine Gun Kelly\",\"album\":\"Bloom\",\"released\":2017},{\"id\":4,\"title\":\"Ghostbusters (I'm not a fraid)\",\"artist\":\"Fall Out Boy, Missy Elliott\",\"album\":\"Ghostbusters\",\"released\":2016},{\"id\":3,\"title\":\"Team\",\"artist\":\"Iggy Azalea\",\"album\":\"\",\"released\":2016},{\"id\":2,\"title\":\"Mom\",\"artist\":\"Meghan Trainor, Kelli Trainor\",\"album\":\"Thank You\",\"released\":2016},{\"id\":1,\"title\":\"Canât Stop the Feeling\",\"artist\":\"Justin Timberlake\",\"album\":\"Trolls\",\"released\":2016}]";
    private static final String EXPECTED_RESPONSE_FOR_ALL_AFTER_NEW_POST = "[{\"id\":10,\"title\":\"7 Years\",\"artist\":\"Lukas Graham\",\"album\":\"Lukas Graham (Blue Album)\",\"released\":2015},{\"id\":9,\"title\":\"Private Show\",\"artist\":\"Britney Spears\",\"album\":\"Glory\",\"released\":2016},{\"id\":8,\"title\":\"No\",\"artist\":\"Meghan Trainor\",\"album\":\"Thank You\",\"released\":2016},{\"id\":7,\"title\":\"i hate u, i love u\",\"artist\":\"Gnash\",\"album\":\"Top Hits 2017\",\"released\":2017},{\"id\":6,\"title\":\"I Took a Pill in Ibiza\",\"artist\":\"Mike Posner\",\"album\":\"At Night, Alone.\",\"released\":2016},{\"id\":5,\"title\":\"Bad Things\",\"artist\":\"Camila Cabello, Machine Gun Kelly\",\"album\":\"Bloom\",\"released\":2017},{\"id\":4,\"title\":\"Ghostbusters (I'm not a fraid)\",\"artist\":\"Fall Out Boy, Missy Elliott\",\"album\":\"Ghostbusters\",\"released\":2016},{\"id\":3,\"title\":\"Team\",\"artist\":\"Iggy Azalea\",\"album\":\"\",\"released\":2016},{\"id\":2,\"title\":\"Mom\",\"artist\":\"Meghan Trainor, Kelli Trainor\",\"album\":\"Thank You\",\"released\":2016},{\"id\":1,\"title\":\"Canât Stop the Feeling\",\"artist\":\"Justin Timberlake\",\"album\":\"Trolls\",\"released\":2016},{\"id\":11,\"title\":\"Under my umburella\",\"artist\":\"Rihanna\",\"album\":\"Good Gril Gonna Bad\",\"released\":2008}]";
    private static final String EXPECTED_SONG = "{\"id\":10,\"title\":\"7 Years\",\"artist\":\"Lukas Graham\",\"album\":\"Lukas Graham (Blue Album)\",\"released\":2015}";
    private static final String NOT_EXISTING_SONG_ID = "13";
    private static final String CORRECT_POST = "{\"title\":\"Under my umburella\",\"artist\":\"Rihanna\",\"album\":\"Good Gril Gonna Bad\",\"released\":2008}";
    private static final String NON_JSON_POST = "<not json>";
    private static final String EXISTING_SONG_ID = "10";
    private static final String ERROR_FOR_BAD_SONG_ID = "Bad song id format.";
    private static final String ERROR_FOR_NOT_EXISTING_SONG = "Song ID does not exist.";
    private static final String ERROR_FOR_PARAMETER_NOT_EXISTING = "Parameter does not exist.";
    private static final String ERROR_FOR_WRONG_HEADER = "Accept-Header must be either \'*\' or \'application/json\'.";

    /**
     * This method is for the initialization of a SongsServlet, a Request and a Response instance.
     */
    @Before
    public void beforeClass() {
        songsservlet = new SongsServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    /**
     * This test case is for testing a do get request from the client with valid parameters and accept header.
     * @throws IOException as described
     */
    @Test
    public void testDoGetWithPassingCorrectId() throws IOException {
        request.setParameter( "songId", EXISTING_SONG_ID );
        request.addHeader("accept", "application/json");
        songsservlet.doGet( request, response );

        assertEquals( "Test case for doGet with passing an existing id",EXPECTED_SONG, response.getContentAsString());
        assertEquals( "Test case for doGet (error) with existing songID",null, response.getErrorMessage());
        assertEquals( "Test case for doGet (status) with not existing songID",200, response.getStatus());
    }

    /**
     * This test case is for testing a do get request from the client where the songId parameter is used with passing no id.
     * @throws IOException as described
     */
    @Test
    public void testDoGetWithPassingNoId() throws IOException {
        request.setParameter( "songId", "" );
        request.addHeader("accept", "application/json");
        songsservlet.doGet( request, response );

        assertEquals( "Test case for doGet with passing an existing id","Song with id 13 not found", response.getContentAsString());
        assertEquals( "Test case for doGet (error) with eisting songID",ERROR_FOR_BAD_SONG_ID, response.getErrorMessage());
        assertEquals( "Test case for doGet (status) with not eisting songID",400, response.getStatus());
    }

    /**
     * This test case is for testing a do get request from the client where the songId parameter is used but a not existing id is passed.
     * @throws IOException as described
     */
    @Test
    public void testDoGetWithPassingNotExistingId() throws IOException {
        request.setParameter( "songId", NOT_EXISTING_SONG_ID );
        request.addHeader("accept", "application/json");
        songsservlet.doGet( request, response );

        //assertEquals( "Test case for doGet (response body should be empty) with non existing songID","Song with id 13 not found", response.getContentAsString());
        assertEquals( "Test case for doGet (error) with not existing songID",null, response.getErrorMessage());
        assertEquals( "Test case for doGet (status) with not existing songID",404, response.getStatus());
    }

    /**
     * This test case is for testing a do get request from the client where the songId parameter is used but with bad value.
     * E.g. songId/ABC
     * @throws IOException as described
     */
    @Test
    public void testDoGetWithPassingBadParam() throws IOException {
        request.setParameter( "songId", "abcd" );
        request.addHeader("accept", "application/json");
        songsservlet.doGet( request, response );

        assertEquals( "Test case for doGet with passing an existing id","", response.getContentAsString());
        assertEquals( "Test case for doGet (error) with existing songID",ERROR_FOR_BAD_SONG_ID, response.getErrorMessage());
        assertEquals( "Test case for doGet (status) with not existing songID",400, response.getStatus());
    }

    /**
     * This test case is for testing a do get request from the client where a not existing parameter is used.
     * @throws IOException as described
     */
    @Test
    public void testDoGetWrongParam() throws IOException{
        request.setParameter( "title", "7 Years" );
        request.addHeader("accept", "application/json");
        songsservlet.doGet( request, response );

        assertEquals( "Test case for doGet with passing an existing id","Invalid GET parameters", response.getContentAsString());
        assertEquals( "Test case for doGet (error) with existing songID",ERROR_FOR_PARAMETER_NOT_EXISTING, response.getErrorMessage());
        assertEquals( "Test case for doGet (status) with not existing songID",400, response.getStatus());
    }

    /**
     * This test case is for testing a do get request from the client where the client is using a valid parameter for all songs
     * @throws IOException as described
     */
    @Test
    public void testDoGetAll() throws IOException {
        request.getParameterMap().isEmpty();
        request.addHeader("accept", "application/json");
        songsservlet.doGet( request, response );

        assertEquals( "Test case for doGet for all songs. Correct parameters.",EXPECTED_RESPONSE_FOR_ALL, songsservlet.getDataSource().getAllSongs());
        assertEquals( "Test case for doGet (error) for all songs",null, response.getErrorMessage());
        assertEquals( "Test case for doGet (status) for all songs",200, response.getStatus());
    }

    /**
     * This test case is for testing the case where do get request with wrong accept header is sent from the client.
     * It is expected that the request is rejected with status 406.
     * @throws IOException as described
     */
    @Test
    public void testWrongAcceptHeader() throws IOException {
        request.setParameter( "songId", EXISTING_SONG_ID );
        request.addHeader("accept", "application/xml");
        songsservlet.doGet( request, response );

        //assertEquals( "Test case for doGet (response body should be empty) for wrong response header","invalid content type ",response.getContentAsString());
        assertEquals( "Test case for doGet (error) for wrong response header",ERROR_FOR_WRONG_HEADER, response.getErrorMessage());
        assertEquals( "Test case for doGet (status) for wrong response header",406, response.getStatus());
    }

    /**
     * This test case is for testing a do post request from the client where the valid Content and accept header is passed.
     * @throws IOException as described
     */
    @Test
    public void testDoPostWithCorrectArguments() throws IOException {
        request.setContentType("application/json");
        request.setContent(CORRECT_POST.getBytes());
        songsservlet.doPost( request, response );

        assertEquals( "https://localhost:8080/songsServlet?songId=0","[https://localhost:8080/songsServlet?songId=0]", response.getHeaderValues("Location").toString());
        //assertEquals( "Test case for doPost (response body should be empty) for wrong response header","", response.getContentAsString());
        assertEquals( "Test case for doPost (status) with correct arguments",200, response.getStatus());

    }

    /**
     * This test case is for testing a do post request from the client where the content is in non json format.
     * The expected behaviour is that the request is getting rejected with response status 400
     * is expected.
     * @throws IOException as described
     */
    @Test
    public void testDoPostWithNoJson() throws IOException {
        request.setContentType("application/json");
        request.setContent(NON_JSON_POST.getBytes());
        songsservlet.doPost( request, response );

        assertEquals( "Test case for doPost (response body should be empty) for wrong response header","could not read song from stream", response.getContentAsString());
        assertEquals( "Test case for doPost (status) with correct arguments",400, response.getStatus());
    }

    /**
     * This test case is for testing a do post request from the client where the content is empty.
     * Expexted behavior is that the request is getting rejected with status 400
     * is expected.
     * @throws IOException as described
     */
    @Test
    public void testDoPostWithEmpty() throws IOException {
        request.setContentType("application/json");
        request.setContent("".getBytes());
        songsservlet.doPost( request, response );

        assertEquals( "Test case for doPost (response body should be empty) for wrong response header","", response.getContentAsString());
        assertEquals( "Test case for doPost (status) with correct arguments",400, response.getStatus());
    }

    /**
     *This test case is for testing the destroy method of the servlet. The expected behavior is that before destroy the
     *all new songs will be saved in the json file and can be retrieved after a restart.
     * @throws ServletException as described
     * @throws IOException as described
     */
    @Test
    public void testDestroy() throws ServletException, IOException {
        //add a new song
        request.setContentType("application/json");
        request.setContent(CORRECT_POST.getBytes());
        songsservlet.doPost( request, response );

        // restart the server
        songsservlet.destroy();
        songsservlet.init();

        // check if after the restart the new song can be read from the json file
        request.setParameter( "all", "songsList" );
        request.addHeader("accept", "application/json");
        songsservlet.doGet( request, response );
        assertEquals( "Test case for doPost, testing get all after new song posted.",EXPECTED_RESPONSE_FOR_ALL_AFTER_NEW_POST, response.getContentAsString());
    }

}
