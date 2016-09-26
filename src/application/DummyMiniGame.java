package application;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class DummyMiniGame implements OpMode{
	
	private Control control;
	private Pane pane = new VBox();
	
	public DummyMiniGame(){
		Label label = new Label("--- Playing a MiniGame ---");
		Button successButton = new Button("simulate success of minigame");
		successButton.setOnAction(e -> {
			System.out.println("Minigame simulating success");
			control.notifyOfOpModeCompletion(this, 1);
		});
		Button failureButton = new Button("simulate failure of minigame");
		failureButton.setOnAction(e -> {
			System.out.println("Minigame simulating failure");
			control.notifyOfOpModeCompletion(this, 0);
		});
		
		pane.getChildren().addAll(label, successButton, failureButton);
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
		if(control == null){
			System.out.println("DummyMiniGame.start() called without setting control!");
			Platform.exit();
		}
		
		// ...
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

	@Override
	public void load(String filename) {
		
	}

}
