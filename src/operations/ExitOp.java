package operations;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ExitOp extends Operation{

	private JFrame frame;
	
	public ExitOp(JFrame frame){
		this.frame = frame;
	}
	
	@Override
	public void executeOp() {
		int n = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if(n == JOptionPane.YES_OPTION){
			frame.setFocusable(false);
			frame.setVisible(false);
			frame.setEnabled(false);
			frame.dispose();
		}
	}

}
