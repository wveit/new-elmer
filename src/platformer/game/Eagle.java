package platformer.game;

import platformer.engine.shape.Rectangle;

public class Eagle {
	private Rectangle rect;
	private double vX = -200;
	private double vY = -100;
	private boolean hasPlayer = false;
	
	public Eagle(){
		rect = new Rectangle();
	}
	
	public Rectangle rect(){
		return rect;
	}
	
	public boolean hasPlayer(){
		return hasPlayer;
	}
	
	public void setRect(double x, double y, double width, double height){
		rect.reset(x, y, width, height);
	}
	
	public void setupToGetPlayer(Player player){
		setRect(player.rect().minX() + 600, player.rect().minY() + 300, 100, 100);
	}
	
	public void update(double deltaTime, World world){
		if(!hasPlayer && rect.overlaps(world.player.rect())){
			hasPlayer = true;
			vY *= -1;
		}
		rect.move(vX * deltaTime, vY * deltaTime);
	}
}
