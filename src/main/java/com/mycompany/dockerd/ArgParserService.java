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
 * @author laursuom
 */
public final class ArgParserService {
    
    private ArgParserService(){/*You wouldn't construct a static class...*/};
    
    public static ArrayList<Integer> parsePortsFromArgumentList(String[] args) {
        ArrayList<Integer> ports = new ArrayList();
        for (String port : args) {
            ports.add(Integer.parseInt(port));
        }
        return ports;
    }

    public static ArrayList<Integer> parsePortsFromFile(File portFile) {
        ArrayList<Integer> ports = new ArrayList();
        try {
            Scanner scanner = new Scanner(portFile);
            while (scanner.hasNextInt()) {
                ports.add(scanner.nextInt());
            }
            return ports;
        } 
        
        catch (FileNotFoundException e) {
            handleFileError(e);
        }
       return ports;
    }

    private static void handleFileError(FileNotFoundException e) {
            System.out.println("Error opening a file:" + e.toString());
            System.err.print(e);
            System.exit(1);    }
}
