package jc;


public class Logic {

	
	public char[][] gameboard= new char[3][3];
	
	public char[][] getGameboard() {
		return gameboard;
	}

	public void setGameboard(char[][] gameboard) {
		this.gameboard = gameboard;
	}

	public void clearGameboard(){
		
		for(int x = 0; x < 3; x++){
			for(int y = 0; y < 3; y++){
				
				this.gameboard[x][y] = '_';
			}
		}
	
	}
	
	public void playerMove(int x, int y){
		 this.gameboard[x][y] = 'X';
	}
	
	public void computerMove(int x, int y){
		 this.gameboard[x][y] = 'O';
	}	
	
	

	
}
