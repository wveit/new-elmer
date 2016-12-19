package platformer.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import platformer.engine.shape.Rectangle;
import platformer.world.Enemy;
import platformer.world.Goal;
import platformer.world.LavaMonster;
import platformer.world.Platform;
import platformer.world.Player;
import platformer.world.Spikey;
import platformer.world.Vulcor;
import platformer.world.World;

public class WorldFileSystem {
	
	public static String worldToString(World world){
		StringBuilder str = new StringBuilder();
		
		str.append("gravity " + world.gravity + " ;\n");
		
		Rectangle r = world.player.rect();
		str.append("player " + r.minX() + " " + r.minY() + " " + r.width() + " " + r.height() + " ;\n");
		
		r = world.goal.rect();
		str.append("goal " + r.minX() + " " + r.minY() + " " + r.width() + " " + r.height() + " ;\n");
		
		for(Platform p : world.platformList){
			r = p.rect();
			str.append("platform " + r.minX() + " " + r.minY() + " " + r.width() + " " + r.height() + " ;\n");
		}
		
		for(Enemy e : world.enemyList){
			r = e.rect();
			str.append(enemyName(e) + " " + r.minX() + " " + r.minY() + " " + r.width() + " " + r.height() + " ;\n");
		}
		
		return str.toString();
	}
	
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
		pw.println(worldToString(world));		
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
		else
			return "monster";
	}
	
	public void loadWorldFromString(World world, String levelDataString){
		Scanner in = new Scanner(levelDataString);		
		while(in.hasNext()){
			loadEntity(world, in);
		}
		in.close();
	}
	
	public static boolean loadWorldFromFile(World world, String filename){
//		Scanner in = null;
//		try{
//			File file = new File(filename);
//			if(!file.exists()){
//				System.out.println("WorldFileSystem.loadWorld(...) - cannot find file " + filename);
//				return false;
//			}
//			in = new Scanner(file);
//		}
//		catch(FileNotFoundException e){
//			System.out.println("WorldFileSystem.loadWorld(...) - Exception while loading file " + filename);
//			return false;
//		}
//		
//		while(in.hasNext()){
//			loadEntity(world, in);
//		}
//		
//		in.close();
//		return true;
		
		String levelData = fileToString(filename);
		if(levelData.isEmpty())
			return false;
		
		Scanner in = new Scanner(levelData);
		while(in.hasNext())
			loadEntity(world, in);
		in.close();
		return true;
	}
	
	public static String fileToString(String filename){
		Scanner in = null;
		try{
			File file = new File(filename);
			if(!file.exists()){
				System.out.println("WorldFileSystem.fileToString(...) - cannot find file " + filename);
				return "";
			}
			in = new Scanner(file);
		}
		catch(FileNotFoundException e){
			System.out.println("WorldFileSystem.fileToString(...) - Exception while loading file " + filename);
			return "";
		}
		
		StringBuilder str = new StringBuilder();
		
		while(in.hasNext()){
			str.append(in.nextLine() + "\n");
		}
		
		in.close();
		return str.toString();
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
		else if(tokenList.get(0).equals("gravity"))
			world.gravity = Double.parseDouble(tokenList.get(1));
		else
			System.out.println("WorldFileSystem.loadEntity(...): couldn't add " + tokenList.get(0));
	}

	public boolean loadVolcano(World world){
		world.gravity = -1000;
		
		double width = 1200;
		
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
		
		return true;
	}
	
}