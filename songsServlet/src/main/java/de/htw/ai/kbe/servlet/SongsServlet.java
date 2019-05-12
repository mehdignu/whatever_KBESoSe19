package de.htw.ai.kbe.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import de.htw.ai.kbe.servlet.DataHandler.utils.Constants;


public class SongsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(SongsServlet.class.getName());

    private String uriToDB = null;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

        BasicConfigurator.configure();
        log.info("init()");

        //get songs xml file path
        String filePath = servletConfig.getServletContext().getRealPath(servletConfig.getInitParameter("datapath"));


    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        Map<String, String[]> params = request.getParameterMap();

        
        String urlPath = request.getPathInfo();

        //verify the called URL
        if(!((urlPath.substring(urlPath.lastIndexOf("/") + 1)).equals("songsServlet")) || urlPath.lastIndexOf("/") != 0){
            sendResponse(400, Constants.BAD_PARAMS, response);
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
                if(!request.getParameterMap().isEmpty())
                    sendResponse(400, Constants.BAD_PARAMS, response);

                //return all songs back
                sendResponse(200, "all songs", response);
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


    private void sendResponse(int code, String msg, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setStatus(code);
        try (PrintWriter out = response.getWriter()) {
            out.println(msg);
        }
    }
}