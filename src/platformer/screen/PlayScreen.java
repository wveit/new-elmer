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
	
	IntListener endOfGameListener = null;
	
	private World world;
	private Renderer renderer;
	private SoundPlayer backgroundMusic = new SoundPlayer();
	ScreenWorldRectConverter converter;
	
	private long lastNanoseconds = 0;
	private int logicFPS = 60;
	private double logicAccumulator = 0;
	
	private boolean scrollVertical = true;
	private boolean scrollHorizontal = true;
	
	private String currentLevel = "assets/platformer/volcano_level.lvl";
	
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
		world.player.setIsDead(false);
		super.start();
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
	public void tick(long nanoseconds){
		// Determine deltaTime
		if(lastNanoseconds == 0)
			lastNanoseconds = nanoseconds;
		
		double deltaTime = (nanoseconds - lastNanoseconds) / 1000000000.0;
		lastNanoseconds = nanoseconds;
		

		
		
			
		logicAccumulator += deltaTime;
		while(logicAccumulator >= 1.0 / logicFPS){
			// Update game logic
			world.update(1.0 / logicFPS);
			
			logicAccumulator -= 1.0 / logicFPS;
		}

		// print some info
		if(world.player.onGoal())
			System.out.println("GameScreen.tick(...) -> Player is on goal");		
		if(world.player.isDead())
			System.out.println("GameScreen.tick(...) -> Player is dead");

		// Draw
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.clearRect(0, 0, getWidth(), getHeight());
		if(scrollVertical)
			converter.getWorldViewport().setY(Math.max(0, world.player.rect().centerY() - converter.getWorldViewport().height() / 2));
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

