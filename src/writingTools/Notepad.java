package writingTools;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import accessories.SharedFile;
import operations.ExitOp;
import operations.NewInstanceOp;
import operations.OpenOp;
import operations.PrintOp;
import operations.SaveAsOp;
import operations.SaveOp;

/**
 * This is a notepad program.  It reads .txt files.
 * @author Dustin
 */
public class Notepad {

    /////////////////////
    //    CONSTANTS    //
    /////////////////////
    
    final public static String PROGRAM_TITLE     = "Notepad - ";
    final public static String DEFAULT_FILE_NAME = "File.txt";
    
    final public static int DEFAULT_WIDTH  = 400;
    final public static int DEFAULT_HEIGHT = 600;
    
    /////////////////////
    //    VARIABLES    //
    /////////////////////
    
    // FONT STUFF ///////////////////////
    private Font       font;
    private int        fontStyle;
    private int        fontSize;
    /////////////////////////////////////
    
    // DISPLAY STUFF /////////////////
    protected JFrame    	frame;    // THE DISPLAY
    protected JFileChooser jfc;   // For file choosing
    //////////////////////////////////
    
    // THE TOP MENU BAR //////////////
    private MenuBar   menuBar;  // The menu bar itself
    protected Menu    fileMenu; // The "File" section
    private MenuItem  miSaveAs;   // save as option
    private MenuItem  miSave;   // save option
    private MenuItem  miOpen;   // open option
    private MenuItem  miPrint;
    private MenuItem  miNewWindow; // opens a new window
    private MenuItem  miExit;   // exit option
    //////////////////////////////////
    
    // OPERATIONS ////////////////////
    private SaveOp save;
    private OpenOp open;
    private SaveAsOp saveAs;
    private ExitOp exit;
    private PrintOp print;
    private NewInstanceOp newWindow;
    //////////////////////////////////
    
    // FILE STUFF ////////////////////
    private SharedFile        sharedFile; // The FILE that we're modifying
    //////////////////////////////////
    
    // Notepad stuff //////////////////
    protected JTextPane   textArea;    // FOR WRITING
    protected JScrollPane sp;          // Scrolling for notepad
    ///////////////////////////////////
    
    public Notepad(){
    	initFrame();
        initDisplay();
        finishFrame();
        initActionListeners();
    }
    
    public static void main(String[] args) {
    	new Notepad();
    }
    
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    
    protected void addMenuToMenuBar(Menu menu){
    	menuBar.add(menu);
    }
    
    protected void resetTitle(){
    	frame.setTitle(PROGRAM_TITLE + sharedFile.toString());
    }
    
    protected String getCurrentFileName(){
    	return sharedFile.toString();
    }
    
    private void initFrame()
    {    	
        // CREATING FRAME WITH ALL OF IT'S GOODIES [DISPLAY]
        frame = new JFrame(PROGRAM_TITLE + DEFAULT_FILE_NAME);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        try{
        	frame.setIconImage(new ImageIcon(getClass().getResource("Icon.png")).getImage());
        }catch(NullPointerException npe){}
        frame.setVisible(true);
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        frame.setLocation((int)(Math.random() * 800), (int)(Math.random() * 400));
    }
    
    private void finishFrame()
    {
        // ADD THE MenuBar to the display
        frame.setMenuBar(menuBar);
        // ADD THE Notepad to the display
        frame.add(sp, BorderLayout.CENTER);
        
        // REFRESH
        frame.validate();
        frame.repaint();
    }
    
    private void initDisplay()
    {
        fontStyle 	= Font.PLAIN;
        fontSize  	= 14;
        font 		= new Font(Font.MONOSPACED, fontStyle, fontSize);
        
        ////////////////////////////////////////////////////////////////////////
        
        // SETTING THE CURRENT FILE TO DEFAULT
        sharedFile = new SharedFile(new File(DEFAULT_FILE_NAME));
        
        ////////////////////////////////////////////////////////////////////////
        
        // INITIALIZING THE MENU BAR
        menuBar  = new MenuBar();
        menuBar.setFont(font);
        
        // INITIALIZING THE FILE SECTION OF THE MENU BAR
        fileMenu = new Menu(); 
        fileMenu.setLabel("File"); // setting the name
        
        // INITIALIZING THE OPTIONS IN THE FILE SECTION
        miSaveAs 	= new MenuItem("Save As");
        miSave 		= new MenuItem("Save       (Ctrl + S)");
        miOpen 		= new MenuItem("Open       (Ctrl + O)");
        miPrint 	= new MenuItem("Print      (Ctrl + P)");
        miNewWindow = new MenuItem("New Window (Ctrl + N)");
        miExit 		= new MenuItem("Exit       (Esc)");
        
        // ADDING THE OPTIONS TO THE FILE SECTION
        fileMenu.add(miSaveAs);
        fileMenu.add(miSave);
        fileMenu.add(miOpen);
        fileMenu.add(miPrint);
        fileMenu.add(miNewWindow);
        fileMenu.addSeparator();
        fileMenu.add(miExit);
        
        // ADDING THE FILE SECTION TO THE MENU BAR
        menuBar.add(fileMenu);
        
        ////////////////////////////////////////////////////////////////////////
        
        // INITIALIZING THE TEXT AREA (area we type in)
        textArea = new JTextPane();
        textArea.setFont(font);
        
        // INITIALIZING THE SCROLLING STUFF (having it hold the text area) 
        // - It's a Notepad now!
        sp = new JScrollPane(textArea);
        
    }
    
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////    
   
    private void initActionListeners()
    {
    	save = new SaveOp(frame, textArea, sharedFile);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(OpenOp.txtDoc, OpenOp.txt);  
    	open = new OpenOp(frame, textArea, sharedFile, filter);
    	saveAs = new SaveAsOp(frame, save, sharedFile);
    	print = new PrintOp(textArea);
    	newWindow = new NewInstanceOp(this);
    	exit = new ExitOp(frame);
    	
        miSaveAs.addActionListener(saveAs.getActionListener());   
        miSave.addActionListener(save.getActionListener());
        miOpen.addActionListener(open.getActionListener());
        miPrint.addActionListener(print.getActionListener());
        miNewWindow.addActionListener(newWindow.getActionListener());
        miExit.addActionListener(exit.getActionListener());
        
        // Add "special keyboard commands"
        frame.addKeyListener(keyboardSpecialCommands);
        textArea.addKeyListener(keyboardSpecialCommands);
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////// 
    
    private KeyListener keyboardSpecialCommands = new KeyListener(){

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) { 
            keyPressedOp(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            
        }
    	
    };
    
    protected void keyPressedOp(KeyEvent e){
        int keyCode = e.getKeyCode();
        
        if(e.isControlDown())
        {
        	if(keyCode == KeyEvent.VK_S){
        		save.executeOp();
        	}
        	else if(keyCode == KeyEvent.VK_O){
        		open.executeOp();
        	}
        	else if(keyCode == KeyEvent.VK_P){
        		print.executeOp();
        	}
        	else if(keyCode == KeyEvent.VK_N){
        		newWindow.executeOp();
        	}
        }
        else if(keyCode == KeyEvent.VK_ESCAPE){
        	exit.executeOp();
        }
    }
}