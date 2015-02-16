/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dockerd;

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
    }

   
    
}
