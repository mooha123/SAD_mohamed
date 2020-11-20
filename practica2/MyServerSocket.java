package practica2;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author moham
 */
public class MyServerSocket {
    private final ServerSocket serversocket;
    private Map <String, Socket> pendingConnections = new ConcurrentHashMap<>();


    public MyServerSocket(int port) throws IOException{
        serversocket = new ServerSocket(port);
    }
    
    public void listen() throws IOException {
        Socket socket = null;
        try {
            socket = serversocket.accept();
            new Thread(new ControlThread(socket)).start();

        } catch (IOException ex) {
            Logger.getLogger(MyServerSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private class ControlThread implements Runnable {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        
        public ControlThread (Socket socket) throws IOException {
            clientSocket = socket;
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }

        public void run(){
            try {
                String connectionNick = in.readLine();
                String remoteNick = in.readLine();

                System.out.println("Connection: " + connectionNick + " " + remoteNick);
                //Comprobamos que el nick no este en uso
                if (!pendingConnections.containsKey(remoteNick + " " + connectionNick)){
                    //Await in queue
                    pendingConnections.put(connectionNick + " " + remoteNick, clientSocket);

                }else {
                    //Start connection
                    Socket remoteSocket = pendingConnections.remove(remoteNick + " " +connectionNick);
                    System.out.println(clientSocket);
                    System.out.println(remoteSocket);
                    new Developer(clientSocket, remoteSocket).start();
                    new Developer(remoteSocket, clientSocket).start();
                }
  
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private class Developer extends Thread {
            private Socket rebut; 
            private Socket enviat;

            public Developer(Socket entry, Socket exit) {
                this.rebut = entry;
                this.enviat = exit;
            }
            
            public void run(){
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(rebut.getInputStream()));
                    PrintWriter out = new PrintWriter(enviat.getOutputStream(), true);

                    out.println("Oki");
                    System.out.println(in);
                    System.out.println(out);

                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        out.println(inputLine);
                        System.out.println(inputLine);
                    }
                    in.close();
                    out.close();
                    rebut.close();
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    
}
