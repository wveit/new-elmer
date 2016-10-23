package platformer.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import platformer.engine.shape.Rectangle;
import platformer.engine.sprite.SpriteAnimator;

import java.io.File;

import javafx.scene.image.Image;

public class Renderer2 {
	private GraphicsContext gc;
	private Rectangle viewport;
	
	public Renderer2(GraphicsContext gc, double width, double height){
		this.gc = gc;
		viewport = new Rectangle(0, 0, width, height);
	}
	
	
	public void render(World world){
		// update viewport based on player position
		double diff = viewport.centerY() - world.player.rect().centerY();
		viewport.move(0, -diff);
		if(viewport.minY() < 0)
			viewport.setY(0);
		
		render(world, viewport);
	}
	
	public void render(World world, Rectangle viewport){
		this.viewport = viewport;
		
		gc.setFill(Color.BLACK);
		for(Platform p : world.platformList){
			Rectangle r = transformRect(p.rect());
			gc.fillRect(r.minX(), r.minY(), r.width(), r.height());
		}
		
		gc.setFill(Color.RED);
		for(Enemy e : world.enemyList){
			Rectangle r = transformRect(e.rect());
			gc.fillRect(r.minX(), r.minY(), r.width(), r.height());
		}
		
		gc.setFill(Color.BLUE);
		if(world.player != null){
			Rectangle r = transformRect(world.player.rect());
			gc.fillRect(r.minX(), r.minY(), r.width(), r.height());
		}
	}
	
		
	
	private Rectangle transformRect(Rectangle r){
		return new Rectangle(r.minX() - viewport.minX(), viewport.maxY() - r.maxY(), r.width(), r.height());
	}

}
