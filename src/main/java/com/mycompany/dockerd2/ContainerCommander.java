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
public class ContainerCommander {

    /*
     Returns the requested container value as a string using 'docker inspect'.
     */
    public static String getContainerFieldValue(String field, String id, PrintWriter out) {
        ProcessBuilder pb = new ProcessBuilder("docker", "inspect", "--format", "'{{" + field + "}}'", id);
        try {
            Process p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String string = reader.readLine();
            string = string.substring(1, string.length() - 1);
            out.write(string);
            return string;
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

    public static String list(PrintWriter out, String ip) {

        String filter = buildFilter(ip);
        StringBuilder string = new StringBuilder();
        ProcessBuilder pb = new ProcessBuilder("docker", "ps", "--filter=", "'id=" + filter + "'");
       
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
    }

    private static String buildFilter(String ip) {
        CopyOnWriteArrayList ids = ContainerManager.getIds(ip);
        Iterator it = ids.iterator();
        StringBuilder string = new StringBuilder();
        while (it.hasNext()) {
            string.append(it.next());
            string.append("|");
        }
        System.out.println(string.substring(0, string.length()-1));
        return string.substring(0, string.length()-1);
    }
}
