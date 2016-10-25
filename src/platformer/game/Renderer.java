package platformer.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import platformer.engine.shape.Rectangle;
import platformer.engine.sprite.SpriteAnimator;

import java.io.File;

import javafx.scene.image.Image;

public class Renderer {
	private GraphicsContext gc;
	private ScreenWorldRectConverter converter;
	
	private Image ninjaImage;
	private Image lavaMonsterImage;
	private Image spikeyImage;
	private Image vulcorImage;
	private Image backgroundImage;
	private Image platformImage;
	private Image gameOverImage;
	
	private SpriteAnimator ninjaAnimator;
	private SpriteAnimator lavaMonsterAnimator;
	
	public Renderer(GraphicsContext gc, ScreenWorldRectConverter converter){
		this.gc = gc;
		this.converter = converter;
		
		// load images		
		try{
			ninjaImage = new Image(new File("assets/platformer/ninja.png").toURI().toURL().toString());
			ninjaAnimator = new SpriteAnimator(ninjaImage);
			ninjaAnimator.addMode();
			ninjaAnimator.addRectToMode(0, new Rectangle(0, 0, 105, 175));
			ninjaAnimator.addRectToMode(0, new Rectangle(105, 0, 105, 175));
			ninjaAnimator.addRectToMode(0, new Rectangle(210, 0, 105, 175));
			ninjaAnimator.addRectToMode(0, new Rectangle(315, 0, 105, 175));
			ninjaAnimator.showBox(true);
			
			lavaMonsterImage = new Image(new File("assets/platformer/lava_monster.png").toURI().toURL().toString());
			lavaMonsterAnimator = new SpriteAnimator(lavaMonsterImage);
			lavaMonsterAnimator.addMode();
			lavaMonsterAnimator.addRectToMode(0, new Rectangle(0, 0, 191, 263));
			
			spikeyImage = new Image(new File("assets/platformer/spikey.png").toURI().toURL().toString());
			vulcorImage = new Image(new File("assets/platformer/vulcor.png").toURI().toURL().toString());
			backgroundImage = new Image(new File("assets/platformer/volcano_background.png").toURI().toURL().toString());
			platformImage = new Image(new File("assets/platformer/platform.png").toURI().toURL().toString());
			gameOverImage = new Image(new File("assets/platformer/gameover.png").toURI().toURL().toString());
		
		} 
		catch(Exception e){
			System.out.println("Renderer had problems loading images.");
		}
		
	}
	
	
	public void render(World world){
		// Draw Background
		renderBackground();
		
		// Draw platforms
		for(Platform p : world.platformList)
			render(p);
		
		// Draw enemies
		// Special logic for Lava so that it will be drawn last (it should always be on top)
		Lava lava = null;
		for(Enemy e : world.enemyList){
			if(e instanceof LavaMonster)
				render((LavaMonster)e);
			else if(e instanceof Spikey)
				render((Spikey)e);
			else if(e instanceof Vulcor)
				render((Vulcor)e);
			else if(e instanceof Lava)
				lava = (Lava)e;
			else
				render(e);
		}
		if(lava != null)
			render(lava);
		
		// Draw Player
		if(world.player != null)
			render(world.player);

	}
	

	public void renderBackground(){

		double worldTop = 5000;
		
		// find dest Rectangle
		Rectangle dest = new Rectangle(converter.getScreen());
		
		// find src Rectangle
		Rectangle src = new Rectangle();
		src.setWidth(backgroundImage.getWidth());
		src.setHeight(converter.getScreen().height() * backgroundImage.getWidth() / converter.getScreen().width());
		src.setY(backgroundImage.getHeight() - src.height() - converter.getWorldViewport().minY() * (backgroundImage.getHeight() - src.height()) / (worldTop - converter.getWorldViewport().height()));
		
		// draw
		draw(backgroundImage, src, dest);
	}
	
	public void render(LavaMonster m){
		Rectangle r = converter.worldRectToScreenRect(m.rect());
		
		if((int)Math.abs(r.minX()) / 40 % 2 == 0)
			lavaMonsterAnimator.setFlippedHorizontal(true);
		else
			lavaMonsterAnimator.setFlippedHorizontal(false);
		
		lavaMonsterAnimator.draw(gc, r);
	}
	
	public void render(Spikey s){
		Rectangle r = converter.worldRectToScreenRect(s.rect());
		draw(spikeyImage, new Rectangle(0, 0, 75, 110), r);
	}
	
	public void render(Vulcor v){
		Rectangle r = converter.worldRectToScreenRect(v.rect());
		
		if(v.actionMode() == Vulcor.ActionMode.READY)
			draw(vulcorImage, new Rectangle(100, 0, 100, 100), r);
		else
			draw(vulcorImage, new Rectangle(0, 0, 100, 100), r);
	}
	
	public void render(Enemy enemy){
		Rectangle r = converter.worldRectToScreenRect(enemy.rect());
		
		gc.setFill(Color.BROWN);
		gc.fillRect(r.minX(), r.minY(), r.width(), r.height());
	}
	
	public void render(Player player){
		Rectangle r = converter.worldRectToScreenRect(player.rect());
		
		if(player.vX() < -0.1){
			ninjaAnimator.setFlippedHorizontal(true);
			ninjaAnimator.setRect((int)Math.abs(player.rect().minX()) / 30 % 4);
		}
		else if(player.vX() > 0.1){
			ninjaAnimator.setFlippedHorizontal(false);
			ninjaAnimator.setRect((int)Math.abs(player.rect().minX()) / 30 % 4);
		}
		else{
			ninjaAnimator.setRect(0);
		}
		
		ninjaAnimator.draw(gc, r);
	}
	
	public void render(Platform platform){
		Rectangle r = converter.worldRectToScreenRect(platform.rect());
		double imageWidth = platformImage.getWidth();
		
		if(r.width() < imageWidth){
			draw( platformImage, new Rectangle(0, 0, r.width() - 50, r.height()), new Rectangle(r.minX(), r.minY(), r.width() - 50, r.height()) );
			draw( platformImage, new Rectangle(imageWidth - 50, 0, 50, r.height()), new Rectangle(r.maxX() - 50, r.minY(), 50, r.height()) );
		}
		else if(r.width() > imageWidth){
			// draw the first 50 pixels
			draw( platformImage, new Rectangle(0, 0, 50, r.height()), new Rectangle(r.minX(), r.minY(), 50, r.height()) );
			
			// repeatedly draw the middle (300) pixels until there is less than 350 pixels to draw
			double x = r.minX() + 50;
			while(x < r.maxX() - imageWidth + 50){
				draw( platformImage, new Rectangle(50, 0, imageWidth - 100, r.height()), new Rectangle(x, r.minY(), imageWidth - 100, r.height()) );
				x += (400 - 100);
			}
			
			// draw middle pixels until there is less than 50 pixels left to draw
			draw( platformImage, new Rectangle(50, 0, r.maxX() - 50 - x, r.height()), new Rectangle(x, r.minY(), r.maxX() - 50 - x, r.height()) );

			// draw the last 50 pixels
			draw( platformImage, new Rectangle(imageWidth - 50, 0, 50, r.height()), new Rectangle(r.maxX() - 50, r.minY(), 50, r.height()) );
		}
		else
			draw(platformImage, new Rectangle(0, 0, imageWidth, r.height()), r);
	}
	
	public void render(Lava lava){
		Rectangle r = converter.worldRectToScreenRect(lava.rect());
		
		gc.setFill(Color.ORANGE);
		gc.fillRect(r.minX(), r.minY(), r.width(), r.height());
	}

	public void renderGameOver(){
		gc.drawImage(gameOverImage, 0, 0, converter.getScreen().width(), converter.getScreen().height());
	}
	
	private void draw(Image img, Rectangle srcRect, Rectangle destRect){
		gc.drawImage(
				img, 
				srcRect.minX(),  srcRect.minY(), srcRect.width(), srcRect.height(),
				destRect.minX(), destRect.minY(), destRect.width(), destRect.height());
	}

}
