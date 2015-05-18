package com.mycompany.dockerd2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Containers.Container;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author laursuom
 */
public abstract class ContainerCommandFactory {

    abstract HashMap makeCommands();
    
    /*
    Creates new containers by container name.
    */
    public static Container makeANewContainer(String container) {
        try {
             Class<?> c = Class.forName(container);
             return (Container) c.newInstance();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException ex) {
            Logger.getLogger(ContainerCommandFactory.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
