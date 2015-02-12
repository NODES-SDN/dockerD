/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dockerd;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
        /**
         *
         * @author Lauri Suomalainen
         */
import java.util.Scanner;

public class DockerD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Integer> ports = new ArrayList();
        if (args.length == 0) {
            System.out.println("Usage: Pass either a file with port numbers or the port numbers themselves as"
                    + "arguments");
            System.exit(0);
        } else {
            File portFile = new File(args[0]);
            if (portFile.exists()) {
                try {
                ports = parsePortsFromFile(portFile);
                } catch (FileNotFoundException e) {
                    handleFileError(e);
                }
            } else {
                ports = parsePortsFromArgumentList(args);
            }
            connectionListener listener = new connectionListener(ports);
            listener.run();
        }

    }

    private static ArrayList<Integer> parsePortsFromArgumentList(String[] args) {
        ArrayList<Integer> ports = new ArrayList();
        for (String port : args) {
            ports.add(Integer.parseInt(port));
        }
        return ports;
    }

    private static ArrayList<Integer> parsePortsFromFile(File portFile) throws FileNotFoundException {
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