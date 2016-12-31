package platformer.screen;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import platformer.engine.shape.Rectangle;
import platformer.engine.sound.SoundPlayer;
import platformer.graphics.Renderer;
import platformer.graphics.ScreenWorldRectConverter;
import platformer.utility.IntListener;
import platformer.utility.WorldFileSystem;
import platformer.world.World;
import platformer.engine.screen.MyScreen;

public class PlayScreen extends MyScreen{

	private World world;
	private Renderer renderer;
	private SoundPlayer backgroundMusic = new SoundPlayer();
	private ScreenWorldRectConverter converter;
	
	private boolean scrollVertical = true;
	private boolean scrollHorizontal = true;
	
	private String currentLevel = "assets/platformer/volcano_level.lvl";
	private IntListener endOfGameListener = null;
	
	public PlayScreen(int width, int height){
		super(width, height);
		
		world = new World();
		converter = new ScreenWorldRectConverter(new Rectangle(0, 0, width, height), new Rectangle(0, 0, width, height));
		renderer = new Renderer(this.getGraphicsContext2D(), converter);
		backgroundMusic.load("assets/platformer/airwolf.wav");
		backgroundMusic.setRepeat(true);
	}
	
	@Override
	public void start(){
		super.start();
		world.player.setIsDead(false);
		backgroundMusic.play();
	}
	
	@Override
	public void stop(){
		super.stop();
		backgroundMusic.pause();
	}
	
	public void setOnEndOfGame(IntListener listener){
		endOfGameListener = listener;
	}
	
	public void load(String filename){
		currentLevel = filename;
		world.clear();
		WorldFileSystem.loadWorldFromFile(world, filename);
	}
	
	public void reloadLevel(){
		world.clear();
		WorldFileSystem.loadWorldFromFile(world, currentLevel);
	}
	
	public void setWorld(World world){
		this.world = world;
	}
	
	

	@Override
	public void tick(double deltaTime){
		world.update(deltaTime);
		
		// Draw
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.clearRect(0, 0, getWidth(), getHeight());
		if(scrollVertical)
			converter.getWorldViewport().setY(world.player.rect().centerY() - converter.getWorldViewport().height() / 4);
		if(scrollHorizontal)
			converter.getWorldViewport().setX(world.player.rect().centerX() - converter.getWorldViewport().width() / 2);
		renderer.render(world);

		
	}
	
	///////////////////////////////////////////////////
	//
	//				Input Methods
	//
	///////////////////////////////////////////////////	
	
	@Override
	public void keyPressed(KeyEvent e){
		
		if(e.getCode() == KeyCode.UP){
			world.player.requestJump();
		}
		else if(e.getCode() == KeyCode.RIGHT){
			world.player.requestRight(true);
		}
		else if(e.getCode() == KeyCode.LEFT){
			world.player.requestLeft(true);
		}
		else if(e.getCode() == KeyCode.ESCAPE){
			if(endOfGameListener != null)
				endOfGameListener.listen(0);
		}
		else if(e.getCode() == KeyCode.R){
			if(world.player.isDead()){
				world.clear();
				WorldFileSystem.loadWorldFromFile(world, currentLevel);
			}
		}
		
	}
	
	@Override
	public void keyReleased(KeyEvent e){
		
		if(e.getCode() == KeyCode.RIGHT){
			world.player.requestRight(false);
		}
		else if(e.getCode() == KeyCode.LEFT){
			world.player.requestLeft(false);
		}
	}
	
}

