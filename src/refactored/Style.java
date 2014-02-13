package refactored;

import java.awt.Font;

public class Style {
    // FONT STUFF ///////////////////////
    private Font       font;
    private int        fontStyle;
    private int        fontSize;
    /////////////////////////////////////
    
    public Style(){
        fontStyle 	= Font.PLAIN;
        fontSize  	= 14;
        font 		= new Font(Font.MONOSPACED, fontStyle, fontSize);
    }
    
    public Font getFont(){
    	return font;
    }
}
