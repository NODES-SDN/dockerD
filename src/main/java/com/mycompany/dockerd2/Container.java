package com.mycompany.dockerd2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 *
 * @author laursuom
 */
public abstract class Container {
    
    PrintWriter out;
    BufferedReader in;
    String id;
    long time;
    long leaseTime;
    
    abstract void run();
    public void setWriter(PrintWriter out) {this.out = out;}
    public void setReader(BufferedReader in) {this.in = in;}
    
}
