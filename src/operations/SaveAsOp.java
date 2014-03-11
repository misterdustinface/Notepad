package operations;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import accessories.SharedFile;


public class SaveAsOp extends Operation{

	private JFileChooser jfc;
	private SaveOp save;
	private JFrame frame;
	private SharedFile sharedFile;
	
	public SaveAsOp(JFrame frame, SaveOp save, SharedFile sharedFile) {
		this.save = save;
		this.frame = frame;
		jfc    		= new JFileChooser();
		this.sharedFile = sharedFile;
	}

	@Override
	public void executeOp() {
		// Open up the Save file screen, if "Save" is pressed
        if(jfc.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION)
        {
            // Get the selected file
            File currentFile = jfc.getSelectedFile();

            // if file doesn't exist, create it
            if (!currentFile.exists()) {
                try {
                    currentFile.createNewFile();
                } catch (IOException ex) {
                     JOptionPane.showMessageDialog(frame, "PROBLEM CREATING FILE");
                }
            }
            sharedFile.set(currentFile);
            
            // Saves the file
            save.executeOp();
            // Reset the top title
            frame.setTitle(currentFile.toString());
        }
	}

}
