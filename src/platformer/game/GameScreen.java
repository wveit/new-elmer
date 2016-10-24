package platformer.game;

import java.io.File;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.*;
import platformer.engine.shape.Rectangle;
import javafx.util.Duration;
import platformer.engine.screen.MyScreen;

public class GameScreen extends MyScreen{
	
	IntListener endOfGameListener = null;
	
	private World world;
	private Renderer renderer;
	private MediaPlayer mediaPlayer;
	ScreenWorldRectConverter converter;
	
	private long lastNanoseconds = 0;
	private int logicFPS = 60;
	private double logicAccumulator = 0;
	
	public GameScreen(int width, int height){
		super(width, height);
		
		world = new World();
		converter = new ScreenWorldRectConverter(new Rectangle(0, 0, width, height), new Rectangle(0, 0, width, height));
		renderer = new Renderer(this.getGraphicsContext2D(), converter);
		
		setUpGameMusic();
	}
	
	@Override
	public void start(){
		super.start();
		if(mediaPlayer != null)
			mediaPlayer.play();
	}
	
	@Override
	public void stop(){
		super.stop();
		if(mediaPlayer != null)
			mediaPlayer.pause();
		
	}
	
	public void setOnEndOfGame(IntListener listener){
		endOfGameListener = listener;
	}
	
	public void load(String filename){
		world.load(filename);
	}
	
	public void setUpGameMusic(){
		File file = new File("assets/platformer/airwolf.wav");
		if(file.exists()){			
			Media media = null;
			try{
				media = new Media("file:" + file.getAbsolutePath());
			}catch(Exception e){
				System.out.println("Exception while loading audio.");
				e.printStackTrace();
			}
			
			mediaPlayer = new MediaPlayer(media);
			mediaPlayer.setOnEndOfMedia(new Runnable(){
				@Override
				public void run(){
					mediaPlayer.seek(Duration.ZERO);
				}
			});
		}
		else{
			mediaPlayer = null;
			System.out.println("could not find audio file.");
		}
	}

	@Override
	public void tick(long nanoseconds){
		// Determine deltaTime
		if(lastNanoseconds == 0)
			lastNanoseconds = nanoseconds;
		
		double deltaTime = (nanoseconds - lastNanoseconds) / 1000000000.0;
		lastNanoseconds = nanoseconds;
		
		for(int i = 0; i < 1000; i++){
			Math.sqrt(i);
		}
		
		if(world.player.isDead()){
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
			converter.getWorldViewport().setY(Math.max(0, world.player.rect().centerY() - converter.getWorldViewport().height() / 2));
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
				world.load("volcano_level.lvl");
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

