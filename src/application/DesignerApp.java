package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import platformer.screen.LevelLayoutPane;

public class DesignerApp extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		LevelLayoutPane pane = new LevelLayoutPane();
		primaryStage.setScene(new Scene(pane));
		primaryStage.setTitle("Level Designer");
		primaryStage.show();
		pane.requestFocus();
	}
	
	public static void main(String[] args){
		Application.launch(args);
	}

}
