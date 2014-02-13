package refactored;

import java.awt.Component;
import java.awt.MenuBar;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class Display {

    final static int DEFAULT_WIDTH  = 400;
    final static int DEFAULT_HEIGHT = 600;
    
    final private String PROGRAM_TITLE;
    private JFrame    	 frame;

	
    public Display(final String PROGRAM_TITLE, final String DEFAULT_FILE_NAME){
    	this.PROGRAM_TITLE = PROGRAM_TITLE;
        frame = new JFrame(PROGRAM_TITLE + DEFAULT_FILE_NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        frame.setLocation((int)(Math.random() * 800), (int)(Math.random() * 400));
    }
    
    public void resetName(String newName){
    	frame.setTitle(PROGRAM_TITLE + newName);
    }
    
    public void setMenuBar(MenuBar menu){
    	frame.setMenuBar(menu);
    }
    
    public void addComponent(Component c, Object constraints){
    	frame.add(c, constraints);
    }
    
    public void addKeyListener(KeyListener kl){
    	frame.addKeyListener(kl);
    }
    
    public void refresh(){
        frame.validate();
        frame.repaint();
    }
    
}
