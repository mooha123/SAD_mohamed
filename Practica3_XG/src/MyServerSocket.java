import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**

/**
 *
 * @author virtual
 */
public class MyServerSocket extends ServerSocket {
    MySocket s;
    public MyServerSocket(int port) throws IOException {
        super(port);
    }
    
    @Override
    public MySocket accept(){
        try {
            this.s = new MySocket(super.accept());
            return s;
        } catch (IOException ex) {
            Logger.getLogger(MyServerSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  null;
    }
    
    @Override
    public void close(){
        try {
            super.close();
        } catch (IOException ex) {
            Logger.getLogger(MyServerSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}
