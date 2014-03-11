package operations;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

public class SaveOp extends Operation{

	private JFrame 		frame;
	private JTextPane 	textArea;
	private File 		file;
	
	public SaveOp(JFrame frame, JTextPane textArea, File file){
		this.frame 		= frame;
		this.textArea 	= textArea;
		this.file  		= file;
	}
	
	public void executeOp(File currentFile){
		this.file = currentFile;
		executeOp();
	}
	
	@Override
	public void executeOp() {
        // Attempt to do something
        try {
            // Make a bunch of shit that will allow us to write to the file
            FileWriter     fw = new FileWriter(file.getAbsoluteFile());
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

}
