package platformer.screen;


import java.util.Scanner;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import platformer.engine.screen.MyScreen;
import platformer.engine.shape.Rectangle;
import platformer.graphics.Renderer;
import platformer.graphics.ScreenWorldRectConverter;
import platformer.utility.WorldFileSystem;
import platformer.world.*;

public class DesignerScreen extends MyScreen{

	private World world = new World();
	private int entityCode = 0; // 0-player, 1-platform, 2-lavaMonster, 3-Spikey, 4-Vulcor, 5-Goal
	private boolean leftMouseDown = false;
	private boolean deleteMode = false;
	private Rectangle screenDragRect = new Rectangle();
	private ScreenWorldRectConverter rectConverter = null;
	private Renderer renderer = null;
	private String currentLevelFile = "assets/platformer/volcano_level.lvl";
	
	public void setDeleteMode(boolean flag){deleteMode = flag;}
	
	public DesignerScreen(int width, int height) {
		super(width, height);
		rectConverter = new ScreenWorldRectConverter(new Rectangle(0, 0, width, height), new Rectangle(0, 0, width, height));
		renderer = new Renderer(this.getGraphicsContext2D(), rectConverter);
		this.start();
	}
	
	private void inputEntity(int code, Rectangle screenRect){
		Rectangle r = rectConverter.screenRectToWorldRect(rectConverter.properRect(screenRect));
	
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
		else if(entityCode == 5)
			world.goal = new Goal(r.minX(), r.minY(), r.width(), r.height());
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
		
		renderer.render(world);
		
		gc.setStroke(Color.BLACK);
		if(leftMouseDown){
			Rectangle temp = rectConverter.properRect(screenDragRect);
			gc.strokeRect(temp.minX(), temp.minY(), temp.width(), temp.height());
		}
	
	}
	
	public void setEntityCode(int code){
		entityCode = code;
	}
	
	public World getWorld(){
		return world;
	}
	
	public void setWorld(World world){
		this.world = world;
	}
	
	///////////////////////////////////////////////////
	//
	//				Input Methods
	//
	///////////////////////////////////////////////////
	
	@Override
	public void keyPressed(KeyEvent e){
		// adjust which type of entity will be created when mouse is dragged
		if(e.getCode() == KeyCode.DIGIT0){
			entityCode = 0;
			System.out.println("Add Player");
		}
		else if(e.getCode() == KeyCode.DIGIT1){
			entityCode = 1;
			System.out.println("Add Platform");
		}
		else if(e.getCode() == KeyCode.DIGIT2){
			entityCode = 2;
			System.out.println("Add LavaMonster");
		}
		else if(e.getCode() == KeyCode.DIGIT3){
			entityCode = 3;
			System.out.println("Add Spikey");
		}
		else if(e.getCode() == KeyCode.DIGIT4){
			entityCode = 4;
			System.out.println("Add Vulcor");
		}
		else if(e.getCode() == KeyCode.DIGIT5){
			entityCode = 5;
			System.out.println("Add Goal");
		}
		
		// change which part of the game world is displayed in window
		else if(e.getCode() == KeyCode.LEFT)
			rectConverter.getWorldViewport().move(-10, 0);
		else if(e.getCode() == KeyCode.RIGHT)
			rectConverter.getWorldViewport().move(10, 0);
		else if(e.getCode() == KeyCode.UP)
			rectConverter.getWorldViewport().move(0, 10);
		else if(e.getCode() == KeyCode.DOWN)
			rectConverter.getWorldViewport().move(0, -10);
		
		// save & load
		else if(e.getCode() == KeyCode.S)
			WorldFileSystem.saveWorld(world, currentLevelFile);
		else if(e.getCode() == KeyCode.L)
			WorldFileSystem.loadWorldFromFile(world, currentLevelFile);
		else if(e.getCode() == KeyCode.A){
			System.out.print("Set current file: ");
			Scanner scanner = new Scanner(System.in);
			currentLevelFile = scanner.nextLine();
			scanner.close();
			System.out.println("Current file set to: " + currentLevelFile);
		}
		else if(e.getCode() == KeyCode.R)
			world.clear();
	}
	
	public void deleteObjectAt(double x, double y){
		Rectangle r = new Rectangle(x, y, 1, 1);
		r = rectConverter.screenRectToWorldRect(r);
		
		for(Platform p : world.platformList){
			if(r.inside(p.rect())){
				world.platformList.remove(p);
				return;
			}
		}
		
		for(Enemy e : world.enemyList){
			if(r.inside(e.rect())){
				world.enemyList.remove(e);
				return;
			}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e){
		if(e.getButton() == MouseButton.PRIMARY){
			if(deleteMode){
				deleteObjectAt(e.getX(), e.getY());
			}
			else{
				leftMouseDown = true;
				screenDragRect.reset(e.getX(), e.getY(), 0, 0);
			}
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e){
		if(e.getButton() == MouseButton.PRIMARY){
			if(!deleteMode){
				leftMouseDown = false;
				inputEntity(entityCode, screenDragRect);
			}
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e){
		screenDragRect.setWidth(e.getX() - screenDragRect.minX());
		screenDragRect.setHeight(e.getY() - screenDragRect.minY());
	}

}
