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
public class Line {
    private ArrayList<Integer> buffer;
    private int pos;
    private boolean insert;
    
    public Line(){
        this.buffer = new ArrayList<>();
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
        if(insert){
            buffer.set(pos, i);
            pos++;
        }else{
            buffer.add(pos, i);
            pos++;
        }
        
    }
    
    public void right(){
        if(pos < buffer.size()){
            pos++;
        }
    }
    
    public void left(){
        if(pos > 0){
            pos--;
        }
    }
    
    public void home(){
        pos = 0;
    }
    
    public void end(){
        pos = buffer.size();
    }
    
    public void supr(){
        if(pos < buffer.size()){
            buffer.remove(pos);
        }
    }
    
    public void bksp(){
        if(pos > 0){
            pos--;
            buffer.remove(pos);
        }
    }
    
    
    //@Override
    public String print(){
        String s="";

        for (Integer i : buffer) {
            s=s+(char)i.intValue();
        }

        return s;
    } 
}
