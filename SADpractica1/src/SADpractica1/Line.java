/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SADpractica1;

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
    
     public boolean isIns() { //if true, inserts. If false substitueix
        return insert;
    }
        
    public void commuteInsert(){
        if(this.ins){
            this.ins=false;
        }else{
            this.ins=true;
        }
    }
}
