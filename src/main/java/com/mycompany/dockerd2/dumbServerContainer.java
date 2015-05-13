/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dockerd2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;
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
        time = System.currentTimeMillis();
        leaseTime = time + MILLISECONDS.convert(10, SECONDS);
    }

    @Override
    public void run() {

        if (singletonId != null) {
            System.out.println("Called the already running Decision Server!");
            sendContainerInfo();
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
                new Thread(new openToTerminal(id)).start();
                sendContainerInfo();

            } catch (IOException | SecurityException ex) {
                Logger.getLogger(UbuntuContainer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void sendContainerInfo() {
        System.out.println(ContainerCommander.getContainerFieldValue(".NetworkSettings.IPAddress", singletonId, out));
        out.write(",");
        System.out.println(ContainerCommander.getContainerFieldValue("(index (index .NetworkSettings.Ports \"15001/tcp\") 0).HostPort", singletonId, out));
        out.println();
        out.flush();
    }

    private static class openToTerminal implements Runnable {

        String id;

        public openToTerminal(String id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                Runtime.getRuntime().exec("/usr/bin/xterm -e docker attach " + id).waitFor();
            } catch (IOException ex) {
                Logger.getLogger(dumbServerContainer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                System.err.println("Xterm interrupted");
            }
        }
    }

}
