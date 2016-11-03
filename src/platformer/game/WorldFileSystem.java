package platformer.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import platformer.engine.shape.Rectangle;

public class WorldFileSystem {
	
	public static boolean saveWorld(World world, String filename){
		PrintWriter pw = null;
		File file = new File(filename);
		try{
			pw = new PrintWriter(file);
		}
		catch(FileNotFoundException e){
			System.out.println("WorldFileSystem.saveWorld(...) FileNotFoundException");
			return false;
		}
		
		pw.println("gravity " + world.gravity + " ;");
		
		Rectangle r = world.player.rect();
		pw.println("player " + r.minX() + " " + r.minY() + " " + r.width() + " " + r.height() + " ;");
		
		r = world.goal.rect();
		pw.println("goal " + r.minX() + " " + r.minY() + " " + r.width() + " " + r.height() + " ;");
		
		r = world.leftBoundary;
		pw.println("leftBoundary " + r.minX() + " " + r.minY() + " " + r.width() + " " + r.height() + " ;");
		
		r = world.rightBoundary;
		pw.println("rightBoundary " + r.minX() + " " + r.minY() + " " + r.width() + " " + r.height() + " ;");
		
		for(Platform p : world.platformList){
			r = p.rect();
			pw.println("platform " + r.minX() + " " + r.minY() + " " + r.width() + " " + r.height() + " ;");
		}
		
		for(Enemy e : world.enemyList){
			r = e.rect();
			pw.println(enemyName(e) + " " + r.minX() + " " + r.minY() + " " + r.width() + " " + r.height() + " ;");
		}
		
		pw.close();
		return true;
	}
	
	private static String enemyName(Enemy e){
		if(e instanceof LavaMonster)
			return "lavaMonster";
		else if(e instanceof Spikey)
			return "spikey";
		else if(e instanceof Vulcor)
			return "vulcor";
		else if(e instanceof Lava)
			return "lava";
		else
			return "monster";
	}
	
	public static boolean loadWorld(World world, String filename){
		Scanner in = null;
		try{
			File file = new File(filename);
			if(!file.exists()){
				System.out.println("WorldFileSystem.loadWorld(...) - cannot find file " + filename);
				return false;
			}
			in = new Scanner(file);
		}
		catch(FileNotFoundException e){
			System.out.println("WorldFileSystem.loadWorld(...) - Exception while loading file " + filename);
			return false;
		}
		
		while(in.hasNext()){
			loadEntity(world, in);
		}
		
		in.close();
		return true;
	}
	
	private static void loadEntity(World world, Scanner in){
		ArrayList<String> tokenList = new ArrayList<>();
		String token = "";
		while(!token.equals(";")){
			token = in.next();
			tokenList.add(token);
		}
		
		if(tokenList.get(0).equals("player"))
			world.player = new Player(Double.parseDouble(tokenList.get(1)), Double.parseDouble(tokenList.get(2)), Double.parseDouble(tokenList.get(3)), Double.parseDouble(tokenList.get(4)));
		else if(tokenList.get(0).equals("goal"))
			world.goal = new Goal(Double.parseDouble(tokenList.get(1)), Double.parseDouble(tokenList.get(2)), Double.parseDouble(tokenList.get(3)), Double.parseDouble(tokenList.get(4)));
		else if(tokenList.get(0).equals("spikey"))
			world.enemyList.add(new Spikey(Double.parseDouble(tokenList.get(1)), Double.parseDouble(tokenList.get(2)), Double.parseDouble(tokenList.get(3)), Double.parseDouble(tokenList.get(4))));
		else if(tokenList.get(0).equals("lavaMonster"))
			world.enemyList.add(new LavaMonster(Double.parseDouble(tokenList.get(1)), Double.parseDouble(tokenList.get(2)), Double.parseDouble(tokenList.get(3)), Double.parseDouble(tokenList.get(4))));
		else if(tokenList.get(0).equals("vulcor"))
			world.enemyList.add(new Vulcor(Double.parseDouble(tokenList.get(1)), Double.parseDouble(tokenList.get(2)), Double.parseDouble(tokenList.get(3)), Double.parseDouble(tokenList.get(4))));
		else if(tokenList.get(0).equals("platform"))
			world.platformList.add(new Platform(Double.parseDouble(tokenList.get(1)), Double.parseDouble(tokenList.get(2)), Double.parseDouble(tokenList.get(3)), Double.parseDouble(tokenList.get(4))));
		else if(tokenList.get(0).equals("leftBoundary"))
			world.leftBoundary = new Rectangle(Double.parseDouble(tokenList.get(1)), Double.parseDouble(tokenList.get(2)), Double.parseDouble(tokenList.get(3)), Double.parseDouble(tokenList.get(4)));
		else if(tokenList.get(0).equals("rightBoundary"))
			world.rightBoundary = new Rectangle(Double.parseDouble(tokenList.get(1)), Double.parseDouble(tokenList.get(2)), Double.parseDouble(tokenList.get(3)), Double.parseDouble(tokenList.get(4)));
		else if(tokenList.get(0).equals("lava"))
			world.enemyList.add(new Lava(Double.parseDouble(tokenList.get(1)), Double.parseDouble(tokenList.get(2)), Double.parseDouble(tokenList.get(3)), Double.parseDouble(tokenList.get(4))));
		else if(tokenList.get(0).equals("gravity"))
			world.gravity = Double.parseDouble(tokenList.get(1));
		else
			System.out.println("WorldFileSystem.loadEntity(...): couldn't add " + tokenList.get(0));
	}

	public boolean loadVolcano(World world){
		world.gravity = -1000;
		
		double width = 1200;
		double height = 800;
		double levelHeight = 10000;
		
		world.leftBoundary = new Rectangle(-50, 0, 50, levelHeight * 100);
		world.rightBoundary = new Rectangle(width, 0, 50, levelHeight * 100);
		
		world.platformList.add( new Platform(0, 0, width, 50) );
		world.platformList.add( new Platform(0, 250, width / 3, 50) );
		world.platformList.add( new Platform(2 * width / 3, 250, width / 3, 50) );
		
		double start = 500;
		double size = 500;
		for(int i = 0; i < 100; i++){
			world.platformList.add( new Platform(width / 3, start + i * size, width / 3, 50) );
			world.platformList.add( new Platform(0, start + i * size + 250, width / 3, 50) );
			world.platformList.add( new Platform(2 * width / 3, start + i * size + 250, width / 3, 50) );
		}
		
		world.enemyList.add(new LavaMonster(800, 1000, 50, 50));
		world.enemyList.add(new Vulcor(600, 600, 50, 50));
		world.enemyList.add(new Spikey(600, 600, 50, 50));
		
		world.player = new Player(250, 250, 50, 75);
		world.enemyList.add(new Lava(0, -100 - height, width, height));
		
		return true;
	}
	
}