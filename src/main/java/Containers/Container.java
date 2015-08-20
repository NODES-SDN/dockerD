package Containers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.mycompany.dockerd2.ContainerCommander;
import java.io.BufferedReader;
import java.io.PrintWriter;

/*
 * @author laursuom 
 */
public abstract class Container {

    PrintWriter out;
    BufferedReader in;
    public String id;
    public String port;
    public long time;
    public long leaseTime;

    public abstract void run();

    public void setWriter(PrintWriter out) {
        this.out = out;
    }

    public void setReader(BufferedReader in) {
        this.in = in;
    }

    /*
    Returns container's IP address and portnumber
     */
    protected void sendContainerInfo() {
        System.out.println(ContainerCommander.getContainerFieldValue(".NetworkSettings.IPAddress", id, out));
        out.write(",");
        System.out.println(ContainerCommander.getContainerFieldValue("(index (index .NetworkSettings.Ports \""+port+"/tcp\") 0).HostPort", id, out));
        //out.write(",");
        //System.out.println(id);
        //out.write(id);
        //out.println();
        out.flush();
    }

}
