package operations;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Open extends Operation{

	private FileReader in;
	
	public Open(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void perform() {
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		perform();
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		keyCode = arg0.getKeyCode();
		if (arg0.isControlDown()){
			if(keyCode == KeyEvent.VK_O){
				perform();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
