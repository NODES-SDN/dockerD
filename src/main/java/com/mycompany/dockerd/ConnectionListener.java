package com.mycompany.dockerd;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author laursuom
 */
public class ConnectionListener {

    int port;
    HashMap<String, Class> commands = new HashMap();
    ArrayList<Container> containers = new ArrayList();

    public ConnectionListener(int port, ContainerCommandFactory commandFactory) {
        this.port = port;
        this.commands = commandFactory.makeCommands();
    }

    public void run() throws InstantiationException, IllegalAccessException {
        try {
            //Set up connections
            System.out.println("Portnumber is " + port);
            ServerSocket serverSocket = new ServerSocket(port);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            
            while (true) {
                out.println("Please write the name of the docker container you wish to launch");
                String message = in.readLine();
                if (commands.containsKey(message)) {
                    Container temp = ContainerCommandFactory.makeANewContainer(commands.get(message).getName());
                    temp.setReader(in);
                    temp.setWriter(out);
                    containers.add(temp);
                    containers.get(0).run();
                    System.out.println("The id of the container:");
                    System.out.println(containers.get(0).id);
                    } else {
                    out.println(listCommands());
                }              
            }
        } catch (IOException e) {
            System.err.println("U dun goofed sonny! (Really should get a better error message...)");
            System.err.println(e.getMessage());
        } finally {System.exit(0);}
    }
    
    private String listCommands() {
        StringBuilder string = new StringBuilder();
        string.append("Available commands are as follows:\n");
        for(String command : commands.keySet()) {
            string.append(command + "\n");
        }
        return string.toString();
    }
}
