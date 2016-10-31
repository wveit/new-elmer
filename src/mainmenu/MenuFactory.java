package mainmenu;

public class MenuFactory {
	MainMenuScreen screen;
	public MenuFactory(MainMenuScreen screen){
		this.screen = screen;
	}
	
	public Menu openingMenu(){
		Menu menu = new Menu();
		menu.setText("Welcome To Elmer The Confused Ninja!");
		menu.addChoice("Continue");
		menu.setAction(0, ()->{screen.goToMenu(gameSelectMenu());});
		return menu;
	}
	
	public Menu gameSelectMenu(){
		Menu menu = new Menu();
		menu.setText("What do you want to do?");
		menu.addChoice("Play Story Mode");
		menu.setAction(0, ()->{screen.act("Story Mode");});
		menu.addChoice("Play a mini-game");
		menu.setAction(1, ()->{screen.goToMenu(miniGameSelectMenu());});
		menu.addChoice("See Credits");
		menu.setAction(2, ()->{screen.goToMenu(creditsMenu());});
		menu.addChoice("Exit");
		menu.setAction(3, ()->{screen.act("Exit");});
		menu.addChoice("Go Back");
		menu.setAction(4, ()->{screen.goBack();});
		return menu;
	}
	
	public Menu miniGameSelectMenu(){
		Menu menu = new Menu();
		menu.setText("Which Mini-game do you want to play?");
		menu.addChoice("Platformer");
		menu.setAction(0, ()->{screen.act("Platformer");});
		menu.addChoice("Puzzle");
		menu.setAction(1, ()->{screen.act("Puzzle");});
		menu.addChoice("Top Down");
		menu.setAction(2, ()->{screen.act("Top Down");});
		menu.addChoice("Go Back");
		menu.setAction(3, ()->{screen.goBack();});
		return menu;
	}
	
	public Menu creditsMenu(){
		Menu menu = new Menu();
		menu.setText("Game by: Ken, Joseph, Danny and Will");
		menu.addChoice("Go Back");
		menu.setAction(0, ()->{screen.goBack();});
		return menu;
	}
	
}
