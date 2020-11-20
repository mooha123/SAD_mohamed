
package practica2;

/**
 *
 * @author moham
 */
import java.net.*;
import java.io.*;
import java.util.logging.*;
import java.io.BufferedReader;

public class Client {
    
    public static class ReaderThread implements Runnable {
        private final MySocket socket;

        public ReaderThread(final MySocket socket) {
            this.socket = socket;
        }

        public void run() {
            while (true) {
                try {
                    System.out.println(socket.recibirMensaje());
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
//comunicarse con el servidor
    public static class WriterThread implements Runnable {
        private final MySocket socket;
        private final BufferedReader in;
        String línia;

        public WriterThread(final MySocket socket) {
            this.socket = socket;
            in = new BufferedReader(new InputStreamReader(System.in));
        }
        

        public void run() {
            try {
                //while ((línia = in.readLine()) != null)
                //escriure línia per socket;

                while ((línia = in.readLine()) != null) {
                    
                        this.socket.enviarMensaje(línia);

                }
            } catch (final IOException e) {
                    e.printStackTrace();
            }
        }
    }
    
    public static void main(String args[]) throws IOException{
    
        final MySocket socket = new MySocket("localhost"); //no sé si esta bien
        
        BufferedReader in;
        String línea;
        
        in = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("Conectado: " + socket);
        
        final ReaderThread ReaderThread = new ReaderThread(socket);
        final WriterThread WriterThread = new WriterThread(socket);
        
        new Thread(ReaderThread).start();

        try{
        while ((línea=in.readLine()) != null){
            
                //Leemos por teclado y enviamos el mensaje al servidor
                new Thread(WriterThread).start();
            }
        }catch(IOException e){
                System.out.println("No se ha podido mandar el mensaje: " + e.getMessage());
        }

 //readLine del teclat retorna null i tanca l’escriptura del socket. El fill del servidor que aten al client rep final de dades i acaba.       
        try{
        //Cerramos la conexión
        if (in != null) in.close();
        if (socket != null) socket.close();
        }
        catch(IOException e)
        {  
          System.out.println("Error cerrando la comunicación ...");
        }
        
    }
//    public static void main(String args[]){
//       try {
//         Client client = new Client("localhost", 6666);
//       } catch (InterruptedException ex) {
//            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
//       }
//   }
   
}
