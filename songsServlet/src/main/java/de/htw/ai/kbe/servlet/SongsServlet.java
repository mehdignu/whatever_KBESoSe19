package de.htw.ai.kbe.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.htw.ai.kbe.servlet.DataHandler.DataStore;
import de.htw.ai.kbe.servlet.pojo.Song;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import de.htw.ai.kbe.servlet.DataHandler.utils.Constants;


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

        //get the full url
        String urlPath = request.getPathInfo();

        //verify the called URL
        if (!((urlPath.substring(urlPath.lastIndexOf("/") + 1)).equals("songsServlet")) || urlPath.lastIndexOf("/") != 0) {
            sendResponse(400, Constants.BAD_PARAMS, response);
        }

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

            int id = -1;

            try {
                id = Integer.parseInt(peep[0]);

            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(Constants.BAD_PARAMS);
            }

            sendResponse(200, "this is your song id = " + id, response);

        } else {

            //check if the url is valid
            if (!request.getParameterMap().isEmpty())
                sendResponse(400, Constants.BAD_PARAMS, response);

            //return all songs back
            sendResponse(200, accepts, dataSource.getAllSongs(), response);
        }


    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        ServletInputStream inputStream = request.getInputStream();
        byte[] inBytes = IOUtils.toByteArray(inputStream);
        try (PrintWriter out = response.getWriter()) {
            out.println(new String(inBytes));
        }
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
    private void sendResponse(int code, String contentType, List<Song> songsDelivery, HttpServletResponse response) throws IOException {
        response.setContentType(contentType);
        response.setStatus(code);
        try (PrintWriter out = response.getWriter()) {
            out.println("boo");
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