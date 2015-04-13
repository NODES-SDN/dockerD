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
public class dumbServerContainer extends Container {

    public static String singletonId = null;
    Process p;
    String PORT = "15001:15001/tcp";

    public dumbServerContainer() {
        id = "";
    }

    @Override
    public void run() {

        if (singletonId != null) {
            System.out.println("Called the already running Decision Server!");
            System.out.println(ContainerCommander.getContainerFieldValue(".NetworkSettings.IPAddress", singletonId, out));
            out.write(",");
            System.out.println(ContainerCommander.getContainerFieldValue("(index (index .NetworkSettings.Ports \"15001/tcp\") 0).HostPort", singletonId, out));
            out.println();
            out.flush();
        } else {
            System.out.println("Decision Server invoked! Sending IP address and Portnumber to client!");
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
                singletonId = message;
                System.out.println(ContainerCommander.getContainerFieldValue(".NetworkSettings.IPAddress", id, out));
                out.write(",");
                System.out.println(ContainerCommander.getContainerFieldValue("(index (index .NetworkSettings.Ports \"15001/tcp\") 0).HostPort", id, out));
                out.println();
                out.flush();

            } catch (IOException | SecurityException ex) {
                Logger.getLogger(UbuntuContainer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
