package de.htw.ai.kbe.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.ai.kbe.servlet.DataHandler.DataStore;
import de.htw.ai.kbe.servlet.pojo.Song;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class SongsServletTest {

    static String path = "songsTest.xml";
    private SongsServlet myServlet;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;


    private static void jaxbObjectToXML(Song employee)
    {
        try
        {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(Song.class);

            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            //Store XML to File
            File file = new File(path);

            //Writes XML file to file-system
            jaxbMarshaller.marshal(employee, file);
        }
        catch (JAXBException e)
        {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() throws Exception {
//        Song s1 = new Song(1, "Beamer Boy", "lil peep", "Beamer Boy", 2017);
//        Song s2 = new Song(2, "16 Lines", "li peep", "Come over when you're sober", 2018);
//
//
//        File xmlFile = new File(path);
//
//
//        myServlet = new SongsServlet();
//
        DataStore dataSource = new DataStore();

        dataSource.loadSongs("bo.xml");

        MockitoAnnotations.initMocks(this);

    }



    @AfterClass
    public static void tearDownClass() {
        File testFile = new File(path);
        if (testFile.exists()) {
            testFile.delete();
        }
    }


    @Test
    public void testDoGetAllSongs01() throws IOException, ServletException {


        myServlet.doGet(request, response);

        String SongsFileContent = new String(Files.readAllBytes(Paths.get(path)), "UTF-8");
        // assertEquals(result, SongsFileContent);
    }

}