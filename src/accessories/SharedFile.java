package accessories;

import java.io.File;

public class SharedFile{
	
	private File file;
	
	public SharedFile(File f){
		file = f;
	}
	
	public void set(File f){
		file = f;
	}
	
	public File get(){
		return file;
	}
	public File getAbsoluteFile(){
		return file.getAbsoluteFile();
	}
	
	@Override
	public String toString(){
		return file.getName();		
	}
	
}
