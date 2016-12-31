package platformer.utility;

import javafx.scene.Node;

public class FocusFixer {
	private static Node element = null;
	
	public static void setFocusElement(Node element){
		FocusFixer.element = element;
	}
	
	public static void fixFocus(){
		if(element != null)
			element.requestFocus();
	}
}
