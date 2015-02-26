package com.mycompany.dockerd;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author laursuom
 */
class UbuntuContainer extends Container {

    Process p;

    public UbuntuContainer() {
    }

    @Override
    public void run() {
        out.println("Hiyo! UbuntuContainer was called!\n");
        ProcessBuilder pb = new ProcessBuilder("docker", "run", "-i", "-a", "STDIN", "-a", "STDOUT",  "ubuntu", "/bin/bash");
        try {
            p = pb.start();

            new Thread(new containerWriter()).start();
            new Thread(new containerReader()).start();

            while (!in.readLine().contains("stop")) {
            };
        } catch (IOException ex) {
            Logger.getLogger(UbuntuContainer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private class containerWriter implements Runnable {

        PrintWriter output = new PrintWriter(p.getOutputStream(), true);
        String message;

        @Override
        public void run() {
            output.flush();
            try {
                while ((message = in.readLine()) != null) {
                    System.out.println(message);
                    output.write(message);
                    output.flush();
                }
            } catch (IOException ex) {
                Logger.getLogger(UbuntuContainer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private class containerReader implements Runnable {

        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String message;

        @Override
        public void run() {
            out.flush();
            try {
                while ((message = input.readLine()) != null) {
                    System.out.println(message);
                    out.print(message);
                    out.flush();
                    }
                
            } catch (IOException ex) {
                Logger.getLogger(UbuntuContainer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
}
