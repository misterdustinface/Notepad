package operations;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class SaveAsOp extends Operation{

	private JFileChooser jfc;
	private SaveOp save;
	private JFrame frame;
	private File   currentFile;
	
	public SaveAsOp(JFrame frame, File file, SaveOp save) {
		this.save = save;
		this.frame = frame;
		this.currentFile = file;
		jfc    = new JFileChooser();
	}

	@Override
	public void executeOp() {
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
            save.executeOp(currentFile);

        }
        
        // Reset the top title
        frame.setTitle(currentFile.toString());
	}

}
