/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dockerd;

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
public class DockerDTest {
    
    public DockerDTest() {
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
        System.out.println("main");
        String[] args = {"testdata.txt"};
        ArgumentCaptor<ConnectionListener> arguments = ArgumentCaptor.forClass(ConnectionListener.class);
        //Mockito.when(DockerD.main(args));
        
        // TODO review the generated test code and remove the default call to fail.
    }
    
}
