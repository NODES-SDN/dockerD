package com.mycompany.dockerd2;

import com.google.common.io.Files;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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

    /**
     * @param args the command line arguments
     */
    /*
    List of accepted connections.
    */
    private static ArrayList<String> ipWhiteList = new ArrayList();
    public static int port;

    public static void main(String[] args) {

        if (args.length == 0) {
            System.err.println("Usage: DockerD <portnumber>");
            System.exit(0);
        } else {
            cleanContainerNameTags(); //Cleanup for possible already running containers.
            ipWhiteList = readIpWhiteList(); //Get IP whitelist.
            new Thread(new ContainerManager()).start(); //Start the Container Manager.
//            for (int i = 0; i < ipWhiteList.size(); i++) {
//                System.out.println(ipWhiteList.get(i));
//            }
            //            File portFile = new File(args[0]);
//            if (portFile.exists()) {
//                ports = ArgParserService.parsePortsFromFile(portFile);
//            } else {
//                ports = ArgParserService.parsePortsFromArgumentList(args);
//           }
            Runtime.getRuntime().addShutdownHook(new Thread() { //Add a shutdownhook, which shuts down all the containers when this program is interrupted.
                @Override
                public void run() {
                    System.out.println("Cloud Manager interrupted. Shutting down all the containers.");
                        cleanContainerNameTags();
                }
            });
            ConnectionListener listener = new ConnectionListener(Integer.parseInt(args[0]), new DefaultCommands());
            port = Integer.parseInt(args[0]);
            listener.run();
        }

    }
    
    /*
    Kills all the running containers, removes the nametag 'server'.
    */
    private static void cleanContainerNameTags() {
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

    /*
    Reads the IP whitelist from a file.
    */
    private static ArrayList<String> readIpWhiteList() {
        List<String> list = new ArrayList();
        try {
            list = Files.readLines(new File("IPWhitelist"), Charset.defaultCharset());
        } catch (IOException ex) {
            System.out.println("IPWhitelist not found! Aborting!");
            Logger.getLogger(DockerD.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        return (ArrayList) list;
    }

    /*
    Checks, if the given IP-address is on the whitelist.
    */
    
    public static boolean isOnIPWhitelist(String IP) {
        String[] tmpIP = IP.split(":");
        tmpIP[0] = tmpIP[0].substring(1);
        return ipWhiteList.contains(tmpIP[0]);
    }
}
