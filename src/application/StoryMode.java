package application;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class StoryMode implements OpMode{

	Control control = null;
	Pane pane = new VBox();
	
	public StoryMode(){
		Label label = new Label("--- Story Mode ---");
		
		Button miniGameButton = new Button("Start Mini Game");
		Button endStoryModeButton = new Button("End Story Mode");
		
		pane.getChildren().addAll(label, miniGameButton, endStoryModeButton);
	}
	
	@Override
	public void setControl(Control control) {
		this.control = control;
	}

	@Override
	public Pane getPane() {
		return pane;
	}

	@Override
	public void start() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void end() {
		
	}

}
