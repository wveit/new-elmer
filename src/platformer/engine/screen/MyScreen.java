package platformer.engine.screen;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class MyScreen extends Canvas{

	private Timeline timeline;
	private int fps = 60;
	
	public MyScreen(int width, int height){
		super(width, height);
		
		setTimeline();
		
		setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
            public void handle(KeyEvent e){
               keyPressed(e);               
            }
        });
		
		setOnKeyReleased(new EventHandler<KeyEvent>(){
			@Override
            public void handle(KeyEvent e){
               keyReleased(e);               
            }
        });
		
		this.setOnMousePressed(e->{
			mousePressed(e);
		});
		
		this.setOnMouseReleased(e->{
			mouseReleased(e);
		});
		
		this.setOnMouseMoved(e->{
			mouseMoved(e);
		});
		
		this.setOnMouseDragged(e->{
			mouseDragged(e);
		});

	}
	
	private void setTimeline(){
		double frameMillis = 1000.0 / fps;
		double deltaTime = frameMillis / 1000.0;
		Duration d = Duration.millis(frameMillis);
		timeline = new Timeline(new KeyFrame(d, e->{tick(deltaTime);}));
		timeline.setCycleCount(Animation.INDEFINITE);
	}
	
	public void setFPS(int fps){
		this.fps = fps;
		setTimeline();
	}
	
	public void start(){
		timeline.play();
	}
	
	public void stop(){
		timeline.stop();
	}
	
	public void tick(double deltaTime){
		
	}
	
	public void keyPressed(KeyEvent e){
		
	}
	
	public void keyReleased(KeyEvent e){
		
	}
	
	public void mousePressed(MouseEvent e){
		
	}
	
	public void mouseReleased(MouseEvent e){
		
	}
	
	public void mouseMoved(MouseEvent e){
		
	}
	
	public void mouseDragged(MouseEvent e){
		
	}
}
