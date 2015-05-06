package com.mycompany.dockerd2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author laursuom
 */
class NewUbuntuContainer extends Container {

    Process p;
    String PORT = "5000";

    public NewUbuntuContainer() {
        id = "";
    }

    @Override
    public void run() {
        out.println("Hiyo! UbuntuContainer was called!\n");
        //ProcessBuilder pb = new ProcessBuilder("/bin/cat", "-");
        ProcessBuilder pb = new ProcessBuilder("docker", "run", "-id", "-p", PORT, "ubuntu", "/bin/bash");
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

//    private class containerWriter implements Runnable {
//
//        PrintWriter output = new PrintWriter(p.getOutputStream(), true);
//        String message;
//
//        @Override
//        public void run() {
//            output.flush();
//            try {
//                while ((message = in.readLine()) != null && !(Boolean) processHasExited.get(p)) {
//                    System.out.println(message);
//                    output.write(message);
//                    output.flush();
//                }
//                output.close();
//            } catch (IOException | IllegalArgumentException ex) {
//                Logger.getLogger(UbuntuContainer.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
//
//    private class containerReader implements Runnable {
//
//        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
//        String message;
//        int tmp;
//
//        @Override
//        public void run() {
//            out.flush();
//            try {
//                while (!(Boolean) processHasExited.get(p)) {
//                    message = input.readLine();
//                    if (message != null) {
//                        System.out.println(message);
//                        out.println(message);
//                        out.flush();
//                    }
//                }
//            } catch (IOException | IllegalArgumentException | IllegalAccessException ex) {
//                Logger.getLogger(UbuntuContainer.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        }
//
//    }
//}
