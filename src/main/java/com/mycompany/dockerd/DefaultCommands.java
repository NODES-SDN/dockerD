package com.mycompany.dockerd;

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
        Class ubuntu = OldUbuntuContainer.class;
        tempMap.put("ubuntu", ubuntu);
        return tempMap;
    }
}
