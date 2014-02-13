package refactored;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import operations.KeyCommands;
import operations.Operation;

/**
 * This is a notepad program.  It reads .txt files.
 * @author Dustin
 */
public class NotepadRefactored {

    /////////////////////
    //    CONSTANTS    //
    /////////////////////
    
    final public static String PROGRAM_TITLE     = "Notepad - ";
    final public static String DEFAULT_FILE_NAME = "File.txt";    
    final public static FileNameExtensionFilter FILTER = new FileNameExtensionFilter("Text Document", "txt");
    
    /////////////////////
    //    VARIABLES    //
    /////////////////////
    
    private Display 				display;
    private NotepadMenu 			menu;
    private FileBehemoth			fileBrowser;
    private ArrayList<Operation> 	operations;
    private Notepad					notepad;
    

    private File        			currentFile; // The FILE that we're modifying
    //////////////////////////////////

    
    public NotepadRefactored(){
    	
    	currentFile = new File(DEFAULT_FILE_NAME);
    	
    	display = new Display(PROGRAM_TITLE, DEFAULT_FILE_NAME);
    	
        Style style = new Style();
        menu = new NotepadMenu();
        menu.setStyle(style);
   
        fileBrowser = new FileBehemoth(FILTER); 
        
        operations = new ArrayList<Operation>();
        //operations.add(new SaveAs("Save As"));
        //operations.add(new Open("Open"));
        //operations.add(new Exit("Exit"));
        
        menu.addOperations(operations);
        
    	
    	display.setMenuBar(menu.getMenuBar());
    	
    	notepad = new Notepad(style);
        
        // ADD THE Notepad to the display
    	display.addComponent(notepad.getPad(), BorderLayout.CENTER);
    	display.refresh();
    	
    	//KeyCommands kc = new KeyCommands();
    	
    	//notepad.addKeyListenerToTextArea(kc);
    	//display.addKeyListener(kc);
    	
    }

}