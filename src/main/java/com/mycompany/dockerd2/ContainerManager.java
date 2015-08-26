/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dockerd2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author laursuom
 */
class ContainerManager {
    
    public HashMap<String, ArrayList<String>> clientsIds;
    public HashMap<String, Container> idsContainers;
    
    
    public ContainerManager() {
        clientsIds = new HashMap();
        idsContainers = new HashMap();
}
    
      public void storeContainer(String ip, String id, Container container) {
        if (!clientsIds.containsKey(ip)) {
            clientsIds.put(ip, new ArrayList());
        }
        clientsIds.get(ip).add(id);
        idsContainers.put(id, container);
    }

    /*
     Returns the IDs of the containers bound to a certain client.
     */
    public ArrayList getIds(String ip) {
        return clientsIds.get(ip);
    }
    public Set<String> getIds() {
        return idsContainers.keySet();
    }
    
}
