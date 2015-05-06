package com.mycompany.dockerd2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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
        Class ubuntu = NewUbuntuContainer.class;
        tempMap.put("ubuntu", ubuntu);
        tempMap.put("snort", snortContainer.class);
        tempMap.put("dumbserver", dumbServerContainer.class);
        return tempMap;
    }
}
