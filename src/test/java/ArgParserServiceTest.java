

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.mycompany.dockerd.ArgParserService;
import java.io.File;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

/**
 *
 * @author laursuom
 */
public class ArgParserServiceTest {

    public ArgParserServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class DockerD.
     */
    @Test
    public void testWithFile() {
        File file = new File("src/test/java//com/mycompany/dockerd/testdata.txt");
        ArrayList<Integer> ports = ArgParserService.parsePortsFromFile(file);
        assertEquals(666, (int) ports.get(0));
        assertEquals(813, (int) ports.get(1));
        assertEquals(80, (int) ports.get(2));

    }

    @Test
    public void testWithArgList() {
        String[] args = {"666","813","80"};
        ArrayList<Integer> ports = ArgParserService.parsePortsFromArgumentList(args);
        assertEquals(666, (int) ports.get(0));
        assertEquals(813, (int) ports.get(1));
        assertEquals(80, (int) ports.get(2));

    }
}
