package platformer.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import platformer.engine.shape.Rectangle;


public class World {
	public double gravity = 0;
	
	public Player player = null;
	public Rectangle leftBoundary = null;
	public Rectangle rightBoundary = null;
	public ArrayList<Platform> platformList = new ArrayList<>();
	public ArrayList<Enemy> enemyList = new ArrayList<>();
	
	public World(){
	}
	
	private void clear(){
		player = null;
		leftBoundary = null;
		leftBoundary = null;
		platformList.clear();
		enemyList.clear();
	}
	
	public void save(String filename){
//		leftBoundary -50 0 50 10000 ;
//		rightBoundary 1200 0 50 10000 ;
//		gravity -1000 ;
//		player 250 250 50 75 ;
//		platform 0 0 1200 50 ;
//		platform 500 500 250 50 ;
//		lavaMonster 800 100 200 200 ;
		
		PrintWriter pw = null;
		File file = new File(filename);
		try{
		pw = new PrintWriter(file);
		}
		catch(FileNotFoundException e){
			System.out.println("World.save(...) FileNotFoundException");
		}
		
		pw.println("gravity " + gravity + " ;");
		
		Rectangle r = player.rect();
		pw.println("player " + r.minX() + " " + r.minY() + " " + r.width() + " " + r.height() + " ;");
		
		r = leftBoundary;
		pw.println("leftBoundary " + r.minX() + " " + r.minY() + " " + r.width() + " " + r.height() + " ;");
		
		r = rightBoundary;
		pw.println("rightBoundary " + r.minX() + " " + r.minY() + " " + r.width() + " " + r.height() + " ;");
		
		for(Platform p : platformList){
			r = p.rect();
			pw.println("platform " + r.minX() + " " + r.minY() + " " + r.width() + " " + r.height() + " ;");
		}
		
		for(Enemy e : enemyList){
			r = e.rect();
			pw.println(enemyName(e) + " " + r.minX() + " " + r.minY() + " " + r.width() + " " + r.height() + " ;");
		}
		
		pw.close();
	}
	
	private String enemyName(Enemy e){
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
	
	private boolean privateLoad(String filename){
		Scanner in = null;
		try{
			File file = new File(filename);
			if(!file.exists())
				System.out.println("can't find file");
			in = new Scanner(file);
		}
		catch(FileNotFoundException e){
			return false;
		}
		
		while(in.hasNext()){
			loadEntity(in);
		}
		
		in.close();
		return true;
	}
	
	private void loadEntity(Scanner in){
		ArrayList<String> tokenList = new ArrayList<>();
		String token = "";
		while(!token.equals(";")){
			token = in.next();
			tokenList.add(token);
		}
		
		System.out.println(tokenList.get(0));
		
		if(tokenList.get(0).equals("player"))
			player = new Player(Double.parseDouble(tokenList.get(1)), Double.parseDouble(tokenList.get(2)), Double.parseDouble(tokenList.get(3)), Double.parseDouble(tokenList.get(4)));
		else if(tokenList.get(0).equals("spikey"))
			enemyList.add(new Spikey(Double.parseDouble(tokenList.get(1)), Double.parseDouble(tokenList.get(2)), Double.parseDouble(tokenList.get(3)), Double.parseDouble(tokenList.get(4))));
		else if(tokenList.get(0).equals("lavaMonster"))
			enemyList.add(new LavaMonster(Double.parseDouble(tokenList.get(1)), Double.parseDouble(tokenList.get(2)), Double.parseDouble(tokenList.get(3)), Double.parseDouble(tokenList.get(4))));
		else if(tokenList.get(0).equals("vulcor"))
			enemyList.add(new Vulcor(Double.parseDouble(tokenList.get(1)), Double.parseDouble(tokenList.get(2)), Double.parseDouble(tokenList.get(3)), Double.parseDouble(tokenList.get(4))));
		else if(tokenList.get(0).equals("platform"))
			platformList.add(new Platform(Double.parseDouble(tokenList.get(1)), Double.parseDouble(tokenList.get(2)), Double.parseDouble(tokenList.get(3)), Double.parseDouble(tokenList.get(4))));
		else if(tokenList.get(0).equals("leftBoundary"))
			leftBoundary = new Rectangle(Double.parseDouble(tokenList.get(1)), Double.parseDouble(tokenList.get(2)), Double.parseDouble(tokenList.get(3)), Double.parseDouble(tokenList.get(4)));
		else if(tokenList.get(0).equals("rightBoundary"))
			rightBoundary = new Rectangle(Double.parseDouble(tokenList.get(1)), Double.parseDouble(tokenList.get(2)), Double.parseDouble(tokenList.get(3)), Double.parseDouble(tokenList.get(4)));
		else if(tokenList.get(0).equals("lava"))
			enemyList.add(new Lava(Double.parseDouble(tokenList.get(1)), Double.parseDouble(tokenList.get(2)), Double.parseDouble(tokenList.get(3)), Double.parseDouble(tokenList.get(4))));
		else if(tokenList.get(0).equals("gravity"))
			gravity = Double.parseDouble(tokenList.get(1));
		else{
			System.out.println("couldn't add" + tokenList.get(0));
			
		}
			
			
	}
	
	public static void main(String[] args){
		World world = new World();
		world.load("assets/platformer/jungle_level.lvl");
		

		if(world.player == null)
			System.out.println("player is null");
		else
			System.out.println("player is good");
	}
	
	public boolean load(String filename){
		clear();
		
		privateLoad("assets/platformer/blah.lvl");
		
		
		return true;
		
//		if(filename.equals("volcano_level.lvl")){
//			loadVolcano();
//			return true;
//		}
//		
//		else return privateLoad(filename);
	}
	
	private void loadVolcano(){
		gravity = -1000;
		
		double width = 1200;
		double height = 800;
		double levelHeight = 10000;
		
		leftBoundary = new Rectangle(-50, 0, 50, levelHeight * 100);
		rightBoundary = new Rectangle(width, 0, 50, levelHeight * 100);
		
		platformList.add( new Platform(0, 0, width, 50) );
		platformList.add( new Platform(0, 250, width / 3, 50) );
		platformList.add( new Platform(2 * width / 3, 250, width / 3, 50) );
		
		double start = 500;
		double size = 500;
		for(int i = 0; i < 100; i++){
			platformList.add( new Platform(width / 3, start + i * size, width / 3, 50) );
			platformList.add( new Platform(0, start + i * size + 250, width / 3, 50) );
			platformList.add( new Platform(2 * width / 3, start + i * size + 250, width / 3, 50) );
		}
		
		enemyList.add(new LavaMonster(800, 1000, 50, 50));
		enemyList.add(new Vulcor(600, 600, 50, 50));
		enemyList.add(new Spikey(600, 600, 50, 50));
		
		player = new Player(250, 250, 50, 75);
		enemyList.add(new Lava(0, -100 - height, width, height));
	}
	

	
	public void update(double deltaTime){
		
		// update level entities
		player.update(deltaTime, this);
		
		// remove dead enemies from the enemy list and update live enemies
		for(int i = 0; i < enemyList.size(); i++){
			if(enemyList.get(i).isDead()){
				enemyList.remove(i);
				i--;				
			}
			else{
				enemyList.get(i).update(deltaTime, this);
			}
		}

	}
}
