package operations;

public class NewInstanceOp extends Operation{

	private Class<?>  c;
	
	public NewInstanceOp(Object object){
		this.c = object.getClass();
	}
	
	@Override
	public void executeOp() {
		try {
			c.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
