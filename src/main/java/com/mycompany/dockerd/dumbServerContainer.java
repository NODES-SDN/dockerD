/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dockerd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author laursuom
 */
public class dumbServerContainer extends Container{

    Process p;
    String PORT = "15001:15001/tcp";

    public dumbServerContainer() {
        id = "";
    }

    @Override
    public void run() {
        System.out.println("Hiyo! dumbServerContainer was called!\n");
        //ProcessBuilder pb = new ProcessBuilder("/bin/cat", "-");
        ProcessBuilder pb = new ProcessBuilder("docker", "run", "-d", "-p", PORT, "fleuri/dumbserver");
        //   ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", "docker run -it ubuntu /bin/bash < /dev/tty");
        pb.redirectErrorStream(true);
        try {

            p = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String message;
            message = input.readLine();
            id = message;
            System.out.println(ContainerCommander.getContainerFieldValue(".NetworkSettings.IPAddress", id, out));
            out.write(",");
            System.out.println(ContainerCommander.getContainerFieldValue("(index (index .NetworkSettings.Ports \"15001/tcp\") 0).HostPort", id, out));
            out.flush();

        } catch (IOException | SecurityException ex) {
            Logger.getLogger(UbuntuContainer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
