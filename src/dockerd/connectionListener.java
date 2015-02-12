/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dockerd;
import java.util.ArrayList;
/**
 *
 * @author laursuom
 */
public class connectionListener {
    
    ArrayList<Integer> ports = new ArrayList();
    
    public connectionListener(ArrayList<Integer> ports){
        this.ports = ports;
    }

    void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
