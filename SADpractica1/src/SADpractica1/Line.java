import java.io.*;
import static java.lang.Boolean.FALSE;
import java.util.ArrayList;

/**
 *
 * @author Anna_Mohamed
 */
public class Line {
    public static int maxLength = 1200;


    private StringBuffer buffer;
    private int pos;
    private boolean insert = false;
    private boolean home = true;

    
    public Line(){
        this.buffer = new StringBuffer(this.maxLength);
        this.pos = 0;
 
    }
    
     public boolean isIns() { //if true, inserts. If false no esborra
        return insert;
    }
        

    public int getPos(){
        return this.pos;
    }

    public void setPos(int posicio){
        if (posicio == 0)
            this.home = true;
        this.pos = posicio;
    }

    public String getLine(){
        return this.buffer.toString();
    }

    public void add(char c){
        if(this.insert){
	    if(pos < buffer.length()){
            	this.buffer.deleteCharAt(this.pos); 	
		}	  
        }
	this.buffer.insert(this.pos, c);
	this.pos++;
        
    }
    
    public void right(){
        if(pos < buffer.length()){
            pos++;
        }
    }
    
    public void left(){
        if(pos > 0){
            pos--;
        }
        if (this.pos == 0)
            this.home = true;
    }
    
    public void home(){
        pos = 0;
    }
    
    public void end(){
        pos = buffer.length();
    }
    
    public void supr(){
        if(pos < buffer.length()){
            buffer.deleteCharAt(pos);
        }
    }
    
    public void bksp(){
        if(pos > 0){
            pos--;
            buffer.deleteCharAt(pos);
        }
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
    
     public char getchar(int s){
        return (char)s;
    } 
  
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
   // public String print(){
  //      String s="";
//
   //     for (Integer i : buffer.length()) {
 //           s=s+(char)i.intValue();
    //    }

   //     return s;
  //  } 
}
