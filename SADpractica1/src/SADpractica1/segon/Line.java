/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SADpractica1.Segon;


import static java.lang.Boolean.FALSE;
import java.util.ArrayList;

/**
 *
 * @author Anna
 */
public class Line extends Observable {
    private StringBuffer buffer;
    private int pos;
    private boolean insert;
    
    public Line(){
        this.buffer = new StringBuffer(1000);
        this.pos = 0;
        this.insert = FALSE; //LA TECLA INSERT ESTÀ DESACTIVADA X DEFECTE I 
        //PER TANT, NO ESBORRA EL CARACTER QUAN ESCRIUS
        
    }
    
     public boolean isIns() { //if true, inserts. If false no esborra
        return insert;
    }
        
    public void commuteInsert(){
        //this.insert = !this.insert; 
        //ho podriem fer així, pero no s'enten del tot. 
        //es pot posar així més endevant
        if(this.insert){
            this.insert=false;
        }else{
            this.insert=true;
        }
    }
    
    
    public void add(int i){
        if(this.insert){
	    if(pos < buffer.length()){
            	this.buffer.deleteCharAt(this.pos); 	
		}	  
        }
	this.buffer.insert(this.pos, (char)i);
	this.pos++;
        this.notify(new Constants(i));
    }
    
    public void right(){
        if(pos < buffer.length()){
            pos++;
            Constants k = new Constants(EditableBufferedReader.RIGHT);
            this.notify(k);
        }
    }
    
    public void left(){
        if(pos > 0){
            pos--;
            Constants k = new Constants(EditableBufferedReader.LEFT);
            this.notify(k);
        }
    }
    
    public void home(){
        pos = 0;
        Constants k = new Constants(EditableBufferedReader.HOME);
        this.notify(k);
    }
    
    public void end(){
        pos = buffer.length();
        Constants k = new Constants(EditableBufferedReader.END);
        this.notify(k);
    }
    
    public void supr(){
        if(pos < buffer.length()){
            buffer.deleteCharAt(pos);        
            Constants k = new Constants(EditableBufferedReader.SUPR);
            this.notify(k);
        }
    }
    
    public void bksp(){
        if(pos > 0){
            pos--;
            buffer.deleteCharAt(pos);
            Constants k = new Constants(EditableBufferedReader.BACKPACE);
            this.notify(k);
        }
    }
    
    //@Override
    public String getDisplayString() {
        StringBuilder displayString = new StringBuilder();
        displayString.append((char) 27);
        displayString.append("[2K");
        displayString.append('\r');
        displayString.append(this.buffer.toString());
        displayString.append(' ');
        displayString.append("\033[" + (1 + this.buffer.length() - pos) + "D");
        return displayString.toString();
    }

    private void notify(Constants k) {
        setChanged();
        notifyObservers(k);
        
    }
}
