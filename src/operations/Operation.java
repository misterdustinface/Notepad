package operations;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Operation{
	
	private ActionListener actionListener = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			executeOp();
		}
	};
	
	public Operation(){} // CONSTRUCTOR
	
	public ActionListener getActionListener(){
		return actionListener;
	}
	
	public abstract void executeOp();
}
