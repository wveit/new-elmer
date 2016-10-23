package platformer.level;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import platformer.engine.screen.MyScreen;
import platformer.engine.shape.Rectangle;
import platformer.game.*;

public class LevelScreen extends MyScreen{

	private World world = new World();
	private int entityCode = 0; // 0-player, 1-platform, 2-lavaMonster
	private boolean leftMouseDown = false;
	private Rectangle worldViewport = null;
	private Rectangle screenDragRect = new Rectangle();
//	private Renderer2 renderer = null;
//	private ArrayList<Rectangle> rList = new ArrayList<>();
	
	public LevelScreen(int width, int height) {
		super(width, height);
		worldViewport = new Rectangle(0, 0, width, height);
//		renderer = new Renderer2(this.getGraphicsContext2D(), width, height);
		world.leftBoundary = new Rectangle(); world.rightBoundary = new Rectangle(); world.gravity = -1000;
		this.start();
	}
	
	private void inputEntity(int code, Rectangle screenRect){
		Rectangle r = screenRectToWorldRect(properRect(screenRect));
		
//		System.out.println("Adding rect: " + r.minX() + "  " + r.minY() + "  " + r.width() + "  " + r.height());
//		rList.add(r);
		
		if(entityCode == 0)
			world.player = new Player(r.minX(), r.minY(), r.width(), r.height());
		else if(entityCode == 1)
			world.platformList.add(new Platform(r.minX(), r.minY(), r.width(), r.height()));
		else if(entityCode == 2)
			world.enemyList.add(new LavaMonster(r.minX(), r.minY(), r.width(), r.height()));
		else if(entityCode == 3)
			world.enemyList.add(new Spikey(r.minX(), r.minY(), r.width(), r.height()));
		else if(entityCode == 4)
			world.enemyList.add(new Vulcor(r.minX(), r.minY(), r.width(), r.height()));
	}
	
	@Override
	public void tick(long nanoSeconds){
		draw();
	}
	
	private void draw(){
		double width = this.getWidth();
		double height = this.getHeight();
		
		GraphicsContext gc = getGraphicsContext2D();
		gc.clearRect(0, 0, width, height);
		
		Rectangle r;
		
		if(world.player != null){
			gc.setFill(Color.BLUE);
			r = worldRectToScreenRect(world.player.rect());
			gc.fillRect(r.minX(), r.minY(), r.width(), r.height());
		}
		
		gc.setFill(Color.GREEN);
		for(Enemy e : world.enemyList){
			r = worldRectToScreenRect(e.rect());
			gc.fillRect(r.minX(), r.minY(), r.width(), r.height());
		}
		
		gc.setFill(Color.BROWN);
		for(Platform p : world.platformList){
			r = worldRectToScreenRect(p.rect());
			gc.fillRect(r.minX(), r.minY(), r.width(), r.height());			
		}
		
//		gc.setStroke(Color.RED);
//		for(Rectangle rect : rList){
//			Rectangle temp = worldRectToScreenRect(rect);
//			gc.strokeRect(temp.minX(), temp.minY(), temp.width(), temp.height());
//		}
		
		gc.setStroke(Color.BLACK);
		if(leftMouseDown){
			Rectangle temp = properRect(screenDragRect);
			gc.strokeRect(temp.minX(), temp.minY(), temp.width(), temp.height());
		}
				
	}
	
	
	///////////////////////////////////////////////////
	//
	//			Rectangle Conversion Methods
	//
	///////////////////////////////////////////////////
	
	public Rectangle screenRectToWorldRect(Rectangle r){
		return new Rectangle(
			r.minX() + worldViewport.minX(), 
			this.getHeight() - r.maxY() + worldViewport.minY(), 
			r.width(), 
			r.height()
		);
	}
	
	public Rectangle worldRectToScreenRect(Rectangle r){
		return new Rectangle(
			r.minX() - worldViewport.minX(), 
			this.getHeight() - r.maxY() + worldViewport.minY(), 
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
	
	///////////////////////////////////////////////////
	//
	//				Input Methods
	//
	///////////////////////////////////////////////////
	
	@Override
	public void keyPressed(KeyEvent e){
		// adjust which type of entity will be created when mouse is dragged
		if(e.getCode() == KeyCode.DIGIT0)
			entityCode = 0;
		else if(e.getCode() == KeyCode.DIGIT1)
			entityCode = 1;
		else if(e.getCode() == KeyCode.DIGIT2)
			entityCode = 2;
		else if(e.getCode() == KeyCode.DIGIT3)
				entityCode = 3;
		else if(e.getCode() == KeyCode.DIGIT4)
			entityCode = 4;
		
		// change which part of the game world is displayed in window
		else if(e.getCode() == KeyCode.LEFT)
			worldViewport.move(-10, 0);
		else if(e.getCode() == KeyCode.RIGHT)
			worldViewport.move(10, 0);
		else if(e.getCode() == KeyCode.UP)
			worldViewport.move(0, 10);
		else if(e.getCode() == KeyCode.DOWN)
			worldViewport.move(0, -10);
		
		// save [& load]
		else if(e.getCode() == KeyCode.S)
			world.save("assets/platformer/blah.lvl");
		else if(e.getCode() == KeyCode.L)
			world.load("assets/platformer/blah.lvl");
	}
	
	@Override
	public void mousePressed(MouseEvent e){
		if(e.getButton() == MouseButton.PRIMARY){
			leftMouseDown = true;
			screenDragRect.reset(e.getX(), e.getY(), 0, 0);
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e){
		if(e.getButton() == MouseButton.PRIMARY){
			leftMouseDown = false;
			inputEntity(entityCode, screenDragRect);
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e){
		screenDragRect.setWidth(e.getX() - screenDragRect.minX());
		screenDragRect.setHeight(e.getY() - screenDragRect.minY());
	}

}
