/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dockerd;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
        /**
         *
         * @author Lauri Suomalainen
         */


public class DockerD {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        
        int port;
        if (args.length == 0) {
            System.err.println("Usage: DockerD <pornumber>");
            System.exit(0);
        } else {
//            File portFile = new File(args[0]);
//            if (portFile.exists()) {
//                ports = ArgParserService.parsePortsFromFile(portFile);
//            } else {
//                ports = ArgParserService.parsePortsFromArgumentList(args);
//           }
             ConnectionListener listener = new ConnectionListener(Integer.parseInt(args[0]), new DefaultCommands()); 
             listener.run();
        }

    }
}
