package application;

import javafx.scene.layout.*;

public interface OpMode {
	
	public void setControl(Control control);
	public Pane getPane();
	
	public void load(String filename);
	public void start();
	public void pause();
	public void resume();
	public void end();	

}
