package refactored;

import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Notepad {
	
	private JTextArea 	textArea;
	private JScrollPane sp;
	
	public Notepad(Style style){
	    // INITIALIZING THE TEXT AREA (area we type in)
	    textArea = new JTextArea();
	    textArea.setFont(style.getFont());
	    
	    // INITIALIZING THE SCROLLING STUFF (having it hold the text area) 
	    // - It's a Notepad now!
	    sp = new JScrollPane(textArea);
	}
	
	public JScrollPane getPad(){
		return sp;
	}
	
	public JTextArea getTextArea(){
		return textArea;
	}
	
	public void addKeyListenerToTextArea(KeyListener kl){
		textArea.addKeyListener(kl);
	}
	
	public void write(File f){
		
	}

}
