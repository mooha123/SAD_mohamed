
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
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public static class WriterThread implements Runnable {
        private final MySocket socket;
        private final BufferedReader in;

        public WriterThread(final MySocket socket) {
            this.socket = socket;
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        public void run() {
            while (true) {
                try {
                    socket.enviarMensaje(in.readLine());
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void main(String args[]) throws IOException{
    
        final MySocket socket = new MySocket("localhost", "remotehost"); //no sé si esta bien

        final ReaderThread readerThread = new ReaderThread(socket);
        final WriterThread writerThread = new WriterThread(socket);

        new Thread(readerThread).start();
        new Thread(writerThread).start();
    }

   
}
