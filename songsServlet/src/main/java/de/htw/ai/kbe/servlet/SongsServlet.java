package de.htw.ai.kbe.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

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

        // alle Parameter (keys)
        Enumeration<String> paramNames = request.getParameterNames();

        if (paramNames.hasMoreElements()) {

            if (!request.getParameterMap().containsKey("songId")) {
                sendResponse(400, "go away you", response);
            } else {
                //send back the wanted song
                sendResponse(200, "the wanted song", response);
            }
        } else {
            //check if the request have no parameters -> send all songs back
            sendResponse(200, "all the songs", response);
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