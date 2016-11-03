package application;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class TopDown implements OpMode{

	Control control = null;
	Pane pane = new Pane();
	Image screenShot = new Image("file:" + new File("assets/topdown/bossStage.png").getAbsolutePath());
	ImageView imgView = new ImageView(screenShot);
	
	public TopDown(){
		imgView.setFitWidth(1200);
		imgView.setFitHeight(800);
		pane.getChildren().add(imgView);
		
		pane.setOnKeyPressed(e->{
			if(e.getCode() == KeyCode.ESCAPE && control != null)
				control.notifyOfOpModeCompletion(this, 1);
		});
	}
	
	@Override
	public void setControl(Control control) {
		this.control = control;
	}

	@Override
	public Pane getPane() {
		return pane;
	}

	@Override
	public void start() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void end() {
		
	}

	@Override
	public void load(String filename) {
		
	}
	

}
