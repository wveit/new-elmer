package platformer.level;

import platformer.engine.shape.Rectangle;

public class ScreenWorldCoordinateConverter {
	private Rectangle screen;
	private Rectangle worldViewport;
	
	public ScreenWorldCoordinateConverter(Rectangle screen, Rectangle worldViewport){
		this.screen = screen;
		this.worldViewport = worldViewport;
	}
	
	public void setScreen(Rectangle screen){
		this.screen = screen;
	}
	
	public void setWorldViewport(Rectangle worldViewport){
		this.worldViewport = worldViewport;
	}
	
	public Rectangle getScreen(){
		return screen;
	}
	
	public Rectangle getWorldViewport(){
		return worldViewport;
	}
	
	public Rectangle screenToWorld(Rectangle screenRect){
		return new Rectangle(
			screenRect.minX(),
			0,
			screenRect.width() * worldViewport.width() / screen.width(),
			screenRect.height() * worldViewport.height() / screen.height()
		);
	}
	
	public Rectangle worldToScreen(Rectangle worldRect){
		return new Rectangle(
			0,
			0,
			0,
			0
		);
	}
}
