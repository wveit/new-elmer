package mainmenu;

import java.io.File;
import java.util.Stack;

import application.MainMenu;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import platformer.engine.screen.MyScreen;
import platformer.engine.sound.SoundPlayer;

public class MainMenuScreen extends MyScreen {

	private MainMenu mainMenu = null;
	private Stack<Menu> stack = new Stack<>();
	private Menu currentMenu;
	private Image titleImage;
	private MenuFactory factory;
	private SoundPlayer soundPlayer = new SoundPlayer();

	public MainMenuScreen(int width, int height) {
		super(width, height);
		soundPlayer.load("assets/mainmenu/mozart.wav");
		soundPlayer.setRepeat(true);
		factory = new MenuFactory(this);
		titleImage = new Image("file:" + new File("assets/mainmenu/title.png").getAbsolutePath());
		goToMenu(factory.openingMenu());
	}
	
	public void setMainMenu(MainMenu mainMenu){
		this.mainMenu = mainMenu;
	}
	
	public void act(String request){
		if(mainMenu == null)
			return;
		
		if(request.equals("Story Mode"))
			mainMenu.getControl().startStoryMode("");
		else if(request.equals("Platformer"))
			mainMenu.getControl().startPlatformer("");
		if(request.equals("Puzzle"))
			mainMenu.getControl().startPuzzle("");
		if(request.equals("Top Down"))
			mainMenu.getControl().startTopDown("");
		if(request.equals("Exit"))
			mainMenu.getControl().notifyOfOpModeCompletion(mainMenu, 1);
	}
	
	public void goToMenu(Menu menu){
		stack.push(menu);
		currentMenu = menu;
	}
	
	public void goBack(){
		stack.pop();
		if(stack.isEmpty()){
			currentMenu = null;
			// TODO: end the main menu screen and make notifications
			return;
		}
		currentMenu = stack.peek();
	}
	
	@Override
	public void tick(long nanoseconds){
		// process input
		
		// update
		
		// render
		draw();
	}
	
	private void draw(){		
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, 1200, 800);
		
		gc.drawImage(titleImage, 100, 100, 1000, 300);
		
		gc.fillText(currentMenu.text(), 200, 50);
		
		double x = 200;
		double y = 500;
		gc.setFont(new Font(32));
		gc.setFill(Color.WHITE);
		gc.fillText(currentMenu.text(), x, y);
		y += 75;
		for(int i = 0; i < currentMenu.numChoices(); i++){
			if(i == currentMenu.currentChoice())
				gc.fillRect(175, y - 10, 10, 10);
			gc.fillText(currentMenu.getChoice(i), x, y);
			y += 50;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e){
		if(e.getCode() == KeyCode.UP){
			currentMenu.previousChoice();
		}
		else if(e.getCode() == KeyCode.DOWN){
			currentMenu.nextChoice();
		}
		else if(e.getCode() == KeyCode.ENTER){
			Runnable action = currentMenu.getAction(currentMenu.currentChoice());
			if(action != null)
				action.run();
		}
	}
	
	@Override
	public void start(){
		super.start();
		soundPlayer.play();
	}
	
	@Override
	public void stop(){
		super.stop();
		soundPlayer.pause();
	}

}
