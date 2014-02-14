package writingTools;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.print.PrinterException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This is a notepad program.  It reads .txt files.
 * @author Dustin
 */
public class Notepad {

    /////////////////////
    //    CONSTANTS    //
    /////////////////////
    
    final public static String PROGRAM_TITLE     = "MathBox - ";
    final public static String DEFAULT_FILE_NAME = "File.txt";
    
    final public static int DEFAULT_WIDTH  = 400;
    final public static int DEFAULT_HEIGHT = 600;
    
    final public static String txtDoc = "Text Document";
    final public static String txt    = "txt";
    
    /////////////////////
    //    VARIABLES    //
    /////////////////////
    
    // FONT STUFF ///////////////////////
    private  static Font       font;
    private static int        fontStyle;
    private static int        fontSize;
    /////////////////////////////////////
    
    // DISPLAY STUFF /////////////////
    protected static JFrame    	frame;    // THE DISPLAY
    protected static JFileChooser jfc;   // For file choosing
    //////////////////////////////////
    
    // THE TOP MENU BAR //////////////
    private static MenuBar   menuBar;  // The menu bar itself
    protected static Menu    fileMenu; // The "File" section
    private static MenuItem  miSaveAs;   // save as option
    private static MenuItem  miSave;   // save option
    private static MenuItem  miOpen;   // open option
    private static MenuItem  miPrint;
    private static MenuItem  miExit;   // exit option
    //////////////////////////////////
    
    // FILE STUFF ////////////////////
    private static FileReader  in;          // Used for opening files
    private static File        currentFile; // The FILE that we're modifying
    //////////////////////////////////
    
    // Notepad stuff //////////////////
    protected static JTextPane   textArea;    // FOR WRITING
    protected static JScrollPane sp;          // Scrolling for notepad
    ///////////////////////////////////
    
    private static int keyCode;
    
    public Notepad(){
    	main(null);
    }
    
    public static void main(String[] args) {
    	initFrame();
        initDisplay();
        finishFrame();
        initActionListeners();
    }
    
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    
    protected static void setTitle(final String TITLE){
    	frame.setTitle(TITLE + currentFile.getName());
    }
    
    private static void initFrame()
    {    	
        // CREATING FRAME WITH ALL OF IT'S GOODIES [DISPLAY]
        frame = new JFrame(PROGRAM_TITLE + DEFAULT_FILE_NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        frame.setLocation((int)(Math.random() * 800), (int)(Math.random() * 400));
    }
    
    private static void finishFrame()
    {
        // ADD THE MenuBar to the display
        frame.setMenuBar(menuBar);
        // ADD THE Notepad to the display
        frame.add(sp, BorderLayout.CENTER);
        
        // REFRESH
        frame.validate();
        frame.repaint();
    }
    
    private static void initDisplay()
    {
        fontStyle 	= Font.PLAIN;
        fontSize  	= 14;
        font 		= new Font(Font.MONOSPACED, fontStyle, fontSize);
    	
        // INITIALIZING OTHER DISPLAY STUFF
        jfc    = new JFileChooser();
        
        // SET FILE TYPE FILTERS
        FileNameExtensionFilter filter = new FileNameExtensionFilter(txtDoc, txt);  
        // Put the filter on the file chooser
        jfc.setFileFilter(filter);
        
        ////////////////////////////////////////////////////////////////////////
        
        // SETTING THE CURRENT FILE TO DEFAULT
        currentFile = new File(DEFAULT_FILE_NAME);
        
        ////////////////////////////////////////////////////////////////////////
        
        // INITIALIZING THE MENU BAR
        menuBar  = new MenuBar();
        menuBar.setFont(font);
        
        // INITIALIZING THE FILE SECTION OF THE MENU BAR
        fileMenu = new Menu(); 
        fileMenu.setLabel("File"); // setting the name
        
        // INITIALIZING THE OPTIONS IN THE FILE SECTION
        miSaveAs = new MenuItem( "Save As");
        miSave = new MenuItem(   "Save  (Ctrl + S)");
        miOpen = new MenuItem(   "Open  (Ctrl + O)");
        miPrint = new MenuItem(  "Print (Ctrl + P)");
        miExit = new MenuItem(   "Exit  (Esc)");
        
        // ADDING THE OPTIONS TO THE FILE SECTION
        fileMenu.add(miSaveAs);
        fileMenu.add(miSave);
        fileMenu.add(miOpen);
        fileMenu.add(miPrint);
        fileMenu.addSeparator();
        fileMenu.add(miExit);
        
        // ADDING THE FILE SECTION TO THE MENU BAR
        menuBar.add(fileMenu);
        
        ////////////////////////////////////////////////////////////////////////
        
        // INITIALIZING THE TEXT AREA (area we type in)
        textArea = new JTextPane();
        textArea.setFont(font);
        //textArea.
        
        //converterModel = new KeywordConvertingModel(new File("src/mathwords.txt"));

        //textArea.setText(converterModel.toString()); // mostly for testing
        
        // INITIALIZING THE SCROLLING STUFF (having it hold the text area) 
        // - It's a Notepad now!
        sp = new JScrollPane(textArea);
        
    }
    
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////    
   
    private static void initActionListeners()
    {
        // Add a reaction to the SAVE button
        miSaveAs.addActionListener(saveAsAction);
        
        miSave.addActionListener(saveAction);
        
        // Add an action to the OPEN button
        miOpen.addActionListener(openAction);
        
        miPrint.addActionListener(printAction);
        
        miExit.addActionListener(exitAction);
        
        // Add "special keyboard commands"
        if(frame != null){
        	frame.addKeyListener(keyboardSpecialCommands);
        }

        textArea.addKeyListener(keyboardSpecialCommands);
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////// 
    
    private static void saveOp(File f)
    {
        // Attempt to do something
        try {
            // Make a bunch of shit that will allow us to write to the file
            FileWriter     fw = new FileWriter(f.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            // Write the text that's in our notepad to the file
            bw.write(textArea.getText());

            // Close the "stream"
            bw.close();

        // If the attempt fails, display the error message - then give up.
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(frame, "PROBLEM SAVING");
        }
    }
    
    public static ActionListener saveAction = new ActionListener(){
    	@Override
        public void actionPerformed(ActionEvent e) {
    		saveOp(currentFile);
        }
    };
    
    public static ActionListener saveAsAction = new ActionListener(){
    	@Override
        public void actionPerformed(ActionEvent e) {

            // Open up the Save file screen, if "Save" is pressed
            if(jfc.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION)
            {
               
                // Get the selected file
                currentFile = jfc.getSelectedFile();

                // if file doesn't exist, create it
                if (!currentFile.exists()) {
                    try {
                        currentFile.createNewFile();
                    } catch (IOException ex) {
                         JOptionPane.showMessageDialog(frame, "PROBLEM CREATING FILE");
                    }
                }

                // Saves the file
                saveOp(currentFile);

            }
            
            // Set the top title to the name of the document
            frame.setTitle(PROGRAM_TITLE + currentFile.getName());
        }
    };
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////// 
    
    private static void openOp(){
        // Open up the file selection screen, if "Open" is pressed
        if(jfc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
        {
            currentFile = jfc.getSelectedFile();
        }
            
        // Attempt to do something
        try {               
            // Make an inFile "stream" with the selected file
            in = new FileReader(currentFile);
            
             // Attempt to read in data [inFile all at once]
            try {
                textArea.read(in, currentFile);
                
            // If the attempt fails, display the error message - then give up.    
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "PROBLEM READING FILE");
            }
            
        // If the attempt fails, display the error message - then give up.    
        } catch (FileNotFoundException ex) {
            // User likely pressed "Cancel"
            //dialog.showMessageDialog(frame, "PROBLEM SETTING UP A FILE READER FOR THE FILE");
        }
        
        // Set the top title to the name of the document
         frame.setTitle(PROGRAM_TITLE + currentFile.getName());
    }
    
    private static ActionListener openAction = new ActionListener(){
    	 @Override
         public void actionPerformed(ActionEvent e) {
             openOp();
    	 }
    };
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////// 
    
    protected static KeyListener keyboardSpecialCommands = new KeyListener(){

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
                                 
            keyCode = e.getKeyCode();
            
            if(e.isControlDown())
            {
            	if(keyCode == KeyEvent.VK_S){
            		saveOp(currentFile);
            	}
            	else if(keyCode == KeyEvent.VK_O){
            		openOp();
            	}
            	else if(keyCode == KeyEvent.VK_P){
            		printOp();
            	}
            }
            else if(keyCode == KeyEvent.VK_ESCAPE){
            	exitOp();
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
            
        }
    	
    };
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    private static ActionListener printAction = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			printOp();
		}
    };
    
    private static void printOp(){
    	try {
			textArea.print();
		} catch (PrinterException e) {
			//e.printStackTrace();
		}
    	
//    	PrinterJob job 		= PrinterJob.getPrinterJob();
//    	PageFormat format 	= job.pageDialog(job.defaultPage());
//    	job.setPrintable(textArea.);
//    	if(job.printDialog()){
//    		try {
//    			job.setJobName(currentFile.getName());
//				job.print();
//			} catch (PrinterException e) {
//				e.printStackTrace();
//			}
//    	}
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    
	private static ActionListener exitAction = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			exitOp();
		}
	};
	
	private static void exitOp(){
		
		int n = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if(n == JOptionPane.YES_OPTION){
			frame.setFocusable(false);
			frame.setVisible(false);
			frame.setEnabled(false);
			frame.dispose();
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////

}