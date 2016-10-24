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
		// draw each of the world's items in terms of the viewport
		renderBackground();
		
		for(Platform p : world.platformList)
			render(p);
		
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
		
		if(world.player != null)
			render(world.player);
		
		if(lava != null)
			render(lava);
	}
	
	// !!!! come back to this... it's not nice
	public void renderBackground(){
//		// find src Rectangle
//		final double levelHeight = 5000;
//		final double scrollRatio = 0.04;
//		
//		double srcWidth = backgroundImage.getWidth();
//		double srcHeight = srcWidth * viewport.height() / viewport.width();
//		double srcY = backgroundImage.getHeight() - srcHeight - viewport.minY() * scrollRatio;
//		Rectangle src = new Rectangle(0, srcY, backgroundImage.getWidth(), srcHeight );
//		
//		// find dest Rectangle
//		Rectangle dest = transromRect(viewport);
//		
//		// draw
//		draw(backgroundImage, src, dest);
	}
	
	public void render(LavaMonster m){
		Rectangle r = converter.worldRectToScreenRect(m.rect());
		
		if( Math.floor(r.minX()) / 40 % 2 == 0)
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
			ninjaAnimator.setRect((int)player.rect().minX() / 30 % 4);
		}
		else if(player.vX() > 0.1){
			ninjaAnimator.setFlippedHorizontal(false);
			ninjaAnimator.setRect((int)player.rect().minX() / 30 % 4);
		}
		else{
			ninjaAnimator.setRect(0);
		}
		
		ninjaAnimator.draw(gc, r);
	}
	
	public void render(Platform platform){
		Rectangle r = converter.worldRectToScreenRect(platform.rect());
		
		//draw(platformImage, new Rectangle(0, 0, Math.min(r.width(), 400), r.height()), r);
		gc.setFill(Color.BROWN);
		gc.fillRect(r.minX(), r.minY(), r.width(), r.height());
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
