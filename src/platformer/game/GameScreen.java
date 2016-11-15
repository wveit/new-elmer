package platformer.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import platformer.engine.shape.Rectangle;
import platformer.engine.sound.SoundPlayer;
import platformer.engine.screen.MyScreen;

public class GameScreen extends MyScreen{
	
	IntListener endOfGameListener = null;
	
	private boolean eagleAnimation = false;
	private Eagle eagle = new Eagle();
	private SoundPlayer eagleSound = new SoundPlayer();
	private double eagleTimer = 0;
	
	private World world;
	private Renderer renderer;
	private SoundPlayer backgroundMusic = new SoundPlayer();
	ScreenWorldRectConverter converter;
	
	private long lastNanoseconds = 0;
	private int logicFPS = 60;
	private double logicAccumulator = 0;
	
	private boolean scrollVertical = true;
	private boolean scrollHorizontal = false;
	
	private String currentLevel = "assets/platformer/volcano_level.lvl";
	
	public GameScreen(int width, int height){
		super(width, height);
		
		world = new World();
		converter = new ScreenWorldRectConverter(new Rectangle(0, 0, width, height), new Rectangle(0, 0, width, height));
		renderer = new Renderer(this.getGraphicsContext2D(), converter);
		backgroundMusic.load("assets/platformer/airwolf.wav");
		backgroundMusic.setRepeat(true);
		eagleSound.load("assets/platformer/eagle.wav");
	}
	
	@Override
	public void start(){
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
		WorldFileSystem.loadWorld(world, currentLevel);
	}
	
	
	
	public void doEagleAnimation(double deltaTime){
		double oldX = eagle.rect().minX();
		double oldY = eagle.rect().minY();
		
		eagle.update(deltaTime, world);
		
		double deltaX = eagle.rect().minX() - oldX;
		double deltaY = eagle.rect().minY() - oldY;
		
		if(eagle.hasPlayer()){
			world.player.rect().move(deltaX, deltaY);
		}
		
		renderer.render(world);
		renderer.render(eagle);
		
		eagleTimer += deltaTime;
		if(eagleTimer > 10 && endOfGameListener != null){
			endOfGameListener.listen(1);
		}
	}

	@Override
	public void tick(long nanoseconds){
		// Determine deltaTime
		if(lastNanoseconds == 0)
			lastNanoseconds = nanoseconds;
		
		double deltaTime = (nanoseconds - lastNanoseconds) / 1000000000.0;
		lastNanoseconds = nanoseconds;
		
		if(eagleAnimation){
			doEagleAnimation(deltaTime);
		}		
		else if(world.player.onGoal()){
			eagleAnimation = true;
			eagle.setupToGetPlayer(world.player);
			eagleSound.play();			
		}
		else if(world.player.isDead()){
			renderer.renderGameOver();
		}
		else{
			
			logicAccumulator += deltaTime;
			while(logicAccumulator >= 1.0 / logicFPS){
				// Update game logic
				world.update(1.0 / logicFPS);
				
				logicAccumulator -= 1.0 / logicFPS;
			}


			// Draw
			GraphicsContext gc = this.getGraphicsContext2D();
			gc.clearRect(0, 0, getWidth(), getHeight());
			if(scrollVertical)
				converter.getWorldViewport().setY(Math.max(0, world.player.rect().centerY() - converter.getWorldViewport().height() / 2));
			if(scrollHorizontal)
				converter.getWorldViewport().setX(world.player.rect().centerX() - converter.getWorldViewport().width() / 2);
			renderer.render(world);

		}
	}
	
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
				WorldFileSystem.loadWorld(world, currentLevel);
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

