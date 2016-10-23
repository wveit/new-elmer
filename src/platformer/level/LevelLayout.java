package platformer.level;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LevelLayout extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		LevelScreen ls = new LevelScreen(1200, 800);
		Pane pane = new Pane(ls);
		primaryStage.setScene(new Scene(pane));
		primaryStage.setTitle("level layout");
		primaryStage.show();
		ls.requestFocus();
	}
	
	public static void main(String[] args){
		Application.launch(args);
	}

}
