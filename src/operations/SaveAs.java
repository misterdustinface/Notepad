package operations;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import refactored.FileBehemoth;

public class SaveAs extends Operation{

	private JTextArea textArea;
	private JFrame	  frame;
	private FileBehemoth filestuffs;

	public SaveAs(String name, JTextArea textArea, JFrame frame, FileBehemoth filestuffs) {
		super(name);
		this.textArea = textArea;
		this.frame = frame;
		this.filestuffs = filestuffs;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void perform() {
		filestuffs.saveAs();
	}

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

	@Override
	public void keyPressed(KeyEvent e) {
		keyCode = e.getKeyCode();
		if (e.isControlDown()){
			if(keyCode == KeyEvent.VK_S){
				perform();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
