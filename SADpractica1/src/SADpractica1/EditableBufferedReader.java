import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;


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
    static final int ENTER = 13; // Es necessita per Readline per acabar de llegir la line
    
    private Line l;
    //constructor
    public EditableBufferedReader(Reader r){
        super(r);
        this.l = new Line();        
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
	try{
        String[] cadena = {"/bin/sh", "-c", "stty raw </dev/tty"};
        //confirmar que se ha podido pasar!
            Runtime.getRuntime().exec(cadena).waitFor();
        }catch(Exception e){
                System.out.println("Not put Terminal in raw mode");
        }
            
    }
    
    public void unsetRaw(){
        //passa la consola de mode raw a mode cooked.
        //lo mismo que el setraw
	try{
        String[] cadena = {"/bin/sh", "-c", "stty sane </dev/tty"};
        //confirmar que se ha podido pasar!
            Runtime.getRuntime().exec(cadena).waitFor();
        }catch(Exception e){
            System.out.println("Not put Terminal in cooked mode");
        }
    }
    
    
    @Override	 
    public int read() throws IOException{
         this.setRaw();    
         int sim = super.read();	
    
         try{
	    
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
	    }catch (Exception e) {

	    } finally {
	    this.unsetRaw();
	}
	
        return sim;
    }
    
    @Override
    public String readLine() throws IOException{
        
	
        int i = -1;
        
	try{
		i=this.read();
        
		while(i!=EditableBufferedReader.ENTER){       
		    		
		    switch(i)
		    {       
		        case EditableBufferedReader.LEFT:
		            this.l.left();
		            break;
		            
		        case EditableBufferedReader.RIGHT:
		            this.l.right();
		            break;
		            
		        case EditableBufferedReader.END:
		            this.l.end();
		            break;
		            
		        case EditableBufferedReader.HOME:
		            this.l.home();
		            break;
		            
		        case EditableBufferedReader.INSERT:
		            this.l.commuteInsert();
		            break;
		            
		        case EditableBufferedReader.DELETE:
		            this.l.supr();
		            break;
		            
		        case EditableBufferedReader.BACKSPACE:
		            this.l.bksp();
		            break;
		            
		        default:
		            this.l.add((char)i);
			    
		    }	      
//		    System.out.print(l.getchar(i));	
		    System.out.print(l.getDisplayString());
		    i=this.read();	
		   
		}
        }catch (IOException e) {
            e.printStackTrace();
        } 

        return this.l.getLine();
    }
	    

}


