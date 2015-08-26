package com.mycompany.dockerd2;

import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Lauri Suomalainen
 */
public class DockerD {
    static ContainerManager containerManager;
    public static GUI gui;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        if (args.length == 0) {
            System.err.println("Usage: DockerD <portnumber>");
            System.exit(0);
        } else {
            cleanContainerNameTags();
            containerManager = new ContainerManager();
            //            File portFile = new File(args[0]);
//            if (portFile.exists()) {
//                ports = ArgParserService.parsePortsFromFile(portFile);
//            } else {
//                ports = ArgParserService.parsePortsFromArgumentList(args);
//           }
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    System.out.println("Cloud Manager interrupted. Shutting down all the containers.");
                    try {
                        gui.dispose();
                        sleep(2);
                        cleanContainerNameTags();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(DockerD.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            ConnectionListener listener = new ConnectionListener(Integer.parseInt(args[0]), new DefaultCommands());
            gui = new GUI();
            listener.run();
        }

    }
    
    private static void cleanContainerNameTags(){
        String[] cmd = {
                        "/bin/sh",
                        "-c",
                        "docker kill $(docker ps -q) && docker rm server"
                    };

                    try {
                        
                        Runtime.getRuntime().exec(cmd);
                    } catch (IOException ex) {
                        Logger.getLogger(DockerD.class.getName()).log(Level.SEVERE, null, ex);
                    }
    }
}
