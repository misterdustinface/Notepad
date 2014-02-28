package writingTools;

//import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Font;
//import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
//import java.awt.PopupMenu;
//import java.awt.SystemTray;
//import java.awt.Toolkit;
//import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
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
    
    final public static String PROGRAM_TITLE     = "Notepad - ";
    final public static String DEFAULT_FILE_NAME = "File.txt";
    
    final public static int DEFAULT_WIDTH  = 400;
    final public static int DEFAULT_HEIGHT = 600;
    
    final public static String txtDoc = "Text Document";
    final public static String txt    = "txt";
    
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
    
    // FILE STUFF ////////////////////
    private FileReader  in;          // Used for opening files
    private File        currentFile; // The FILE that we're modifying
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
    
    protected void setTitle(final String TITLE){
    	frame.setTitle(TITLE + currentFile.getName());
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
   
    private void initActionListeners()
    {
        // Add a reaction to the SAVE button
        miSaveAs.addActionListener(saveAsAction);
        
        miSave.addActionListener(saveAction);
        
        // Add an action to the OPEN button
        miOpen.addActionListener(openAction);
        
        miPrint.addActionListener(printAction);
        
        miNewWindow.addActionListener(newWindowAction);
        
        miExit.addActionListener(exitAction);
        
        // Add "special keyboard commands"
        if(frame != null){
        	frame.addKeyListener(keyboardSpecialCommands);
        }

        textArea.addKeyListener(keyboardSpecialCommands);
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////// 
    
    private void saveOp(File f)
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
    
    private ActionListener saveAction = new ActionListener(){
    	@Override
        public void actionPerformed(ActionEvent e) {
    		saveOp(currentFile);
        }
    };
    
    private ActionListener saveAsAction = new ActionListener(){
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
    
    private void openOp(){
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
    
    private ActionListener openAction = new ActionListener(){
    	 @Override
         public void actionPerformed(ActionEvent e) {
             openOp();
    	 }
    };
    
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
        		saveOp(currentFile);
        	}
        	else if(keyCode == KeyEvent.VK_O){
        		openOp();
        	}
        	else if(keyCode == KeyEvent.VK_P){
        		printOp();
        	}
        	else if(keyCode == KeyEvent.VK_N){
        		newWindowOp();
        	}
        }
        else if(keyCode == KeyEvent.VK_ESCAPE){
        	exitOp();
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    private ActionListener printAction = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			printOp();
		}
    };
    
    private void printOp(){
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
    
	private ActionListener newWindowAction = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			newWindowOp();
		}
	};
	
	protected void newWindowOp(){
		new Notepad();
	}
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    
	private ActionListener exitAction = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			exitOp();
		}
	};
	
	private void exitOp(){
		
		int n = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if(n == JOptionPane.YES_OPTION){
			frame.setFocusable(false);
			frame.setVisible(false);
			frame.setEnabled(false);
			frame.dispose();
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////

//	public static void createSystemTrayIcon() {
//
//	    if (SystemTray.isSupported()) {
//	        SystemTray tray = SystemTray.getSystemTray();
//	        Image image =
//	            Toolkit.getDefaultToolkit()
//	            .getImage(
//	                    System.getenv("MY_PROGRAM_HOME") + "Icon.ico");
//
//	        PopupMenu popup = new PopupMenu();
//
//	        final MenuItem menuExit = new MenuItem("Quit");
//
//	        MouseListener mouseListener =
//	            new MouseListener() {
//	            public void mouseClicked(MouseEvent e) {
//	            }
//
//	            public void mouseEntered(MouseEvent e) {
//	            }
//
//	            public void mouseExited(MouseEvent e) {
//	            }
//
//	            public void mousePressed(MouseEvent e) {
//	            }
//
//	            public void mouseReleased(MouseEvent e) {
//	            }
//	        };
//
//	        ActionListener exitListener =
//	            new ActionListener() {
//	            public void actionPerformed(ActionEvent e) {
//	                Runtime r = Runtime.getRuntime();
//	                System.out.println("Exiting...");
//	                r.exit(0);
//	            }
//	        };
//
//	        menuExit.addActionListener(exitListener);
//	        popup.add(menuExit);
//
//	        final TrayIcon trayIcon = new TrayIcon(image, "My program", popup);
//
//	        ActionListener actionListener =
//	            new ActionListener() {
//	                public void actionPerformed(ActionEvent e) {
//	                    trayIcon.displayMessage("Notepad ","version: 1.0",
//	                            TrayIcon.MessageType.INFO);
//	            }
//	        };
//
//	        trayIcon.setImageAutoSize(true);
//	        trayIcon.addActionListener(actionListener);
//	        trayIcon.addMouseListener(mouseListener);
//
//	        try {
//	            tray.add(trayIcon);
//	        } catch (AWTException e) {
//	            System.err.println("TrayIcon could not be added.");
//	        }
//
//	    } else {
//	        //  System Tray is not supported
//	    }
//	}
	
}