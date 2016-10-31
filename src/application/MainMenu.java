package application;

import javafx.scene.layout.Pane;
import mainmenu.MainMenuScreen;

public class MainMenu implements OpMode{
	
	Pane pane = new Pane();
	MainMenuScreen screen;
	Control control = null;
	
	public MainMenu(){
		screen = new MainMenuScreen(1200, 800);
		screen.setMainMenu(this);
		pane.getChildren().add(screen);
	}
	
	@Override
	public void setControl(Control control) {
		this.control = control;
	}
	
	public Control getControl(){
		return control;
	}

	@Override
	public Pane getPane() {
		return pane;
	}

	@Override
	public void start() {
		screen.start();
		screen.requestFocus();
	}

	@Override
	public void pause() {
		screen.stop();
	}

	@Override
	public void resume() {
		screen.start();
		screen.requestFocus();
	}

	@Override
	public void end() {
		screen.stop();
	}

	@Override
	public void load(String filename) {
		
	}
}
