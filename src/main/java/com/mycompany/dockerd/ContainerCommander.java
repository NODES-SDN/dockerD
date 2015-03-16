package com.mycompany.dockerd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
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
    public static String getContainerFieldValue(String field, String id) {
        ProcessBuilder pb = new ProcessBuilder("docker", "inspect", "--format", "'{{" + field + "}}'", id);
        try {
            Process p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            return reader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(ContainerCommander.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /*
     Returns all the container values as Json using 'docker inspect'.
     */
    public static String inspect(String id) {

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
        return string.toString();
    }

    static String exec(String id, String command) {
        ProcessBuilder pb = new ProcessBuilder("docker", "exec", id, command);
        try {
            Process p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            return reader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(ContainerCommander.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    }


