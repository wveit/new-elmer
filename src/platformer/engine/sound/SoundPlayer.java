package platformer.engine.sound;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SoundPlayer {
	MediaPlayer player = null;
	boolean repeat = false;
	
	public boolean load(String filename){
		File file = new File(filename);
		if(!file.exists()){
			System.out.println("SoundPlayer.load(" + filename + ") unsuccessful: Could not find file");
			player = null;
			return false;
		}
		
		try{
			Media media = new Media("file:" + file.getAbsolutePath());
			player = new MediaPlayer(media);
		}
		catch(MediaException e){
			System.out.println("SoundPlayer.load(" + filename + ") unsuccessful: Unsupported format");
			player = null;
			return false;
		}
		
		return true;
	}
	
	public void play(){
		if(repeat && player != null)
			player.setOnEndOfMedia(()->{player.seek(Duration.ZERO);});
		else if(player != null)
			player.setOnEndOfMedia(null);
		
		if(player != null)
			player.play();
	}
	
	public void pause(){
		if(player != null)
			player.pause();
	}
	
	public void stop(){
		if(player != null)
			player.stop();
	}
	
	public void setRepeat(boolean flag){
		repeat = flag;
	}
}
