/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2;

import java.io.IOException;
/**
 *
 * @author moham
 */
public class Server {
    
    public static void main(String args[]) throws IOException {
        MyServerSocket serverSocket = new MyServerSocket(6666);
        serverSocket.listen();
        //una vegada que ha rebut alguna cosa faig el start dels starts!!
    }
}
