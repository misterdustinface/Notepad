package operations;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Exit extends Operation{

	private JFrame frame;
	
	public Exit(String name, JFrame frame) {
		super(name);
		this.frame = frame;
	}

	@Override
	public void perform() {
		int n = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if(n == JOptionPane.YES_OPTION){
			frame.setFocusable(false);
			frame.setVisible(false);
			frame.setEnabled(false);
			frame.dispose();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		perform();		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_ESCAPE){
			perform();
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
