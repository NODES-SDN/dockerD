/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dockerd;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author laursuom
 */
public class ConnectionListener {

    int port;

    public ConnectionListener(int port) {
        this.port = port;
    }

    public void run() {
        try {
            System.out.println("Portnumber is " + port);
            ServerSocket serverSocket = new ServerSocket(port);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            while (true) {
                out.println("Please write something.");
                String message = in.readLine();
                out.println("You wrote:" + message);
                Thread.sleep(1000);
                out.println("Your server also works.");
                Thread.sleep(1000);
                out.println("Congratulations.");
            }
        } catch (IOException e) {
            System.err.println("U dun goofed sonny! (Really should get a better error message...)");
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Interrupted!");
        } finally {System.exit(0);}
    }
}
