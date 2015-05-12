/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dockerd2;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 *
 * @author laursuom
 */
public class ContainerManager implements Runnable {

    /* A HashMap containing the client IP as a key and another HashMap
     / as a value, that containing the id of a container and the container
     / itself.
     */
    private volatile HashMap<String, HashMap<String, Container>> containers;

    public ContainerManager() {
         containers = new HashMap();
   }
    
    @Override
    public void run() {
        for (Entry client : containers.entrySet()) { //Get the clientlist
            for (Entry id : containers.get(client.getKey().toString()).entrySet()) { //get to container ids of each client
               if ( System.currentTimeMillis() > containers.get(client.getKey().toString()).get(id.getKey().toString()).leaseTime) { //compare leasetime and current time
                   shutDownContainer(id.getKey().toString());
               }
                   
               
            }
        }
    }

    private void shutDownContainer(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
