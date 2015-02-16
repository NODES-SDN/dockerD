/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dockerd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author laursuom
 */
class UbuntuContainer extends Container {

    public UbuntuContainer() {
    }
     @Override
    public void run() {
        out.print("Hiyo! UbuntuContainer was called!\n");
            ProcessBuilder p = new ProcessBuilder("docker", "run", "-ia", "STDIN", "-a", "STDOUT", "ubuntu", "/bin/bash");
            p.inheritIO();
        try {
            p.start();
        } catch (IOException ex) {
            Logger.getLogger(UbuntuContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
            
       
        
    }

   
    
}
