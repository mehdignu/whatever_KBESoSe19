package de.htw.ai.kbe.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.ai.kbe.servlet.DataHandler.DataStore;
import de.htw.ai.kbe.servlet.DataHandler.utils.Constants;
import de.htw.ai.kbe.servlet.pojo.Song;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.XML;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SongsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(SongsServlet.class.getName());
    private DataStore dataSource;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

        this.dataSource = new DataStore();
        BasicConfigurator.configure();
        log.info("init()");

        //get songs xml file path
        String filePath = servletConfig.getServletContext().getRealPath(servletConfig.getInitParameter("datapath"));


        try {
            dataSource.loadSongs(filePath);
        } catch (IOException e) {
            throw new ServletException(e);
        }

    }


    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {


        //get the url params and save them in a map
        Map<String, String[]> params = request.getParameterMap();

        //get the accept headers
        String accepts = null;
        try {
            accepts = readAcceptHeader(request);
        } catch (IllegalArgumentException e) {
            sendResponse(406, e.getMessage(), response);
            return;
        }

        if (request.getParameterMap().containsKey(Constants.SEARCH_PARAM)) {

            String[] peep = params.get(Constants.SEARCH_PARAM);

            if (peep == null || peep.length != 1) {
                throw new IllegalArgumentException(Constants.BAD_PARAMS);
            }

            //get song ID
            int id = -1;

            try {
                id = Integer.parseInt(peep[0]);

            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(Constants.BAD_PARAMS);
            }


            //find the song by id, if found return it
            getSongById(response, accepts, id);

        } else {

            //check if the url is valid
            if (!request.getParameterMap().isEmpty())
                sendResponse(400, Constants.BAD_PARAMS, response);

            //return all songs back
            sendResponse(200, accepts, dataSource.getAllSongs(), response);

        }

    }

    /**
     * get back the required song from specific id
     * @param response
     * @param accepts
     * @param id
     * @throws IOException
     */
    private void getSongById(HttpServletResponse response, String accepts, int id) throws IOException {

        Song song = dataSource.getSong(id);

        if(song != null){
            sendResponse(200, accepts, Arrays.asList(song), response);
        } else {
            sendResponse(404, "Song with id "+ id + " not found", response);
        }

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        /**
        response.setContentType("text/plain");
        ServletInputStream inputStream = request.getInputStream();
        byte[] inBytes = IOUtils.toByteArray(inputStream);
        try (PrintWriter out = response.getWriter()) {
            out.println(new String(inBytes));
        }*/

        response.setContentType("application/json");

        /**
        Gson gson = new Gson();
        try {
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = request.getReader().readLine()) != null) {
                sb.append(s);
            }

            Song newSong = (Song) gson.fromJson(sb.toString(), Song.class);

            Status status = new Status();

            status.setSuccess(true);

            response.getOutputStream().print(gson.toJson(status));
            response.getOutputStream().flush();

        } catch (Exception ex) {
            ex.printStackTrace();
            Status status = new Status();
            status.setSuccess(false);
            status.setDescription(ex.getMessage());
            response.getOutputStream().print(gson.toJson(status));
            response.getOutputStream().flush();
        }
         */
        String xml_data = "";

        String payloadRequest = getBody(request);

        System.out.println("Hier die gelesene JSON-Daten");
        System.out.println(payloadRequest);

        try {
            JSONObject obj;
            obj = new JSONObject(payloadRequest);
            xml_data = XML.toString(obj);
        } catch(Exception e) {
            System.out.println("Hier Catch-Error");
            System.out.println(e);
        }

        //converting json to xml


        System.out.println("Hier die XML Form");
        System.out.println(xml_data);

    }

    /**
     * Hier wird body von JSON Objekt in ein String gelesen
     * @param request
     * @return
     * @throws IOException
     */
    public static String getBody(HttpServletRequest request) throws IOException {

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }

    /**
     * response that handles the songs delivery
     *
     * @param code
     * @param contentType
     * @param songsDelivery
     * @param response
     * @throws IOException
     */
    private void sendResponse(int code, String contentType, List<Song> songsDelivery, HttpServletResponse response)  {

        response.setContentType(contentType);
        response.setStatus(code);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(response.getOutputStream(), songsDelivery);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    /**
     * response that handles the error messages to the user
     *
     * @param code
     * @param msg
     * @param response
     * @throws IOException
     */
    private void sendResponse(int code, String msg, HttpServletResponse response) throws IOException {

        response.setContentType(Constants.TEXT_TYPE);
        response.setStatus(code);
        try (PrintWriter out = response.getWriter()) {
            out.println(msg);
        }
    }


    /**
     * extract the accept headers and check it's validity
     * @param request
     * @return
     */
    private String readAcceptHeader(HttpServletRequest request) {

        //extract accept headers
        String accepts = request.getHeader(Constants.ACCEPT_HEADER);

        //if accept header is empty use json as a return type
        if (accepts.isEmpty() || accepts.equals("*/*")) {
            accepts = Constants.JSON_TYPE;
        }

        // non application/json accept headers are not valid
        if (!accepts.equals(Constants.JSON_TYPE))
            throw new IllegalArgumentException("invalid content type");

        return accepts;
    }
}