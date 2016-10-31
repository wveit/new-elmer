package mainmenu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainMenuApp extends Application{
	private MainMenuScreen screen = new MainMenuScreen(1200, 800);
	
	@Override
	public void start(Stage primaryStage){

		Pane pane = new Pane(screen);
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Main Menu App");
		primaryStage.show();
		screen.start();
		screen.requestFocus();
	}
	
	public static void main(String[] args){
		Application.launch(args);
	}
}
