package com.mycompany.dockerd2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author laursuom
 */

/*
 A class containing commands for interracting with containers via Docker Daemon.
 */
public class ContainerCommander {
    
    /*
     Returns the requested container value as a string using 'docker inspect'.
     */
    public static String getContainerFieldValue(String field, String id, PrintWriter out) {
      
        ProcessBuilder pb = new ProcessBuilder("docker", "inspect", "--format", "{{"+field +"}}'", id);
        
        try {
            Process p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder string = new StringBuilder();
            string.append(reader.readLine());
            for (int i = 0; i < string.length(); i++) {
                if (string.charAt(i) == '\'') {
                string.deleteCharAt(i);
            }
            }
            if (out != null) {out.write(string.toString());}
            return string.toString();
        } catch (IOException ex) {
            Logger.getLogger(ContainerCommander.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /*
     Returns all the container values as Json using 'docker inspect'.
     */
    public static String inspect(String id, PrintWriter out) {

        StringBuilder string = new StringBuilder();
        ProcessBuilder pb = new ProcessBuilder("docker", "inspect", id);
        try {
            Process p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String tmp;

            while (true) {
                tmp = reader.readLine();
                if (tmp == null) {
                    break;
                } else {
                    string.append(tmp).append("\n");
                }
            }
        } catch (IOException | IllegalArgumentException | SecurityException ex) {
            Logger.getLogger(ContainerCommander.class.getName()).log(Level.SEVERE, null, ex);
        }
        out.write(string.toString());
        return string.toString();
    }

    /*
     Executes a given command to a given docker Container with docker exec
     */
    static String exec(String id, String command, PrintWriter out) {
       
        ProcessBuilder pb = new ProcessBuilder("docker", "exec", id, command);
        StringBuilder string = new StringBuilder();
        try {
            Process p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String tmp;

            while (true) {
                tmp = reader.readLine();
                if (tmp == null) {
                    break;
                } else {
                    string.append(tmp).append("\n");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ContainerCommander.class.getName()).log(Level.SEVERE, null, ex);
        }
        out.write(string.toString());
        return string.toString();
    }
    
/*
    Returns and prints the info of the containers by docker ps, filtered to those containers of which the client is an owner.
    */
    public static String list(PrintWriter out, String ip) {

        if (ContainerManager.getIds(ip) != null) {
            String filter = buildFilter(ip);
            StringBuilder string = new StringBuilder();
            ProcessBuilder pb = new ProcessBuilder("docker","ps", "--filter=id=" + filter);
            System.out.println(pb.command());
            try {
                Process p = pb.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String tmp;

                while (true) {
                    tmp = reader.readLine();
                    if (tmp == null) {
                        break;
                    } else {
                        string.append(tmp).append("\n");
                    }
                }
            } catch (IOException | IllegalArgumentException | SecurityException ex) {
                Logger.getLogger(ContainerCommander.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(string.toString());
            out.write(string.toString());
            out.println();
            return string.toString();
        } else {
            String string = "No containers found.";
            out.write(string);
            out.println();
            return string;
        }

    }

    /*
    Builds a filter for the containers bound to a certain client
    */
    
    private static String buildFilter(String ip) {
        CopyOnWriteArrayList ids = ContainerManager.getIds(ip);
        Iterator it = ids.iterator();
        StringBuilder string = new StringBuilder();
        while (it.hasNext()) {
            string.append(it.next().toString().substring(0, 4));
            string.append("|");
        }
        System.out.println(string.substring(0, string.length() - 1));
        return string.substring(0, string.length() - 1);
    }
}
