/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SADpractica1;

import java.io.BufferedReader;
/**
 *
 * @author dat
 */
public class EditableBufferedReader {
    //Atributos
    
    
    //constructor
    public EditableBufferedReader(){
    
    }
    
    //Funcions
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
    
    public void read(){
    //llegeix el següent caràcter o la següent tecla de cursor.
    // Supongo ==> Este metodo se invoca solo si readline lo ejecuta. 
    //tendremos que utilizar ASCII?¿?¿?¿
    
    //Primero debemos pasar a mode raw.
    this.setRaw();
    // ahora debemos leer, por lo tanto nos devuelve un numero de la tabla ASCII
    int sim = super.read();
    //No se si debemos retornar este numero o debemos pasarle la traduccion?¿¿??¿?

    } 
}
