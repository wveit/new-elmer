package platformer.graphics;

import platformer.engine.shape.Rectangle;

public class ScreenWorldRectConverter {
	private Rectangle screen;
	private Rectangle worldViewport;
	
	public ScreenWorldRectConverter(Rectangle screen, Rectangle worldViewport){
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
	
	public Rectangle screenRectToWorldRect(Rectangle r){
		return new Rectangle(
			r.minX() + worldViewport.minX(), 
			screen.height() - r.maxY() + worldViewport.minY(), 
			r.width(), 
			r.height()
		);
	}
	
	public Rectangle worldRectToScreenRect(Rectangle r){
		return new Rectangle(
			r.minX() - worldViewport.minX(), 
			screen.height() - r.maxY() + worldViewport.minY(), 
			r.width(), 
			r.height()
		);
	}
	
	public Rectangle properRect(Rectangle inputRect){
		Rectangle temp = new Rectangle(inputRect);
		
		if(temp.width() < 0){
			temp.setX( temp.maxX() );
			temp.setWidth( temp.width() * -1 );
		}
		
		if(temp.height() < 0){
			temp.setY( temp.maxY() );
			temp.setHeight( temp.height() * -1 );
		}
		
		return temp;
	}
}
