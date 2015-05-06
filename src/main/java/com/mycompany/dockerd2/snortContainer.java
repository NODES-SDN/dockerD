/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dockerd2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author laursuom
 */
public class snortContainer extends Container{

    Process p;
    String PORT = "5001";

    public snortContainer() {
        id = "";
    }

    @Override
    public void run() {
        out.println("Hiyo! snortContainer was called!\n");
        //ProcessBuilder pb = new ProcessBuilder("/bin/cat", "-");
        ProcessBuilder pb = new ProcessBuilder("docker", "run", "-d", "-p", PORT, "glanf/snort", "/bin/bash");
        //   ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", "docker run -it ubuntu /bin/bash < /dev/tty");
        pb.redirectErrorStream(true);
        try {

            p = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String message;
            message = input.readLine();
            System.out.println(message);
            out.println(message);
            out.flush();
            id = message;
            

        } catch (IOException | SecurityException ex) {
            Logger.getLogger(UbuntuContainer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
