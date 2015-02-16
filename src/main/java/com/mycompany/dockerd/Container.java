/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dockerd;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 *
 * @author laursuom
 */
public abstract class Container {
    
    PrintWriter out;
    BufferedReader in;
    
    abstract void run();
    public void setWriter(PrintWriter out) {this.out = out;}
    public void setReader(BufferedReader in) {this.in = in;}
}
