package mainmenu;

import java.util.ArrayList;

public class Menu {
	private String text;
	private ArrayList<String> choiceList = new ArrayList<>();
	private int currentChoice = 0;
	private ArrayList<Runnable> actionList = new ArrayList<>();
	
	public Menu(){
		
	}
	
	
	public String text(){
		return text;
	}
	
	public void nextChoice(){
		currentChoice++;
		if(currentChoice >= numChoices())
			currentChoice = 0;
	}
	
	public void previousChoice(){
		currentChoice--;
		if(currentChoice < 0)
			currentChoice = numChoices() - 1;
	}
	
	public int currentChoice(){
		return currentChoice;
	}
	
	public int numChoices(){
		return choiceList.size();
	}
	
	public String getChoice(int index){
		return choiceList.get(index);
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public void addChoice(String choice){
		choiceList.add(choice);
	}
	
	public void setAction(int index, Runnable action){
		if(index >= choiceList.size())
			return;
		
		while(actionList.size() < index + 1)
			actionList.add(null);
		
		actionList.set(index, action);
	}
	
	public Runnable getAction(int index){
		return actionList.get(index);
	}
}
