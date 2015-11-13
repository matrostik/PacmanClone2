package pacman;

public class GameMap {
	private boolean[][] food;
	private boolean[][] walls;
	private byte[][] heroes;
	private int length;

	public GameMap() {
		length = Constants.LenOfMap;
		food = new boolean[length][length];
		walls = new boolean[length][length];
		heroes = new byte[length][length];
	}

	public int getLength() {
		return length;
	}

	public void setFoodAt(Point p) {
		food[p.y][p.x] = true;
	}
	public void setWallAt(Point p) {
		walls[p.y][p.x] = true;
	}
	public void setHeroAt(Point p,byte who) {
		heroes[p.y][p.x] = who;
		//System.out.println(who+": ("+p.x+";"+p.y+")");
	}


	public void delFoodAt(Point p) {
		food[p.y][p.x] = false;
	}
	public void delWallAt(Point p) {
		walls[p.y][p.x] = false;
	}
	public void delHeroAt(Point p) {
		//System.out.println(p.x + " " + p.y + " DELETED");
		heroes[p.y][p.x] = 0;
	}


	public boolean foodIsOn(Point p) {
		return food[p.y][p.x];
	}
	public byte whoIsOn(Point p) {
		
		return heroes[p.y][p.x];
	}
	public boolean wallIsOn(Point p) {
		if(p.x >= length || p.y >= length || p.x < 0 || p.y < 0)
			return true;
		return walls[p.y][p.x];
	}


	public  boolean isFree(Point p,byte who) {
		if(p.x >= length || p.y >= length || p.x < 0 || p.y < 0 || wallIsOn(p) || (who == Constants.Ghost && whoIsOn(p) == Constants.Ghost))
			return false;
		return true;
	}
	
	public void heroMoved(Hero hero) {
		Point coord = hero.getCurCoord();

		if( isFree(coord, whoIsOn(hero.getPrevCoord())) ) {
			if(
				(whoIsOn(hero.getPrevCoord()) == Constants.Ghost  &&  whoIsOn(coord) == Constants.Pacman) // Призрак схавал пакмана
						||
				(whoIsOn(hero.getPrevCoord()) == Constants.Pacman  &&  whoIsOn(coord) == Constants.Ghost) // или пакман сожрал призрака
			)
				Game.stopGame();
			setHeroAt(hero.getCurCoord(), whoIsOn(hero.getPrevCoord()));
			delHeroAt(hero.getPrevCoord());
			
		}
		else {
			hero.cancelMove();
		}
	}
	
	public int getNumOfObjOn(Point p) {
		return p.x + p.y * length;
	}
	
	public boolean lineIsFree(Point p1, Point p2) { // Нет ли стен между точками
		/*
		 * TODO:
		 *   Переписать
		 */
		if(p1.x == p2.x) {
			if(p1.y < p2.y) {
				for(int i = p1.y+1; i < p2.y; i++)
					if(walls[i][p1.x] == true)
						return false;
			}
			else
				for(int i = p2.y+1; i < p1.y; i++)
					if(walls[i][p1.x] == true)
						return false;
		}
		else {
			if(p1.x < p2.x) {
				for(int i = p1.x+1; i < p2.x; i++)
					if(walls[p1.y][i] == true)
						return false;
			}
			else
				for(int i = p2.x+1;i < p1.x; i++)
					if(walls[p1.y][i] == true)
						return false;
		}
		
		return true;
	}
}
