/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;

/**
 *
 * @author moham
 */
public class MyServerSocket {
    private final ServerSocket serversocket;

    public MyServerSocket(int port) throws IOException{
        serversocket = new ServerSocket(port);
    }
    
    public Socket listen() throws IOException {
        Socket s = null;
        try {
            s = serversocket.accept();
//            new Thread(new algo(s)).start();  //supongo que va el server
        } catch (IOException ex) {
            Logger.getLogger(MyServerSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
    
}
