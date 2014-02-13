package operations;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public abstract class Operation implements ActionListener, KeyListener{

	private 	String 		name;
	protected 	int 		keyCode;
	
	public Operation(String name){
		this.name 		= name;
	}
	
	public String getName(){
		return name;
	}
	
	abstract public void perform();
}
