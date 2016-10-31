package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import jc.Logic;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Puzzle implements OpMode{

	Control control = null;
	Pane pane = new Pane();
	
	private GraphicsContext gc;
	
	//  image positions
	
	private double screenHeight = 800;
	private double screenWidth = 1200;

	private double boardHeight = screenHeight * 0.8;
	private double boardWidth = screenWidth * 0.8;
	
	private	double heightSpacing = screenHeight * 0.1;
	private	double widthSpacing = screenWidth * 0.1;
	
	private double imageSpacingHeight = boardHeight/10;
	private double imageSpacingWidth = boardWidth/10;
	
	
	//  create logic object
	private Logic logic = new Logic();
	
	//   game is not finished yet
	private boolean playable = true;
	
	//   player starts first
	private boolean playerMove = true;
	
	//  choose the level of difficulty
	//  default level is easy
	private boolean easy = false;
	private boolean medium = false;
	private boolean hard = true;
	
	//   check if game is being played in story mode
	private boolean story = false;
	
	private Button button1 = new Button("Select");
	private Button button2 = new Button("Select");
	private Button button3 = new Button("Select");
	private Button button4 = new Button("Select");
	private Button button5 = new Button("Select");
	private Button button6 = new Button("Select");
	private Button button7 = new Button("Select");
	private Button button8 = new Button("Select");
	private Button button9 = new Button("Select");
	
	private Button quitButton = new Button("Quit Game");
	private Button playAgainButton = new Button("Play Again");
	private Button moveOn = new Button("Play Again");
	
	private Image ninja;
	private Image wizard;
	private Image backgroundImage;
	
	private MediaPlayer mediaPlayer;

	public Puzzle() {
		
		try{
			ninja = new Image(new File("assets/puzzle/ninja.png").toURI().toURL().toString());
			wizard = new Image(new File("assets/puzzle/wizard.jpeg").toURI().toURL().toString());
			backgroundImage = new Image(new File("assets/platformer/volcano_background.png").toURI().toURL().toString());
		}catch(Exception e){
			System.out.println("Problem images.");
		}
		
		
		Canvas canvas = new Canvas(1200,800);
		pane.getChildren().add(canvas);
		

		
	/*
	 * 
	 * 	create tic-tac-toe board below
	 * 	
	 */
		
		gc = canvas.getGraphicsContext2D();
		
		gc.setStroke(Color.RED);
		
		gc.strokeRect(widthSpacing, heightSpacing, boardWidth, boardHeight);
		
		//   vertical lines
		gc.strokeLine(widthSpacing + boardWidth/3 , heightSpacing, widthSpacing + boardWidth/3, heightSpacing + boardHeight);
		gc.strokeLine(widthSpacing + 2*boardWidth/3, heightSpacing, widthSpacing + 2*boardWidth/3, heightSpacing + boardHeight);
		
		//   horizontal Lines
		gc.strokeLine(widthSpacing, heightSpacing + boardHeight/3, widthSpacing + boardWidth, heightSpacing + boardHeight/3);
		gc.strokeLine(widthSpacing, heightSpacing + 2*boardHeight/3, widthSpacing + boardWidth, heightSpacing + 2*boardHeight/3);

	
		logic.clearGameboard();
		
		//   top row
		

		button1.setLayoutX(widthSpacing + 10);
		button1.setLayoutY(heightSpacing + 10);

		button1.setOnAction(e -> {

			if(playable == true && playerMove == true){
				gc.drawImage(ninja, widthSpacing + imageSpacingWidth, heightSpacing + imageSpacingHeight, 100.0, 100.0);
				playerMove = false;
				logic.playerMove(0, 0);
				button1.setDisable(true);
				checkWin();
				
				playButtonSound();
				
				computerMove();
			}

		}
				);
		
		button2.setLayoutX(widthSpacing + boardWidth/3 + 10);
		button2.setLayoutY(heightSpacing + 10);
		
		button2.setOnAction(e -> {
			
			if(playable == true && playerMove == true){
				gc.drawImage(ninja, widthSpacing + boardWidth/3 + imageSpacingWidth, heightSpacing + imageSpacingHeight, 100.0, 100.0);
				playerMove = false;
				logic.playerMove(0, 1);
				button2.setDisable(true);
				checkWin();
				
				playButtonSound();
				
				computerMove();
			}

		}
				);
		
		button3.setLayoutX(widthSpacing + 2*boardWidth/3 + 10);
		button3.setLayoutY(heightSpacing + 10);
		
		button3.setOnAction(e -> {
			
			if(playable == true && playerMove == true){
				gc.drawImage(ninja, widthSpacing + 2*boardWidth/3 + imageSpacingWidth, heightSpacing + imageSpacingHeight, 100.0, 100.0);
				playerMove = false;
				logic.playerMove(0, 2);
				button3.setDisable(true);
				checkWin();
				
				playButtonSound();
				
				computerMove();

			}
			
		}
				);
		
		//   middle row
		button4.setLayoutX(widthSpacing + 10);
		button4.setLayoutY(heightSpacing + boardHeight/3 + 10);
		
		button4.setOnAction(e -> {
			
			if(playable == true && playerMove == true){
				gc.drawImage(ninja, widthSpacing +imageSpacingWidth, heightSpacing + boardHeight/3 + imageSpacingHeight, 100.0, 100.0);
				playerMove = false;
				logic.playerMove(1, 0);
				button4.setDisable(true);
				checkWin();
				
				playButtonSound();

				computerMove();	
			}
	
		}
				);
		
		button5.setLayoutX(widthSpacing + boardWidth/3 + 10);
		button5.setLayoutY(heightSpacing + boardHeight/3 + 10);
		
		button5.setOnAction(e -> {
			
			if(playable == true && playerMove == true){
				
				gc.drawImage(ninja, widthSpacing + boardWidth/3 + imageSpacingWidth, heightSpacing + boardHeight/3 + imageSpacingHeight, 100.0, 100.0);
				playerMove = false;
				logic.playerMove(1, 1);
				button5.setDisable(true);
				checkWin();

				playButtonSound();

				computerMove();
			}
	
		}
				);
		
		button6.setLayoutX(widthSpacing + 2*boardWidth/3 + 10);
		button6.setLayoutY(heightSpacing + boardHeight/3 + 10);
		
		button6.setOnAction(e -> {
			
			if(playable == true && playerMove == true){
				
				gc.drawImage(ninja, widthSpacing + 2*boardWidth/3 + imageSpacingWidth, heightSpacing + boardHeight/3 + imageSpacingHeight, 100.0, 100.0);
				playerMove = false;
				logic.playerMove(1, 2);
				button6.setDisable(true);
				checkWin();
				
				playButtonSound();

				computerMove();
			}
	
		}
				);
		
		//   bottom row
		button7.setLayoutX(widthSpacing + 10);
		button7.setLayoutY(heightSpacing + 2*boardHeight/3 + 10);
		
		button7.setOnAction(e -> {
			
			if(playable == true && playerMove == true){
				gc.drawImage(ninja, widthSpacing +imageSpacingWidth, heightSpacing + 2*boardHeight/3 + imageSpacingHeight, 100.0, 100.0);
				playerMove = false;
				logic.playerMove(2, 0);
				button7.setDisable(true);
				checkWin();
				
				playButtonSound();

				computerMove();	
			}
	
		}
				);
		
		button8.setLayoutX(widthSpacing + boardWidth/3 + 10);
		button8.setLayoutY(heightSpacing + 2*boardHeight/3 + 10);
			
		button8.setOnAction(e -> {
			
			if(playable == true && playerMove == true){
				
				gc.drawImage(ninja, widthSpacing + boardWidth/3 + imageSpacingWidth, heightSpacing + 2*boardHeight/3 + imageSpacingHeight, 100.0, 100.0);
				playerMove = false;
				logic.playerMove(2, 1);
				button8.setDisable(true);
				checkWin();
				
				playButtonSound();

				computerMove();
			}
	
		}
				);
		
		button9.setLayoutX(widthSpacing + 2*boardWidth/3 + 10);
		button9.setLayoutY(heightSpacing + 2*boardHeight/3 + 10);
		
		button9.setOnAction(e -> {
			
			if(playable == true && playerMove == true){
				
				gc.drawImage(ninja, widthSpacing + 2*boardWidth/3 + imageSpacingWidth, heightSpacing + 2*boardHeight/3 + imageSpacingHeight, 100.0, 100.0);
				playerMove = false;
				logic.playerMove(2, 2);
				button9.setDisable(true);
				checkWin();
				
				playButtonSound();

				computerMove();
			}
	
		}
				);
		
		quitButton.setLayoutX(widthSpacing + boardWidth/3);
		quitButton.setLayoutY(heightSpacing/3);
		
		quitButton.setOnAction(e -> {
			control.notifyOfOpModeCompletion(this, 0);
		}
				);
		
		pane.getChildren().addAll(button1, button2, button3, button4, button5, button6, button7, button8, button9, quitButton);


	}
	
	private void clickButtonOne(){
		
		gc.drawImage(wizard, widthSpacing + imageSpacingWidth, heightSpacing + imageSpacingHeight, 100.0, 100.0);
		playerMove = true;
		logic.computerMove(0, 0);
		button1.setDisable(true);
		checkWin();
		
	}
	
	private void clickButtonTwo(){
		
		gc.drawImage(wizard, widthSpacing + boardWidth/3 + imageSpacingWidth, heightSpacing + imageSpacingHeight, 100.0, 100.0);
		playerMove = true;
		logic.computerMove(0, 1);
		button2.setDisable(true);
		checkWin();
	}
	
	private void clickButtonThree(){
		
		gc.drawImage(wizard, widthSpacing + 2*boardWidth/3 + imageSpacingWidth, heightSpacing + imageSpacingHeight, 100.0, 100.0);
		playerMove = true;
		logic.computerMove(0, 2);
		button3.setDisable(true);
		checkWin();		
	}
	
	private void clickButtonFour(){
		
		gc.drawImage(wizard, widthSpacing + imageSpacingWidth, heightSpacing + boardHeight/3 + imageSpacingHeight, 100.0, 100.0);
		playerMove = true;
		logic.computerMove(1, 0);
		button4.setDisable(true);
		checkWin();		
	}
	
	private void clickButtonFive(){
		
		gc.drawImage(wizard, widthSpacing + boardWidth/3 + imageSpacingWidth, heightSpacing + boardHeight/3 + imageSpacingHeight, 100.0, 100.0);
		playerMove = true;
		logic.computerMove(1, 1);
		button5.setDisable(true);
		checkWin();		
	}
	
	private void clickButtonSix(){
		
		gc.drawImage(wizard, widthSpacing + 2*boardWidth/3 + imageSpacingWidth, heightSpacing  + boardHeight/3 + imageSpacingHeight, 100.0, 100.0);
		playerMove = true;
		logic.computerMove(1, 2);
		button6.setDisable(true);
		checkWin();
	}
	
	private void clickButtonSeven(){
		
		gc.drawImage(wizard, widthSpacing + imageSpacingWidth, heightSpacing + 2*boardHeight/3 + imageSpacingHeight, 100.0, 100.0);
		playerMove = true;
		logic.computerMove(2, 0);
		button7.setDisable(true);
		checkWin();
	}
	
	private void clickButtonEight(){
		
		gc.drawImage(wizard, widthSpacing + boardWidth/3 + imageSpacingWidth, heightSpacing + 2*boardHeight/3 + imageSpacingHeight, 100.0, 100.0);
		playerMove = true;
		logic.computerMove(2, 1);
		button8.setDisable(true);
		checkWin();
		
	}
	
	private void clickButtonNine(){
		
		gc.drawImage(wizard, widthSpacing + 2*boardWidth/3 + imageSpacingWidth, heightSpacing  + 2*boardHeight/3 + imageSpacingHeight, 100.0, 100.0);
		playerMove = true;
		logic.computerMove(2, 2);
		button9.setDisable(true);
		checkWin();	
		
	}
	
private void computerMove(){
		
		if(easy == true){
			if(playable == true && button1.isDisabled() == false){
				clickButtonOne();
				return;
			}
			if(playable == true && button2.isDisabled() == false){
				clickButtonTwo();
				return;
			}
			if(playable == true && button3.isDisabled() == false){
				clickButtonThree();
				return;
			}
			if(playable == true && button4.isDisabled() == false){
				clickButtonFour();
				return;
			}
			if(playable == true && button5.isDisabled() == false){
				clickButtonFive();
				return;
			}
			if(playable == true && button6.isDisabled() == false){
				clickButtonSix();
				return;
			}
			if(playable == true && button7.isDisabled() == false){
				clickButtonSeven();
				return;
			}
			if(playable == true && button8.isDisabled() == false){
				clickButtonEight();
				return;
			}
			if(playable == true && button9.isDisabled() == false){
				clickButtonNine();
				return;
			}
		}
		
		if(medium == true){
			
			if((playable == true && logic.gameboard[0][1] == 'X' && logic.gameboard[0][2] == 'X') 
					|| (playable == true && logic.gameboard[1][0] == 'X' && logic.gameboard[2][0] == 'X')
					|| (playable == true && logic.gameboard[1][1] == 'X' && logic.gameboard[2][2] == 'X')){
				
				if(button1.isDisabled() == false){
					clickButtonOne();
					return;
				}
			}
			
			if((playable == true && logic.gameboard[0][0] == 'X' && logic.gameboard[0][2] == 'X') 
					|| (playable == true && logic.gameboard[1][1] == 'X' && logic.gameboard[2][1] == 'X')){
				
				if(button2.isDisabled() == false){
					clickButtonTwo();
					return;
				}
			}
			
			if((playable == true && logic.gameboard[0][0] == 'X' && logic.gameboard[0][1] == 'X') 
					|| (playable == true && logic.gameboard[1][2] == 'X' && logic.gameboard[2][2] == 'X')
					|| (playable == true && logic.gameboard[2][0] == 'X' && logic.gameboard[1][1] == 'X')){
				
				if(button3.isDisabled() == false){
					clickButtonThree();
					return;
				}
			}
			
			if((playable == true && logic.gameboard[0][0] == 'X' && logic.gameboard[2][0] == 'X') 
					|| (playable == true && logic.gameboard[1][1] == 'X' && logic.gameboard[1][2] == 'X')){
				
				if(button4.isDisabled() == false){
					clickButtonFour();
					return;
				}
			}
			
			if((playable == true && logic.gameboard[0][0] == 'X' && logic.gameboard[2][2] == 'X') 
					|| (playable == true && logic.gameboard[2][0] == 'X' && logic.gameboard[0][2] == 'X')
					|| (playable == true && logic.gameboard[0][1] == 'X' && logic.gameboard[2][1] == 'X')
					|| (playable == true && logic.gameboard[1][0] == 'X' && logic.gameboard[1][2] == 'X')){
				
				if(button5.isDisabled() == false){
					clickButtonFive();
					return;
				}
			}
			
			if((playable == true && logic.gameboard[1][0] == 'X' && logic.gameboard[1][1] == 'X') 
					|| (playable == true && logic.gameboard[0][2] == 'X' && logic.gameboard[2][2] == 'X')){
				
				if(button6.isDisabled() == false){
					clickButtonSix();
					return;
				}
			}
			
			if((playable == true && logic.gameboard[2][1] == 'X' && logic.gameboard[2][2] == 'X') 
					|| (playable == true && logic.gameboard[0][0] == 'X' && logic.gameboard[1][0] == 'X')
					|| (playable == true && logic.gameboard[1][1] == 'X' && logic.gameboard[0][2] == 'X')){
				
				if(button7.isDisabled() == false){
					clickButtonSeven();
					return;
				}
			}
			
			if((playable == true && logic.gameboard[2][0] == 'X' && logic.gameboard[2][2] == 'X') 
					|| (playable == true && logic.gameboard[0][1] == 'X' && logic.gameboard[1][1] == 'X')){
				
				if(button8.isDisabled() == false){
					clickButtonEight();
					return;
				}
			}
			
			if((playable == true && logic.gameboard[2][0] == 'X' && logic.gameboard[2][1] == 'X') 
					|| (playable == true && logic.gameboard[0][2] == 'X' && logic.gameboard[1][2] == 'X')
					|| (playable == true && logic.gameboard[2][0] == 'X' && logic.gameboard[2][1] == 'X')){
				
				if(button9.isDisabled() == false){
					clickButtonNine();
					return;
				}
			}
			

			if(playable == true && button1.isDisabled() == false){
				clickButtonOne();
				return;
			}
			if(playable == true && button2.isDisabled() == false){
				clickButtonTwo();
				return;
			}
			if(playable == true && button3.isDisabled() == false){
				clickButtonThree();
				return;
			}
			if(playable == true && button4.isDisabled() == false){
				clickButtonFour();
				return;
			}
			if(playable == true && button5.isDisabled() == false){
				clickButtonFive();
				return;
			}
			if(playable == true && button6.isDisabled() == false){
				clickButtonSix();
				return;
			}
			if(playable == true && button7.isDisabled() == false){
				clickButtonSeven();
				return;
			}
			if(playable == true && button8.isDisabled() == false){
				clickButtonEight();
				return;
			}
			if(playable == true && button9.isDisabled() == false){
				clickButtonNine();
				return;
			}
			

		}	
		
		if(hard == true){
			
			if((playable == true && logic.gameboard[0][1] == 'X' && logic.gameboard[0][2] == 'X') 
					|| (playable == true && logic.gameboard[1][0] == 'X' && logic.gameboard[2][0] == 'X')
					|| (playable == true && logic.gameboard[1][1] == 'X' && logic.gameboard[2][2] == 'X')){
				
				if(button1.isDisabled() == false){
					clickButtonOne();
					return;
				}
			}
			
			if((playable == true && logic.gameboard[0][0] == 'X' && logic.gameboard[0][2] == 'X') 
					|| (playable == true && logic.gameboard[1][1] == 'X' && logic.gameboard[2][1] == 'X')){
				
				if(button2.isDisabled() == false){
					clickButtonTwo();
					return;
				}
			}
			
			if((playable == true && logic.gameboard[0][0] == 'X' && logic.gameboard[0][1] == 'X') 
					|| (playable == true && logic.gameboard[1][2] == 'X' && logic.gameboard[2][2] == 'X')
					|| (playable == true && logic.gameboard[2][0] == 'X' && logic.gameboard[1][1] == 'X')){
				
				if(button3.isDisabled() == false){
					clickButtonThree();
					return;
				}
			}
			
			if((playable == true && logic.gameboard[0][0] == 'X' && logic.gameboard[2][0] == 'X') 
					|| (playable == true && logic.gameboard[1][1] == 'X' && logic.gameboard[1][2] == 'X')){
				
				if(button4.isDisabled() == false){
					clickButtonFour();
					return;
				}
			}
			
			if((playable == true && logic.gameboard[0][0] == 'X' && logic.gameboard[2][2] == 'X') 
					|| (playable == true && logic.gameboard[2][0] == 'X' && logic.gameboard[0][2] == 'X')
					|| (playable == true && logic.gameboard[0][1] == 'X' && logic.gameboard[2][1] == 'X')
					|| (playable == true && logic.gameboard[1][0] == 'X' && logic.gameboard[1][2] == 'X')){
				
				if(button5.isDisabled() == false){
					clickButtonFive();
					return;
				}
			}
			
			if((playable == true && logic.gameboard[1][0] == 'X' && logic.gameboard[1][1] == 'X') 
					|| (playable == true && logic.gameboard[0][2] == 'X' && logic.gameboard[2][2] == 'X')){
				
				if(button6.isDisabled() == false){
					clickButtonSix();
					return;
				}
			}
			
			if((playable == true && logic.gameboard[2][1] == 'X' && logic.gameboard[2][2] == 'X') 
					|| (playable == true && logic.gameboard[0][0] == 'X' && logic.gameboard[1][0] == 'X')
					|| (playable == true && logic.gameboard[1][1] == 'X' && logic.gameboard[0][2] == 'X')){
				
				if(button7.isDisabled() == false){
					clickButtonSeven();
					return;
				}
			}
			
			if((playable == true && logic.gameboard[2][0] == 'X' && logic.gameboard[2][2] == 'X') 
					|| (playable == true && logic.gameboard[0][1] == 'X' && logic.gameboard[1][1] == 'X')){
				
				if(button8.isDisabled() == false){
					clickButtonEight();
					return;
				}
			}
			
			if((playable == true && logic.gameboard[2][0] == 'X' && logic.gameboard[2][1] == 'X') 
					|| (playable == true && logic.gameboard[0][2] == 'X' && logic.gameboard[1][2] == 'X')
					|| (playable == true && logic.gameboard[2][0] == 'X' && logic.gameboard[2][1] == 'X')){
				
				if(button9.isDisabled() == false){
					clickButtonNine();
					return;
				}
			}
			
			if(playable == true && button5.isDisabled() == false){
				clickButtonFive();
				return;
			}
			if(playable == true && button1.isDisabled() == false){
				clickButtonOne();
				return;
			}
			if(playable == true && button2.isDisabled() == false){
				clickButtonTwo();
				return;
			}
			if(playable == true && button3.isDisabled() == false){
				clickButtonThree();
				return;
			}
			if(playable == true && button4.isDisabled() == false){
				clickButtonFour();
				return;
			}
			if(playable == true && button6.isDisabled() == false){
				clickButtonSix();
				return;
			}
			if(playable == true && button7.isDisabled() == false){
				clickButtonSeven();
				return;
			}
			if(playable == true && button8.isDisabled() == false){
				clickButtonEight();
				return;
			}
			if(playable == true && button9.isDisabled() == false){
				clickButtonNine();
				return;
			}
			

		}
		
	}
	
	
	private void checkWin(){
		
		/*
		 *   Player Wins
		 */
		
		if(logic.gameboard[0][0] == 'X' && logic.gameboard[0][1] == 'X' && logic.gameboard[0][2] == 'X'				//   horizontal wins
				|| logic.gameboard[1][0] == 'X' && logic.gameboard[1][1] == 'X' && logic.gameboard[1][2] == 'X'		
				|| logic.gameboard[2][0] == 'X' && logic.gameboard[2][1] == 'X' && logic.gameboard[2][2] == 'X'
				
				|| logic.gameboard[0][0] == 'X' && logic.gameboard[1][0] == 'X' && logic.gameboard[2][0] == 'X'		//   vertical wins
				|| logic.gameboard[0][1] == 'X' && logic.gameboard[1][1] == 'X' && logic.gameboard[2][1] == 'X'
				|| logic.gameboard[0][2] == 'X' && logic.gameboard[1][2] == 'X' && logic.gameboard[2][2] == 'X'
				
				|| logic.gameboard[0][0] == 'X' && logic.gameboard[1][1] == 'X' && logic.gameboard[2][2] == 'X'		//   diagonal wins
				|| logic.gameboard[2][0] == 'X' && logic.gameboard[1][1] == 'X' && logic.gameboard[0][2] == 'X'){
			
			winOptions();
		}
		
		
		/*
		 *   Computer Wins
		 */
		

		if(logic.gameboard[0][0] == 'O' && logic.gameboard[0][1] == 'O' && logic.gameboard[0][2] == 'O'				//   horizontal wins
				|| logic.gameboard[1][0] == 'O' && logic.gameboard[1][1] == 'O' && logic.gameboard[1][2] == 'O'		
				|| logic.gameboard[2][0] == 'O' && logic.gameboard[2][1] == 'O' && logic.gameboard[2][2] == 'O'
				
				|| logic.gameboard[0][0] == 'O' && logic.gameboard[1][0] == 'O' && logic.gameboard[2][0] == 'O'		//   vertical wins
				|| logic.gameboard[0][1] == 'O' && logic.gameboard[1][1] == 'O' && logic.gameboard[2][1] == 'O'
				|| logic.gameboard[0][2] == 'O' && logic.gameboard[1][2] == 'O' && logic.gameboard[2][2] == 'O'
				
				|| logic.gameboard[0][0] == 'O' && logic.gameboard[1][1] == 'O' && logic.gameboard[2][2] == 'O'		//   diagonal wins
				|| logic.gameboard[2][0] == 'O' && logic.gameboard[1][1] == 'O' && logic.gameboard[0][2] == 'O'){
			
			lossOptions();
		}
		
		
		/*
		 *   Draw 
		 */
		
		if(playable == true 
				&& button1.isDisabled() == true
				&& button2.isDisabled() == true
				&& button3.isDisabled() == true
				&& button4.isDisabled() == true
				&& button5.isDisabled() == true
				&& button6.isDisabled() == true
				&& button7.isDisabled() == true
				&& button8.isDisabled() == true
				&& button9.isDisabled() == true){
			
			drawOptions();
		}
		
	}

	
	private void winOptions(){
		
		if(story == true){
			pane.getChildren().removeAll(quitButton);
			gc.fillText("You Win!\n\nPress \"Continue\" to move on to the next part of the story.", widthSpacing, heightSpacing/3, boardWidth);
			playable = false;
			
			Button moveOn = new Button("Continue");
			
			moveOn.setLayoutX(widthSpacing + 2*boardWidth/3);
			moveOn.setLayoutY(heightSpacing/3);
			
			moveOn.setOnAction(e -> {
				control.notifyOfOpModeCompletion(this, 1);
			}
					);
			
			pane.getChildren().addAll(moveOn);
		}else{
			pane.getChildren().removeAll(quitButton);
			gc.fillText("You Win!\n\nPress \"Quit Game\" to return to the main menu.\nPress \"Play Again\" to play another round against the wizard.", widthSpacing, heightSpacing/3, boardWidth);
			playable = false;
			
			Button quitButton = new Button("Quit Game");
			Button playAgainButton = new Button("Play Again");
			
			quitButton.setLayoutX(widthSpacing + boardWidth/3);
			quitButton.setLayoutY(heightSpacing/3);
			
			quitButton.setOnAction(e -> {
				control.notifyOfOpModeCompletion(this, 1);
			}
					);
			
			playAgainButton.setLayoutX(widthSpacing + 2*boardWidth/3);
			playAgainButton.setLayoutY(heightSpacing/3);
			
			playAgainButton.setOnAction(e -> {
				reset();
				
			}
					);
			
			pane.getChildren().addAll(quitButton, playAgainButton);
		}
		
	}
	
	private void lossOptions(){
		
		if(story == true){
			pane.getChildren().removeAll(quitButton);
			gc.fillText("You Lose!\n\nPress \"Continue\" to move on to the next part of the story", widthSpacing, heightSpacing/3, boardWidth);
			playable = false;
			
			Button moveOn = new Button("Continue");
			
			moveOn.setLayoutX(widthSpacing + 2*boardWidth/3);
			moveOn.setLayoutY(heightSpacing/3);
			
			moveOn.setOnAction(e -> {
				control.notifyOfOpModeCompletion(this, 0);
			}
					);
			
			pane.getChildren().addAll(moveOn);
		}else{
			pane.getChildren().removeAll(quitButton);
			gc.fillText("You Lose!\n\nPress \"Quit Game\" to return to the main menu.\nPress \"Play Again\" to play another round against the wizard.", widthSpacing, heightSpacing/3, boardWidth);
			playable = false;
			
			Button quitButton = new Button("Quit Game");
			Button playAgainButton = new Button("Play Again");
			
			quitButton.setLayoutX(widthSpacing + boardWidth/3);
			quitButton.setLayoutY(heightSpacing/3);
			
			quitButton.setOnAction(e -> {
				control.notifyOfOpModeCompletion(this, 0);
			}
					);
			
			playAgainButton.setLayoutX(widthSpacing + 2*boardWidth/3);
			playAgainButton.setLayoutY(heightSpacing/3);
			
			playAgainButton.setOnAction(e -> {
				reset();			
				
			}
					);
			
			pane.getChildren().addAll(quitButton, playAgainButton);
		}

	}
	
	private void drawOptions(){
		
		if(story == true){
			pane.getChildren().removeAll(quitButton);
			gc.fillText("The game is a draw...\n\nPress \"Continue\" to move on to the next part of the story", widthSpacing, heightSpacing/3, boardWidth);
			playable = false;
			
			Button moveOn = new Button("Continue");
			
			moveOn.setLayoutX(widthSpacing + 2*boardWidth/3);
			moveOn.setLayoutY(heightSpacing/3);
			
			moveOn.setOnAction(e -> {
				control.notifyOfOpModeCompletion(this, 0);
			}
					);
			
			pane.getChildren().addAll(moveOn);
		}else{
		
			pane.getChildren().removeAll(quitButton);
			gc.fillText("The game is a draw...\n\nPress \"Quit Game\" to return to the main menu.\nPress \"Play Again\" to play another round against the wizard.", widthSpacing, heightSpacing/3, boardWidth);
			playable = false;

			Button quitButton = new Button("Quit Game");
			Button playAgainButton = new Button("Play Again");

			quitButton.setLayoutX(widthSpacing + boardWidth/3);
			quitButton.setLayoutY(heightSpacing/3);

			quitButton.setOnAction(e -> {
				control.notifyOfOpModeCompletion(this, 0);
			}
					);

			playAgainButton.setLayoutX(widthSpacing + 2*boardWidth/3);
			playAgainButton.setLayoutY(heightSpacing/3);

			playAgainButton.setOnAction(e -> {
				reset();
			}
					);

			pane.getChildren().addAll(quitButton, playAgainButton);
		}
	}

	
	private void playButtonSound(){
		try
		{
			String File = "assets/puzzle/punch.wav";
			InputStream in = new FileInputStream(File);	    
			AudioStream audioStream = new AudioStream(in);
			AudioPlayer.player.start(audioStream);
		}
		catch (Exception e)
		{
			System.out.println("Exception while loading audio.");
		}
	}
	
	
	
	
	
	
	private void reset(){
		
		playable = true;
		playerMove = true;
		
		try{
			ninja = new Image(new File("assets/puzzle/ninja.png").toURI().toURL().toString());
			wizard = new Image(new File("assets/puzzle/wizard.jpeg").toURI().toURL().toString());
			backgroundImage = new Image(new File("assets/platformer/volcano_background.png").toURI().toURL().toString());
		}catch(Exception e){
			System.out.println("Problem images.");
		}
		
		gc.clearRect(0, 0, screenWidth, screenHeight);
		logic.clearGameboard();
		
		gc.strokeRect(widthSpacing, heightSpacing, boardWidth, boardHeight);
		
		//   vertical lines
		gc.strokeLine(widthSpacing + boardWidth/3 , heightSpacing, widthSpacing + boardWidth/3, heightSpacing + boardHeight);
		gc.strokeLine(widthSpacing + 2*boardWidth/3, heightSpacing, widthSpacing + 2*boardWidth/3, heightSpacing + boardHeight);
		
		//   horizontal Lines
		gc.strokeLine(widthSpacing, heightSpacing + boardHeight/3, widthSpacing + boardWidth, heightSpacing + boardHeight/3);
		gc.strokeLine(widthSpacing, heightSpacing + 2*boardHeight/3, widthSpacing + boardWidth, heightSpacing + 2*boardHeight/3);
	
		button1.setDisable(false);
		button2.setDisable(false);
		button3.setDisable(false);
		button4.setDisable(false);
		button5.setDisable(false);
		button6.setDisable(false);
		button7.setDisable(false);
		button8.setDisable(false);
		button9.setDisable(false);
		
		button1.setLayoutX(widthSpacing + 10);
		button1.setLayoutY(heightSpacing + 10);

		button1.setOnAction(e -> {

			if(playable == true && playerMove == true){
				gc.drawImage(ninja, widthSpacing + imageSpacingWidth, heightSpacing + imageSpacingHeight, 100.0, 100.0);
				playerMove = false;
				logic.playerMove(0, 0);
				button1.setDisable(true);
				checkWin();
				
				playButtonSound();
				
				computerMove();
			}

		}
				);
		
		button2.setLayoutX(widthSpacing + boardWidth/3 + 10);
		button2.setLayoutY(heightSpacing + 10);
		
		button2.setOnAction(e -> {
			
			if(playable == true && playerMove == true){
				gc.drawImage(ninja, widthSpacing + boardWidth/3 + imageSpacingWidth, heightSpacing + imageSpacingHeight, 100.0, 100.0);
				playerMove = false;
				logic.playerMove(0, 1);
				button2.setDisable(true);
				checkWin();
				
				playButtonSound();
				
				computerMove();
			}

		}
				);
		
		button3.setLayoutX(widthSpacing + 2*boardWidth/3 + 10);
		button3.setLayoutY(heightSpacing + 10);
		
		button3.setOnAction(e -> {
			
			if(playable == true && playerMove == true){
				gc.drawImage(ninja, widthSpacing + 2*boardWidth/3 + imageSpacingWidth, heightSpacing + imageSpacingHeight, 100.0, 100.0);
				playerMove = false;
				logic.playerMove(0, 2);
				button3.setDisable(true);
				checkWin();
				
				playButtonSound();
				
				computerMove();

			}
			
		}
				);
		
		//   middle row
		button4.setLayoutX(widthSpacing + 10);
		button4.setLayoutY(heightSpacing + boardHeight/3 + 10);
		
		button4.setOnAction(e -> {
			
			if(playable == true && playerMove == true){
				gc.drawImage(ninja, widthSpacing +imageSpacingWidth, heightSpacing + boardHeight/3 + imageSpacingHeight, 100.0, 100.0);
				playerMove = false;
				logic.playerMove(1, 0);
				button4.setDisable(true);
				checkWin();
				
				playButtonSound();

				computerMove();	
			}
	
		}
				);
		
		button5.setLayoutX(widthSpacing + boardWidth/3 + 10);
		button5.setLayoutY(heightSpacing + boardHeight/3 + 10);
		
		button5.setOnAction(e -> {
			
			if(playable == true && playerMove == true){
				
				gc.drawImage(ninja, widthSpacing + boardWidth/3 + imageSpacingWidth, heightSpacing + boardHeight/3 + imageSpacingHeight, 100.0, 100.0);
				playerMove = false;
				logic.playerMove(1, 1);
				button5.setDisable(true);
				checkWin();

				playButtonSound();

				computerMove();
			}
	
		}
				);
		
		button6.setLayoutX(widthSpacing + 2*boardWidth/3 + 10);
		button6.setLayoutY(heightSpacing + boardHeight/3 + 10);
		
		button6.setOnAction(e -> {
			
			if(playable == true && playerMove == true){
				
				gc.drawImage(ninja, widthSpacing + 2*boardWidth/3 + imageSpacingWidth, heightSpacing + boardHeight/3 + imageSpacingHeight, 100.0, 100.0);
				playerMove = false;
				logic.playerMove(1, 2);
				button6.setDisable(true);
				checkWin();
				
				playButtonSound();

				computerMove();
			}
	
		}
				);
		
		//   bottom row
		button7.setLayoutX(widthSpacing + 10);
		button7.setLayoutY(heightSpacing + 2*boardHeight/3 + 10);
		
		button7.setOnAction(e -> {
			
			if(playable == true && playerMove == true){
				gc.drawImage(ninja, widthSpacing +imageSpacingWidth, heightSpacing + 2*boardHeight/3 + imageSpacingHeight, 100.0, 100.0);
				playerMove = false;
				logic.playerMove(2, 0);
				button7.setDisable(true);
				checkWin();
				
				playButtonSound();

				computerMove();	
			}
	
		}
				);
		
		button8.setLayoutX(widthSpacing + boardWidth/3 + 10);
		button8.setLayoutY(heightSpacing + 2*boardHeight/3 + 10);
			
		button8.setOnAction(e -> {
			
			if(playable == true && playerMove == true){
				
				gc.drawImage(ninja, widthSpacing + boardWidth/3 + imageSpacingWidth, heightSpacing + 2*boardHeight/3 + imageSpacingHeight, 100.0, 100.0);
				playerMove = false;
				logic.playerMove(2, 1);
				button8.setDisable(true);
				checkWin();
				
				playButtonSound();

				computerMove();
			}
	
		}
				);
		
		button9.setLayoutX(widthSpacing + 2*boardWidth/3 + 10);
		button9.setLayoutY(heightSpacing + 2*boardHeight/3 + 10);
		
		button9.setOnAction(e -> {
			
			if(playable == true && playerMove == true){
				
				gc.drawImage(ninja, widthSpacing + 2*boardWidth/3 + imageSpacingWidth, heightSpacing + 2*boardHeight/3 + imageSpacingHeight, 100.0, 100.0);
				playerMove = false;
				logic.playerMove(2, 2);
				button9.setDisable(true);
				checkWin();
				
				playButtonSound();

				computerMove();
			}
	
		}
				);
		
		quitButton.setLayoutX(widthSpacing);
		quitButton.setLayoutY(heightSpacing/3);
		
		quitButton.setOnAction(e -> {
			control.notifyOfOpModeCompletion(this, 0);
		}
				);
		
		pane.getChildren().addAll(button1, button2, button3, button4, button5, button6, button7, button8, button9, quitButton);
	
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
		
		if(filename.equalsIgnoreCase("story")){
			story = true;
		}
		
	}
	

}