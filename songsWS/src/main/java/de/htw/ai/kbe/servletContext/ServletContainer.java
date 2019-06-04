package de.htw.ai.kbe.servletContext;

import de.htw.ai.kbe.entities.Song;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import java.io.*;
import java.util.List;
import java.util.Properties;

@WebListener
public class ServletContainer implements ServletContextListener {

    private static ServletContext sc;
    private static List<Song> songFinal;

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //must be implemented
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        System.out.println("Initilizing ServletContextListener");
        System.out.println("Trying to open XML Data to load in DB");
        sc = sce.getServletContext();

        try {
            jaxbXmlFileToObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
        try {
            loadUsingClassMethod();
        } catch (Exception e) {
            System.out.println("caught the Error inside contextInitialized");
            e.printStackTrace();
        }
         */

    }

    private static void jaxbXmlFileToObject() throws IOException {

        Properties properties = new Properties();

        InputStream inStream = sc.getResourceAsStream("/WEB-INF/songs.xml");
        try {
            properties.load(inStream);
            JAXBContext jaxbContext;

            try {
                jaxbContext = JAXBContext.newInstance(Song.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                Song song = (Song) jaxbUnmarshaller.unmarshal(inStream);

                System.out.println(song);

            } catch (JAXBException e) {
                e.printStackTrace();
            }
        } finally {
            inStream.close();
        }
    }

}


