package platformer.world;

import platformer.engine.shape.Rectangle;

public class Goal {
	private Rectangle rect;
	
	public Goal(double x, double y, double width, double height){
		rect = new Rectangle(x, y, width, height);
	}
	
	public Rectangle rect(){ return rect; }
}
