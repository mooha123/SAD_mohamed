
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author moham
 */
public class MySocket extends Socket{
        public static final String HOST_NAME = "localhost";
    public static final int PORT = 6666;
    
    private BufferedReader in;
    private PrintWriter out;
    private String nick;
    private Socket s;
    
    public MySocket (String nick) throws IOException{
        try {
            s = new Socket(HOST_NAME, PORT);
            this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            this.out = new PrintWriter(s.getOutputStream(), true);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.nick = nick;
    }
    public void enviarMensaje(final String message) {
        out.print(nick + " " + message);
    }   
    public String recibirMensaje() throws IOException {
        return in.readLine();
    }
    
    public void close () throws IOException{
        in.close();
        out.close();
        s.close();
    }
}

