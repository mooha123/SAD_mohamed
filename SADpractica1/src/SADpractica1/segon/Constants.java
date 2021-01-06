/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SADpractica1.segon;

/**
 *
 * @author Anna
 */
public class Constants {
    private boolean right=false;
    private boolean left=false;
    private boolean end=false;
    private boolean home=false;
    private boolean insert=false;
    private boolean supr=false;
    private boolean bksp=false;
    private int lletra=0;
    
    public Constants(int i){
        switch(i){
            case EditableBufferedReader.RIGHT: //right 
                this.right=true;
                break;

            case EditableBufferedReader.LEFT: // left
                this.left=true;
                break;
   
            case EditableBufferedReader.END: // end
                this.end=true;
                break;

            case EditableBufferedReader.HOME: // home
                this.home=true;
                break;

            case EditableBufferedReader.INSERT: // insert
                this.insert=true;
                break;

            case EditableBufferedReader.DELETE: // supr
                this.supr=true;
                break;
            
            case EditableBufferedReader.BACKSPACE: // bksp
                this.bksp=true;
                break;
                
            default:
                this.lletra=i;
                
        }  
    }
     public boolean isRight() {
        return right;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isEnd() {
        return end;
    }

    public boolean isHome() {
        return home;
    }

    public boolean isInsert() {
        return insert;
    }

    public boolean isSupr() {
        return supr;
    }

    public boolean isBksp() {
        return bksp;
    }
    
    public int getLletra() {
        return lletra;
    }
}
