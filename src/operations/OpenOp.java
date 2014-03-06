package operations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class OpenOp extends Operation{

    final public static String txtDoc = "Text Document";
    final public static String txt    = "txt";
    
    private FileReader   in;          // Used for opening files
    private JFrame		 frame;
	private JFileChooser jfc;
	private JTextPane    textArea;
	private File 		 currentFile;
	
	public OpenOp(JFrame frame, JTextPane textArea, File file){
        jfc    = new JFileChooser();
        this.frame = frame;
        this.textArea = textArea;
        this.currentFile = file;
	}
	
	public OpenOp(JFrame frame, JTextPane textArea, File file, FileNameExtensionFilter filter){
        jfc    = new JFileChooser();
        this.frame = frame;
        this.textArea = textArea;
        this.currentFile = file;
        
        // SET FILE TYPE FILTERS
        // Put the filter on the file chooser
        jfc.setFileFilter(filter);
	}
	
	@Override
	public void executeOp() {
		// TODO Auto-generated method stub
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
        frame.setTitle(currentFile.toString());
	}

}
