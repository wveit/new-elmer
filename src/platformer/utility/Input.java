package platformer.utility;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class Input extends HBox{
	
	Runnable okRunnable = null;
	Runnable cancelRunnable = null;
	TextField inputTextField;
	String code = "";
	
	public Input(){
		inputTextField = new TextField();
		Button okButton = new Button("OK");
		Button cancelButton = new Button("Cancel");
		
		getChildren().addAll(inputTextField, okButton, cancelButton);
		
		okButton.setOnAction(e->{if(okRunnable != null) okRunnable.run();});
		cancelButton.setOnAction(e->{if(cancelRunnable != null) cancelRunnable.run();});
	}
	
	public void setCode(String code){
		this.code = code;
	}
	
	public String getCode(){
		return code;
	}
	
	public String getText(){
		return inputTextField.getText();
	}
	
	public void setOnOk(Runnable r){
		okRunnable = r;
	}
	
	public void setOnCancel(Runnable r){
		cancelRunnable = r;
	}
}
