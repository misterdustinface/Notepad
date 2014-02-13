package refactored;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.util.ArrayList;

import operations.Operation;

public class NotepadMenu {
    // THE TOP MENU BAR //////////////
    private MenuBar   menuBar;  // The menu bar itself
    private Menu      fileMenu; // The "File" section
    private ArrayList<MenuItem> menuItems;
    
//    private MenuItem  miSave;   // save option
//    private MenuItem  miOpen;   // open option
//    private MenuItem  miExit;   // exit option
    //////////////////////////////////
    
    public NotepadMenu(){
        // INITIALIZING THE MENU BAR
        menuBar  = new MenuBar();
        
        // INITIALIZING THE FILE SECTION OF THE MENU BAR
        fileMenu = new Menu(); 
        fileMenu.setLabel("File"); // setting the name
        
        // ADDING THE FILE SECTION TO THE MENU BAR
        menuBar.add(fileMenu);
    }
    
    public void addOperations(ArrayList<Operation> ops){
    	for(Operation op : ops){
    		MenuItem temp = new MenuItem(op.getName());
    		menuItems.add(temp);
    		// temp. set action listener = op.getListener
    	}
    	
        for(MenuItem item : menuItems){
        	fileMenu.add(item);
        }
    }
    
    public void setStyle(Style s){
    	menuBar.setFont(s.getFont());
    }
    
    public MenuBar getMenuBar(){
    	return menuBar;
    }
}
