package application;

import java.util.Stack;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Control extends Application{
	
	private Scene scene;
	private Stack<OpMode> opModeStack = new Stack<>();
	private OpMode currentOpMode;
	private int windowWidth = 1200;
	private int windowHeight = 800;
	private int lastFinishCode = -1;
	
	@Override
	public void start(Stage primaryStage){
		currentOpMode = new MainMenu();
		currentOpMode.setControl(this);
		
		scene = new Scene(currentOpMode.getPane(), windowWidth, windowHeight);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Elmer the Confused Ninja");
		primaryStage.show();
		currentOpMode.getPane().requestFocus();
		currentOpMode.start();
	}
	
	public void startMainMenu(){
		startOpMode(new MainMenu());
	}
	
	public void startStoryMode(){
		startOpMode(new StoryMode());
	}
	
	public void startPlatformer(String levelName){
		startOpMode(new DummyMiniGame());
	}
	
	public void startPuzzle(String levelName){
		startOpMode(new DummyMiniGame());
	}
	
	public void startTopDown(String levelName){
		startOpMode(new DummyMiniGame());
	}
	
	private void startOpMode(OpMode opMode){
		currentOpMode.pause();
		opModeStack.push(currentOpMode);
		currentOpMode = opMode;
		currentOpMode.setControl(this);
		scene.setRoot(currentOpMode.getPane());
		currentOpMode.getPane().requestFocus();
		currentOpMode.start();
	}
	
	public void notifyOfOpModeCompletion(OpMode notifyingOpMode, int finishCode){
		if(notifyingOpMode != currentOpMode){
			// There's a problem because only the current OpMode should be calling this function
			System.out.println("Control problem: Op Mode other than current is notifying of completion!");
			Platform.exit();
		}
		
		currentOpMode.end();
		if(opModeStack.isEmpty()){
			// Main Menu should be the bottom of the stack. If it completes, then the whole application should end.
			Platform.exit();
		}
		
		currentOpMode = opModeStack.pop();
		scene.setRoot(currentOpMode.getPane());
		currentOpMode.getPane().requestFocus();
		currentOpMode.resume();
		lastFinishCode = finishCode;
		
		System.out.println("Recieved finishCode: " + finishCode);
	}
	
	public int getLastFinishCode(){
		return lastFinishCode;
	}
	
	public static void main(String[] args){
		Application.launch(args);
	}
}
