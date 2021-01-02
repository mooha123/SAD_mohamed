import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Client{
    
    String      appName     = "Chat en Swing";
    Client   mainGUI;
    JFrame      newFrame    = new JFrame(appName);
    JTextField  usernameChooser;
    JFrame      preFrame;
    JTextArea   mensajesChat;
    JScrollPane scrollMensajesChat;
    JTextField  tfMensaje;
    JButton     btEnviar;
    JList       lista;
    DefaultListModel model;
    private boolean conectado = false;
    private MySocket socket;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
                }
                Client mainGUI = new Client();
                mainGUI.preDisplay();
            }
        });
    }
    
    public Client(){
        super();
        this.socket = new MySocket("localhost", 3001);
        // System.out.println("Conectado: " + socket);
    }

    public void preDisplay() {
        newFrame.setVisible(false);
        preFrame = new JFrame(appName);
        usernameChooser = new JTextField(15);
        usernameChooser.addActionListener(new enterServerButtonListener());
        JLabel chooseUsernameLabel = new JLabel("Introduzca usuario");
        JButton enterServer = new JButton("Entrar al chat");
        enterServer.addActionListener(new enterServerButtonListener());
        JPanel prePanel = new JPanel(new GridBagLayout());
        prePanel.add(chooseUsernameLabel);
        prePanel.add(usernameChooser);
        
        //Configuraciones de ventana
        preFrame.add(BorderLayout.CENTER, prePanel);
        preFrame.add(BorderLayout.SOUTH, enterServer);
        preFrame.setSize(300, 200);
        preFrame.setLocationRelativeTo(null);
        preFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        preFrame.setVisible(true);
    }

    public void display() {
        mensajesChat = new JTextArea();
        mensajesChat.setEnabled(false); // La parte de los mensajes no se tiene que poder editar
        mensajesChat.setLineWrap(true); // Dividir las línea al llegar al max textarea
        mensajesChat.setWrapStyleWord(true); // Lineas partidas por palabras (espacios en blanco)
        scrollMensajesChat = new JScrollPane(mensajesChat);
        tfMensaje = new JTextField("");
        tfMensaje.requestFocusInWindow();
        tfMensaje.addActionListener(new sendMessageButtonListener());
        btEnviar = new JButton("Enviar");
        btEnviar.setBackground(Color.BLUE);
        btEnviar.addActionListener(new sendMessageButtonListener());
        model = new DefaultListModel();
        lista = new JList(model);
        
        Container c = newFrame.getContentPane();
        c.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        //Cuadro de los msg
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        c.add(scrollMensajesChat, gbc);
        
        //Lista usuarios
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        c.add(lista, gbc);
        
        // Texto (msg)
        gbc.gridwidth = 1;        
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;        
        gbc.insets = new Insets(0, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 1;
        c.add(tfMensaje, gbc);
        
        // Boton enviar
        gbc.weightx = 0;
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        c.add(btEnviar, gbc);
        
        newFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        newFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                socket.println(".desconectar");
                socket.close();
                newFrame.dispose();
                System.exit(0);
            }
        });
        newFrame.setSize(600, 500);
        newFrame.setLocationRelativeTo(null);
        newFrame.setVisible(true);
    }

    class sendMessageButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (tfMensaje.getText().length() < 1) {
                // No hacemos nada
            } else {
                mensajesChat.append("<" + username + ">:  " + tfMensaje.getText()+ "\n");
                socket.println(tfMensaje.getText());
                tfMensaje.setText("");
            }
            tfMensaje.requestFocusInWindow();
        }
    }
    // PP-D-60777307
    String  username;

    class enterServerButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            username = usernameChooser.getText();
            if (username.length() < 1){
            }else{
                socket.println(username);
                String condicion = socket.readLine();
                if (condicion.equals("ACK")) {
                    JOptionPane.showMessageDialog(null, "Usuario introducido ya existente.", "Error!",
                            JOptionPane.INFORMATION_MESSAGE);
                    usernameChooser.setText("");
                } else if(condicion.equals("ACK2")){
                    preFrame.setVisible(false);
                    conectado = true;
                    display();
                    OutputThread outThread = new OutputThread();
                    outThread.start();
                }
            }
        }
    }
    
    class OutputThread extends Thread{
        public void run(){
            while(conectado){
                String msg;
                msg = socket.readLine();
                switch (msg) {
                    case ".desconectar":
                        conectado = false;
                        break;
                    case ".updateList":
                        updateUsers();
                        break;
                    default:
                        mensajesChat.append(msg + "\n");
                        break;
                }
            }
        }
    }
    public int close (){
        socket.close();
        newFrame.dispose();
        return 0;
    }
    
    public void updateUsers(){
        String u;
        u = socket.readLine();
        String[] l = u.split(" ");
        model.removeAllElements();
        for (String l1 : l) {
            model.addElement(l1);
        }
    }
}