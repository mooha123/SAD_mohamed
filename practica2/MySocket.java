
package practica2;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author moham
 */
public class MySocket {
    
    public static final String HOST_NAME = "localhost";
    public static final int PORT = 6666;
    
    private BufferedReader in;
    private PrintWriter out;
    private String nick;
    private Socket s;
    
    public MySocket (String cliente1, String cliente2) throws IOException{
        try {
            s = new Socket(HOST_NAME, PORT);
            this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            this.out = new PrintWriter(s.getOutputStream(), true);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void enviarMensaje(final String message) {
        out.println(message);
    }
    public String recibirMensaje() throws IOException {
        return in.readLine();
    }
    
//    public void close () throws IOException{
//        in.close();
//        out.close();
//        s.close();
//    }
}
