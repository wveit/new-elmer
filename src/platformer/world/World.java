package platformer.world;

import java.util.ArrayList;

public class World {
	
	public double gravity = -1000;
	public Player player = null;
	public Goal goal = null;
	public ArrayList<Platform> platformList = new ArrayList<>();
	public ArrayList<Enemy> enemyList = new ArrayList<>();
	
	
	public World(){
		clear();
	}
	

	
	public void clear(){
		player = new Player(50, 50, 50, 50);
		goal = new Goal(50, 50, 50, 50);
		platformList.clear();
		enemyList.clear();
	}
	
	
	public void update(double deltaTime){
		player.update(deltaTime, this);
		
		// remove dead enemies from the enemy list and update live enemies
		for(int i = 0; i < enemyList.size(); i++){
			if(enemyList.get(i).isDead()){
				enemyList.remove(i);
				i--;
			}
			else{
				enemyList.get(i).update(deltaTime, this);
			}
		}
	}
	
}
