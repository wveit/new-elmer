package platformer.screen;


import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import platformer.engine.screen.MyScreen;
import platformer.utility.FocusFixer;
import platformer.utility.Input;
import platformer.utility.WorldFileSystem;
import platformer.world.World;

public class LevelLayoutPane extends VBox{

	MyScreen screen;
	DesignerScreen designerScreen = new DesignerScreen(1200, 800);
	PlayScreen playScreen = new PlayScreen(1200, 800);
	Label messageLabel = new Label();
	Input textInput = new Input();
	World world;
	String savePath = "assets/platformer/userLevels/";
	String currentSaveFile = "";
	
	public LevelLayoutPane(){
		MenuBar menuBar = makeMenuBar();
		screen = designerScreen;
		world = designerScreen.getWorld();
		textInput.setVisible(false);
		
		this.getChildren().addAll(menuBar, screen, messageLabel, textInput);
		
		this.setOnKeyPressed(e->{onKeyPress(e);});
		this.setOnKeyReleased(e->{onKeyRelease(e);});
		FocusFixer.setFocusElement(this);
	}
	
	private void onKeyPress(KeyEvent e){
		screen.keyPressed(e);
	}
	
	private void onKeyRelease(KeyEvent e){
		screen.keyReleased(e);
	}
	
	public void setEntityCode(int code){
		if (screen == designerScreen){
			designerScreen.setEntityCode(code);
			designerScreen.setDeleteMode(false);
		}
		else{
			System.out.println("LevelLayoutPane.setEntityCode() -> tried to set code while in play mode");
		}
	}
	
	public void print(String message){
		messageLabel.setText(message);
	}
	
	private void waitForInput(){
		textInput.setVisible(true);
		textInput.setOnOk(()->{resumeFromInput(textInput.getText());});
		textInput.setOnCancel(()->{cancelInput();});
		screen.stop();
	}
	
	private void resumeFromInput(String input){		
		if(textInput.getCode().equals("Load")){
			WorldFileSystem.loadWorldFromFile(world, savePath + input);
			print("Loaded from file: " + savePath + input);
		}
		else if(textInput.getCode().equals("Save As")){
			currentSaveFile = input;
			WorldFileSystem.saveWorld(world, savePath + input);
			print("Saved to file: " + savePath + input);
		}
		
		textInput.setVisible(false);		
		screen.start();
		FocusFixer.fixFocus();
	}
	
	private void cancelInput(){
		print("Received cancel");
		textInput.setVisible(false);		
		screen.start();
		FocusFixer.fixFocus();
	}
	
	public void switchToPlay(){
		if(screen == playScreen)
			return;
		
		screen = playScreen;
		int index = this.getChildren().indexOf(designerScreen);
		this.getChildren().set(index, playScreen);
		playScreen.setWorld(world);
		designerScreen.stop();
		playScreen.start();
	}
	
	public void switchToEdit(){
		if(screen == designerScreen)
			return;
		
		screen = designerScreen;
		int index = this.getChildren().indexOf(playScreen);
		this.getChildren().set(index, designerScreen);
		designerScreen.setWorld(world);
		playScreen.stop();
		designerScreen.start();
	}
	
	public void delete(){
		// prevent memory leaks by calling delete on objects this class owns and setting their references to null
	}
	
	private void save(){
		if(currentSaveFile.isEmpty())
			saveAs();
		else
			WorldFileSystem.saveWorld(world, savePath + currentSaveFile);
	}
	
	private void saveAs(){
		print("Save As - Enter File Name: ");
		textInput.setCode("Save As");
		waitForInput();
	}
	
	private void load(){
		print("Load - Enter File Name: ");
		textInput.setCode("Load");
		waitForInput();
	}
	
	private MenuBar makeMenuBar(){
		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("File");
		Menu editMenu = new Menu("Edit");
		menuBar.getMenus().addAll(fileMenu, editMenu);
		
		// Set up file menu
		MenuItem saveMenu = new MenuItem("Save");
		saveMenu.setOnAction(e->{save();});
		MenuItem saveAsMenu = new MenuItem("Save As");
		saveAsMenu.setOnAction(e->{saveAs();});
		MenuItem loadMenu = new MenuItem("Load");
		loadMenu.setOnAction(e->{load();});
		MenuItem editLevelMenu = new MenuItem("Edit Level");
		editLevelMenu.setOnAction(e->{switchToEdit();});
		MenuItem playLevelMenu = new MenuItem("Play Level");
		playLevelMenu.setOnAction(e->{switchToPlay();});
		MenuItem quitMenu = new MenuItem("Quit");
		quitMenu.setOnAction(e->{Platform.exit();});
		fileMenu.getItems().addAll(saveMenu, saveAsMenu, loadMenu, editLevelMenu, playLevelMenu, quitMenu);
		
		// Set up edit level menu
		Menu addMenu = new Menu("Add");
		MenuItem addPlayerMenu = new MenuItem("Add Player");
		addPlayerMenu.setOnAction(e->{setEntityCode(0);});
		MenuItem addGoalMenu = new MenuItem("Add Goal");
		addGoalMenu.setOnAction(e->{setEntityCode(5);});
		MenuItem addPlatformMenu = new MenuItem("Add Platform");
		addPlatformMenu.setOnAction(e->{setEntityCode(1);});
		MenuItem addLavaMonsterMenu = new MenuItem("Add LavaMonster");
		addLavaMonsterMenu.setOnAction(e->{setEntityCode(2);});
		MenuItem addSpikeyMenu = new MenuItem("Add Spikey");
		addSpikeyMenu.setOnAction(e->{setEntityCode(3);});
		MenuItem addVulcorMenu = new MenuItem("Add Vulcor");
		addVulcorMenu.setOnAction(e->{setEntityCode(4);});
		addMenu.getItems().addAll(addPlayerMenu, addGoalMenu, addPlatformMenu, addLavaMonsterMenu, addSpikeyMenu, addVulcorMenu);
		MenuItem removeMenu = new MenuItem("Remove");
		removeMenu.setOnAction(e->{designerScreen.setDeleteMode(true);});
		editMenu.getItems().addAll(addMenu, removeMenu);
		
		return menuBar;
	}

}
