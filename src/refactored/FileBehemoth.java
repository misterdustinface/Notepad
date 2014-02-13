package refactored;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import operations.Save;

public class FileBehemoth {
	
	private JFileChooser 			jfc;   // For file choosing
	
	public FileBehemoth(FileNameExtensionFilter filter){
	    // INITIALIZING OTHER DISPLAY STUFF
	    jfc    = new JFileChooser();
	    // SET FILE TYPE FILTERS
	    // Put the filter on the file chooser
	    jfc.setFileFilter(filter);
	}
	
	public File getFile(final JFrame frame){
	    // Open up the Save file screen, if "Save" is pressed
		File currentFile = null;
		
	    if(jfc.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION)
	    {
	        // Get the selected file
	        currentFile = jfc.getSelectedFile();

	        // if file doesn't exist, create it
	        if (!currentFile.exists()) {
	            try {
	                currentFile.createNewFile();
	            } catch (IOException ex) {
	                 JOptionPane.showMessageDialog(frame, "FILE DOES NOT EXIST, AND COULD NOT CREATE A NEW ONE.");
	            }
	        }

	    }
	    
	    return currentFile;
	}
	
	public void openFile(Display display){
		
	}
	
	/**
	 * 
	 * @param currentFile
	 * @param textToSave
	 * @return Name of File
	 */
	public String saveAs(String textToSave){
		
		JFrame temp = new JFrame();
		File   currentFile;
		
        // Open up the Save file screen, if "Save" is pressed
        if(jfc.showSaveDialog(temp) == JFileChooser.APPROVE_OPTION)
        {
           
            // Get the selected file
            currentFile = jfc.getSelectedFile();

            // if file doesn't exist, create it
            if (!currentFile.exists()) {
                try {
                    currentFile.createNewFile();
                } catch (IOException ex) {
                     JOptionPane.showMessageDialog(temp, "PROBLEM CREATING FILE");
                }
            }
            
            // Attempt to do something
            try {
                // Make a bunch of shit that will allow us to write to the file
                FileWriter     fw = new FileWriter(currentFile.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                
                // Write the text that's in our notepad to the file
                bw.write(textToSave);

                // Close the "stream"
                bw.close();

            // If the attempt fails, display the error message - then give up.
            } catch (IOException ioe) {
                JOptionPane.showMessageDialog(temp, "PROBLEM SAVING");
            }

            return currentFile.getName();
            
        }else{
        	return "FILE_NOT_FOUND";
        }
	}

}
