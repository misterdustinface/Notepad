package operations;

import java.awt.print.PrinterException;

import javax.swing.JTextPane;

public class PrintOp extends Operation{

	private JTextPane textArea;
	public PrintOp(JTextPane textArea){
		this.textArea = textArea;
	}
	
	@Override
	public void executeOp() {
    	try {
			textArea.print();
		} catch (PrinterException e) {
			//e.printStackTrace();
		}
//    	PrinterJob job 		= PrinterJob.getPrinterJob();
//    	PageFormat format 	= job.pageDialog(job.defaultPage());
//    	job.setPrintable(textArea.);
//    	if(job.printDialog()){
//    		try {
//    			job.setJobName(currentFile.getName());
//				job.print();
//			} catch (PrinterException e) {
//				e.printStackTrace();
//			}
//    	}
	}

}
