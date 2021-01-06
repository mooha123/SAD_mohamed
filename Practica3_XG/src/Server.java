import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author virtual
 */
public class Server implements Runnable {

    public static ConcurrentHashMap<String, MySocket> clients = new ConcurrentHashMap<String, MySocket>();
    public static boolean t = true;
    public static boolean conectado = true;
    MySocket s;
    String nombre;

    Server(String nick, MySocket s) {
        this.s = s;
        this.nombre = nick;
    }

    public static void main(String[] args) throws Exception {
        MyServerSocket ss = new MyServerSocket(3001);
        System.out.println("The server is running...");

        while (true) {
            MySocket ms = ss.accept();
            while (t) {
                // ms.println("Introduce tu nombre de usuario: ");
                String nombre = ms.readLine();
                if (clients.containsKey(nombre)) {
                    // ms.println("El nombre de usuario " + nombre + " ya existe.");
                    ms.println("ACK");
                } else {
                    ms.println("ACK2");
                    System.out.println("Se ha conectado " + nombre);
                    clients.put(nombre, ms);
                    new Thread(new Server(nombre, ms)).start();
                    t = false;
                }
            }
            t = true;
        }
    }

    public void run() {
        while (conectado){
            darLista();
            String linia = clients.get(nombre).readLine();
            if (linia.equals(".desconectar")) {
                conectado = false;
                clients.get(nombre).println(".desconectar");
                clients.get(nombre).close();
                clients.remove(nombre);
                darLista();
            }else{
                for (ConcurrentHashMap.Entry<String,MySocket> entry : clients.entrySet()){
                    if (!entry.getKey().equals(nombre)){
                        entry.getValue().println(nombre + ": " + linia);
                    }
                }
            }   
        }
    }   

    public void darLista(){
        String list ="";
        for (ConcurrentHashMap.Entry<String,MySocket> entry : clients.entrySet()){
            list =list + entry.getKey() + " ";
        }
        for (ConcurrentHashMap.Entry<String,MySocket> entry : clients.entrySet()){
            entry.getValue().println(".updateList");
            entry.getValue().println(list);   
        }
    }
}
