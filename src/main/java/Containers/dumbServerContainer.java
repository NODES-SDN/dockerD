/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Containers;

import com.mycompany.dockerd2.ContainerCommander;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author laursuom
 */
public class dumbServerContainer extends Container {
    public static String singletonId = null; //This class only allows one instance of itself
    Process p;
    String PORT = "15001";

    public dumbServerContainer() {
        id = "";
        time = System.currentTimeMillis();
        leaseTime = time + MILLISECONDS.convert(40, SECONDS);
        port = PORT;
    }

    @Override
    public void run() {

        if (p != null && processStillRunning()) { // If this container is still running, don't create a new one.
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
    /*
    Checks, if the container process is still running.
    */
    private boolean processStillRunning() {
        try {
            Field hasExited = p.getClass().getDeclaredField("hasExited");
            hasExited.setAccessible(true);
            return !(Boolean) hasExited.get(p);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException ex) {
            Logger.getLogger(dumbServerContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    /*
    Opens the container output to a XTerminal when the container is launched.
    */
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
