
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author moham
 */
public class MySocket extends Socket{

    Socket s;
    BufferedReader in;
    PrintWriter out;
    
    public MySocket(String host, int port){
        try {
            this.s = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
        } catch (IOException ex) {
            Logger.getLogger(MySocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public MySocket(Socket socket){
        try{
            this.s = socket;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
        }catch (IOException ex) {
            Logger.getLogger(MySocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
        
    public void println(String st){
        out.println(st);
        out.flush();
    }

    public String readLine() {
        String st = null;
        try {
            st = this.in.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return st;
    }
       
       
    @Override
    public void close(){
        try {
            s.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public String read() throws IOException{
        String linia = in.readLine();
        return linia;
    }
 }
