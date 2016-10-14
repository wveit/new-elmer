package application;

import javafx.scene.layout.Pane;
import platformer.game.GameScreen;

public class Platformer implements OpMode{

	Control control = null;
	GameScreen gameScreen = new GameScreen(1200, 800);
	Pane pane = new Pane(gameScreen);
	
	public Platformer(){
		gameScreen.setOnEndOfGame( (int i)->{
			if(control != null)
				control.notifyOfOpModeCompletion(this, i);
		});
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
		gameScreen.start();
		gameScreen.requestFocus();
	}

	@Override
	public void pause() {
		gameScreen.stop();
		
	}

	@Override
	public void resume() {
		gameScreen.start();
		gameScreen.requestFocus();
	}

	@Override
	public void end() {
		gameScreen.stop();
		gameScreen.setOnEndOfGame(null);
		// gameScreen.delete(); write this to prevent memory leaks
	}

	@Override
	public void load(String filename) {
		gameScreen.load(filename);
	}
	

}
