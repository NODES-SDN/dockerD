package com.mycompany.dockerd2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Containers.Container;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author laursuom
 */
public class ConnectionListener {

    int port;
    HashMap<String, Class> commands = new HashMap();

    /*
     Sets the available containers and the port to which clients connect.
     */
    public ConnectionListener(int port, ContainerCommandFactory commandFactory) {
        this.port = port;
        this.commands = commandFactory.makeCommands();
    }

    public void run() {
        try {
            //Set up connections
            System.out.println("Portnumber is " + port);
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String ip = clientSocket.getRemoteSocketAddress().toString();
                System.out.println("Client connected from address " + ip + "!");
                if (DockerD.isOnIPWhitelist(ip)) { // if the connecting client isn't on the IP whitelist, the connection is denied.
                    DockerD.gui.addIP(ip);
                   new Thread(new UI(out, in, clientSocket)).start(); //Allocate UI for the client.
                } else {
                    out.println("Access denied!");
                    closeClientSocket(out, in, clientSocket);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ConnectionListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private class UI implements Runnable {

        PrintWriter out;
        BufferedReader in;
        Socket clientSocket;

        private UI(PrintWriter out, BufferedReader in, Socket clientSocket) {
            this.out = out;
            this.in = in;
            this.clientSocket = clientSocket;

        }

        @Override
        public void run() {

            try {
                while (true) {
                    //     out.println("Please write the name of the docker container you wish to launch");
                    StringTokenizer message = new StringTokenizer(in.readLine());
                    if (message.hasMoreElements()) {
                        String first = message.nextToken();
                        if (commands.containsKey(first)) {
                            Container temp = ContainerCommandFactory.makeANewContainer(commands.get(first).getName());
                            temp.setReader(in);
                            temp.setWriter(out);
                            temp.run();
                            ContainerManager.storeContainer(clientSocket.getRemoteSocketAddress().toString(), temp.id, temp);
                        } else if (first.equals("inspect")) {
                            if (message.countTokens() == 2) {
                                ContainerCommander.getContainerFieldValue(message.nextToken(), message.nextToken(), out);
                            } else if (message.countTokens() == 1) {
                                ContainerCommander.inspect(message.nextToken(), out);
                            } else {
                                out.println(listCommands());
                            }
                        } else if (first.equals("exec")) {
                            if (message.countTokens() == 2) {
                                ContainerCommander.exec(message.nextToken(), message.nextToken(), out);
                            } else {
                                out.println(listCommands());
                            }
                        } else if (first.equals("list")) {
                            ContainerCommander.list(out, clientSocket.getRemoteSocketAddress().toString());
                        } else {
                            out.println(listCommands());
                        }
                    }
                }
            } catch (NullPointerException np) {
                closeClientSocket(out, in, clientSocket);
            } catch (IOException ex) {
                System.err.println("U dun goofed sonny! (Really should get a better error message...)");
                System.err.println(ex.getMessage());
            }
        }
        
        /*
         Returns a list of all available commands.
         */

        private String listCommands() {
            StringBuilder string = new StringBuilder();
            string.append("Available commands are as follows:\n");
            for (String command : commands.keySet()) {
                string.append(command).append("\n");
            }
            string.append("inspect (field) <container id> \n");
            string.append("exec <container id> <command> \n");
            string.append("list");
            return string.toString();
        }
    }
    
    /*
     Deallocates resources after a client has disconnected.
     */

    protected void closeClientSocket(PrintWriter out, BufferedReader in, Socket clientSocket) {
        System.out.println("Client has disconnected");
        DockerD.gui.removeIP(clientSocket.getRemoteSocketAddress().toString());
        try {
            clientSocket.close();
            out.close();
            in.close();
        } catch (IOException e) {
            System.out.println("Error when closing the socket");
        }
    }
}
