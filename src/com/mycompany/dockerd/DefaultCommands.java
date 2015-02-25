/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dockerd;

import java.util.HashMap;

/**
 *
 * @author laursuom
 */
public class DefaultCommands extends ContainerCommandFactory {
    
    public DefaultCommands(){};
    
    @Override
    public HashMap makeCommands() {
        HashMap<String, Class> tempMap = new HashMap();
        Class ubuntu = UbuntuContainer.class;
        tempMap.put("ubuntu", ubuntu);
        return tempMap;
    }
}
