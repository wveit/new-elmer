package application;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MainMenu implements OpMode{

	Pane pane = new VBox();
	Control control = null;
	
	public MainMenu(){
		Label label = new Label("--- Main Menu ---");
		Button storyModeButton = new Button("StoryMode");
		storyModeButton.setOnAction(e -> {
			control.startStoryMode("story_mode");
		});
		
		Button dummyMiniGameButton = new Button("DummyMiniGame");	
		dummyMiniGameButton.setOnAction(e -> {
			control.startDummyMiniGame("dummy_string");
		});
		
		Button platformerButton = new Button("Platformer");	
		platformerButton.setOnAction(e -> {
			control.startPlatformer("volcano_level");
		});
		
		Button topDownButton = new Button("Top Down Game");	
		topDownButton.setOnAction(e -> {
			control.startTopDown("top_down_level");
		});
		
		Button puzzleButton = new Button("Puzzle Game");	
		puzzleButton.setOnAction(e -> {
			control.startPuzzle("puzzle_level");
		});
		
		Button exitButton = new Button("Exit");
		exitButton.setOnAction(e -> {
			control.notifyOfOpModeCompletion(this, 1);
		});
		
		pane.getChildren().addAll(label, storyModeButton, dummyMiniGameButton, platformerButton, topDownButton, puzzleButton, exitButton);
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

	@Override
	public void load(String filename) {
		
	}
}
