/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SADpractica1.segon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 *
 * @author dat
 */
public class EditableBufferedReader extends BufferedReader{
    //Atributos
/*
27 79 70 0 end
27 79 72 0 home
27 79 81 0 f2
27 79 82 0 f3
27 79 83 0 f4
27 91 49 53 126 0 f5
27 91 49 55 126 0 f6
27 91 49 56 126 0 f7
27 91 49 57 126 0 f8
27 91 50 48 126 0 f9
27 91 50 49 126 0 f10
27 91 50 52 126 0 f12
27 91 50 126 0 insert
27 91 51 126 0 delete
27 91 49 126 0 home
27 91 52 126 0 end
27 91 53 126 0 pageUp
27 91 54 126 0 pageDown
27 91 65 0 up
27 91 66 0 down
27 91 67 0 right
27 91 68 0 left
27 91 49 59 53 67 0 ctl-right
27 91 49 59 53 68 0 ctl-left
27 91 49 59 53 65 0 ctl-up
27 91 49 59 53 66 0 ctl-down
27 91 69 0 keypad-five
9 0 tab
33 0 bang
35 0 pound
36 0 dollarSign
37 0 percent
38 0 ampersand
40 0 openParen
*/
    static final int ESCAPE = 27;
    static final int EDICIO = 91;
    static final int RIGHT = 67;
    static final int LEFT = 68;
    static final int HOME = 72;
    static final int END = 70;
    static final int INSERT = 50;
    static final int DELETE = 51;
    static final int BACKSPACE = 127;
    static final int TILDE = 126;
    public static final int ENTER = 13; // Es necessita per Readline per acabar de llegir la line
    
    private Line l;
    private Console cons;
    //constructor
    public EditableBufferedReader(Reader r){
        super(r);
        l = new Line();
        cons = new Console(l);
        
    }
    
    //Mètodes
    public void setRaw(){
        //Primero debo utilizar el comando  'stty -echo raw' en la terminal
        //para que se ejecute en un terminal utilizo bin/sh
        //Read commands from the command_string operand
        //     instead of from the standard input.  Special
        //     parameter 0 will be set from the command_name op‐
        //     erand and the positional parameters ($1, $2, etc.)
        //     set from the remaining argument operands.

        String[] cadena = {"/bin/sh", "-c", "stty -echo raw </dev/tty"};
        //confirmar que se ha podido pasar!
        try{
            Runtime.getRuntime().exec(cadena).waitFor();
        }catch(Exception e){
                System.out.println("Not put Terminal in raw mode");
        }
            
    }
    
    public void unsetRaw(){
        //passa la consola de mode raw a mode cooked.
        //lo mismo que el setraw
        String[] cadena = {"/bin/sh", "-c", "stty -echo raw </dev/tty"};
        //confirmar que se ha podido pasar!
        try{
            Runtime.getRuntime().exec(cadena).waitFor();
        }catch(Exception e){
            System.out.println("Not put Terminal in cooked mode");
        }
    }
    
    
    public int read() throws IOException{
    //llegeix el següent caràcter o la següent tecla de cursor.
    // Supongo ==> Este metodo se invoca solo si readline lo ejecuta. 
    //tendremos que utilizar ASCII?¿?¿?¿
    
    //Primero debemos pasar a mode raw.
    this.setRaw();
    int sim = super.read();
    //No se si debemos retornar este numero o debemos pasarle la traduccion?¿¿??¿?
    if(sim == EditableBufferedReader.ESCAPE){
        sim = super.read();
        if(sim == EditableBufferedReader.EDICIO){
            sim = super.read();
            switch (sim){
                case EditableBufferedReader.RIGHT:
                    sim = EditableBufferedReader.RIGHT;
                    break;
                case EditableBufferedReader.LEFT:
                    sim = EditableBufferedReader.LEFT;
                    break;
                case EditableBufferedReader.HOME:
                    sim = EditableBufferedReader.HOME;
                    break;
                case EditableBufferedReader.END:
                    sim = EditableBufferedReader.END;
                    break;
                case EditableBufferedReader.INSERT:
                    sim = this.read();
                    if (sim == EditableBufferedReader.TILDE)
                        sim = EditableBufferedReader.INSERT;
                    else
                        sim = -1;
                    break;
                case EditableBufferedReader.DELETE:
                    sim = this.read();
                    if (sim == EditableBufferedReader.TILDE)
                        sim = EditableBufferedReader.DELETE;
                    else
                        sim = -1;
                    break;
                case EditableBufferedReader.BACKSPACE:
                    sim = EditableBufferedReader.BACKSPACE;
                    break;
                
            }  
           
        }else{
            sim =  EditableBufferedReader.ESCAPE;
        }         
    }
    this.unsetRaw();
        return 0;
    }
    
    public String readLine() throws IOException{
            
        int i;
        i=this.read();
        
        while(i!=ENTER){       
            switch(i)
            {
                case 0:
                    break;
                    
                case EditableBufferedReader.LEFT:
                    l.left();
                    break;
                    
                case EditableBufferedReader.RIGHT:
                    l.right();
                    break;
                    
                case EditableBufferedReader.END:
                    l.end();
                    break;
                    
                case EditableBufferedReader.HOME:
                    l.home();
                    break;
                    
                case EditableBufferedReader.INSERT:
                    l.commuteInsert();
                    break;
                    
                case EditableBufferedReader.DELETE:
                    l.supr();
                    break;
                    
                case EditableBufferedReader.BACKSPACE:
                    l.bksp();
                    break;
                    
                default:
                    l.add(i);
            }
                   
            i=this.read();
        }
        
        return l.print();
    }
    

}


