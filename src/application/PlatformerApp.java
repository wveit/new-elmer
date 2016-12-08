package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import platformer.game.GameScreen;

public class PlatformerApp extends Application{
	GameScreen gameScreen = new GameScreen(1200, 800);
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(new Pane(gameScreen));
		primaryStage.setScene(scene);
		primaryStage.setTitle("Platformer");
		primaryStage.show();

		gameScreen.load("assets/platformer/blah.lvl");
		
		gameScreen.setOnEndOfGame((int i)->{primaryStage.close();});
		gameScreen.requestFocus();
		gameScreen.start();
	}
	
	public static void main(String[] args){
		Application.launch(args);
	}

}
