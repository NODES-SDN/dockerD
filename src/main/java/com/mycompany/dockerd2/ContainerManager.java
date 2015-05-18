/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dockerd2;

import Containers.Container;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author laursuom
 */
public class ContainerManager implements Runnable {

    /* A HashMap containing the client IP as a key and another HashMap
     / as a value, that containing the id of a container and the container
     / itself.
     */
    
    /*
    CopyOnWriteArraylist may have a performance bottleneck with mutating functions.
    However, they aren't as common as read-only operations which are performed periodically
    so using it is justified. Further investigation probably needed.
    */
    
    private static volatile ConcurrentHashMap<String,CopyOnWriteArrayList<String>> clientsIds; 
    private static volatile ConcurrentHashMap <String, Container> idsContainers;

   
    
    public ContainerManager() {
        clientsIds = new ConcurrentHashMap();
        idsContainers = new ConcurrentHashMap();
    }

    /*
    Starts the periodic Keep-alive checks, which inspects containers for their lease time every
    thirty seconds, requesting a shutdown whenever their lease time is expired.
    */
    
    @Override
    public void run() {
        while (true) {
            System.out.println("Starting container keep-alive check!");
            for (Entry client : clientsIds.entrySet()) { //Get the clientlist
                for (String id : clientsIds.get(client.getKey().toString())) {
                    if (System.currentTimeMillis() > idsContainers.get(id).leaseTime) { //compare leasetime and current time
                        shutDownContainer(id, client.getKey().toString());
                    }
                }
            }
            try {
                System.gc(); //Notify the garbage collector to clean memory of removed containers.
                sleep(30000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ContainerManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /*
    Stores a container and its info to HashMaps.
    */
    
    public static void storeContainer(String ip, String id, Container container) {
       if (!clientsIds.containsKey(ip)) {
           clientsIds.put(ip, new CopyOnWriteArrayList());
       }   
           clientsIds.get(ip).add(id);
           idsContainers.put(id, container);
       } 
    
    /*
    Returns the IDs of the containers bound to a certain client.
    */
    
     public static CopyOnWriteArrayList getIds(String ip) {
        return clientsIds.get(ip);
    }
    
     /*
     Shuts down the container process and removes its data from the data structures
     */

    private void shutDownContainer(String id, String client) {
        System.out.println("Lease time for the container " +  id + " has expired! Stopping the container");
        ProcessBuilder pb = new ProcessBuilder("docker", "stop", id);
        try {
            pb.start();
            idsContainers.remove(id);
            clientsIds.get(client).remove(id);
            if (clientsIds.get(client).isEmpty()) {
                clientsIds.remove(client);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ContainerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
