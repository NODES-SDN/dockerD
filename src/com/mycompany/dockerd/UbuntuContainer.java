/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dockerd;

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

    public UbuntuContainer() {
    }

    @Override
    public void run() {
        out.println("Hiyo! UbuntuContainer was called!\n");
        ProcessBuilder pb = new ProcessBuilder("docker", "run", "-ia", "STDIN", "-a", "STDOUT", "ubuntu", "/bin/bash");
        pb.redirectErrorStream(true);

        try {
            Process p = pb.start();
            PrintWriter output = new PrintWriter(p.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while (true) {
                
                String message = in.readLine();
                output.write(message);
                String message2 = input.readLine();
                out.print(message2);
            }
        } catch (IOException ex) {
            Logger.getLogger(UbuntuContainer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
