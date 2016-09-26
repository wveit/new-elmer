package application;

import java.util.Stack;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Control extends Application{
	
	private Scene scene;
	private Stack<OpMode> opModeStack = new Stack<>();
	private int windowWidth = 1200;
	private int windowHeight = 800;
	private int lastFinishCode = -1;
	
	@Override
	public void start(Stage primaryStage){
		OpMode menu = new MainMenu();
		menu.setControl(this);
		opModeStack.push(menu);
		
		scene = new Scene(menu.getPane(), windowWidth, windowHeight);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Elmer the Confused Ninja");
		primaryStage.show();
		menu.getPane().requestFocus();
		menu.start();
	}
	
	public void startMainMenu(String filename){
		startOpMode(new MainMenu(), filename);
	}
	
	public void startStoryMode(String filename){
		startOpMode(new StoryMode(), filename);
	}
	
	public void startPlatformer(String filename){
		startOpMode(new Platformer(), filename);
	}
	
	public void startPuzzle(String filename){
		startOpMode(new Puzzle(), filename);
	}
	
	public void startTopDown(String filename){
		startOpMode(new TopDown(), filename);
	}
	
	public void startDummyMiniGame(String filename){
		startOpMode(new DummyMiniGame(), filename);
	}
	
	private void startOpMode(OpMode opMode, String filename){
		opModeStack.peek().pause();
		opModeStack.push(opMode);
		opMode.setControl(this);
		opMode.load(filename);
		scene.setRoot(opMode.getPane());
		opMode.getPane().requestFocus();
		opMode.start();
	}
	
	public void notifyOfOpModeCompletion(OpMode notifyingOpMode, int finishCode){
		if(notifyingOpMode != opModeStack.peek()){
			// Only the currentOpMode (the one at the top of the stack) should be calling this
			// method. If a different OpMode is calling this method, there is a problem!
			// In this case, display error message and terminate program
			System.out.println("Control problem: Op Mode other than current is notifying of completion!");
			Platform.exit();
			return;
		}
		
		opModeStack.peek().end();
		opModeStack.pop();
		System.out.println("Recieved finishCode: " + finishCode);
		
		if(opModeStack.isEmpty()){
			// Main Menu should be the bottom of the stack. If it completes, then the whole application should end.
			Platform.exit();
			return;
		}
		
		scene.setRoot(opModeStack.peek().getPane());
		opModeStack.peek().getPane().requestFocus();
		opModeStack.peek().resume();
		lastFinishCode = finishCode;
	}
	
	public int getLastFinishCode(){
		return lastFinishCode;
	}
	
	public static void main(String[] args){
		Application.launch(args);
	}
}
