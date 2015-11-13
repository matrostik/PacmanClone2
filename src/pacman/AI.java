package pacman;

import java.util.Random;

public class AI {
		Ghost ghost;
		boolean pacmanDetected; // пакман обнаружен
		int pacmanDirection; // В какую сторону бежал пакман
		Point target; // Куда направляемся
		GameMap map;
		public AI(Ghost g) {
			ghost = g;
			pacmanDetected = false;
			pacmanDirection = -1;
			target = null;
		}

		public void launch() {
			if(target == null)
				return;
			if(ghost.getCurCoord().equal(target))  { // Цель достигнута
				ghost.setDirection(pacmanDirection);
				target = null;
			}
		}

	public void  setPacman(Point p,int direction) { // Пакман найден. Меняем направление
		target = p;
		pacmanDirection = direction;
		Point ghostCoord = ghost.getCurCoord();
		if(p.x == ghostCoord.x) {
			if(ghostCoord.y < p.y) // Пакман ниже
				ghost.setDirection(Constants.Down);
			else
				ghost.setDirection(Constants.Up);
		}
		else {
			if(ghostCoord.x < p.x)  // Пакман правее
				ghost.setDirection(Constants.Right);
			else
				ghost.setDirection(Constants.Left);
		}
	}
	public void collision() {// Сменить направление
		if(target != null) { // врезались в призрака
			return;
		}
		Random rand = new Random();
		int d = 0;
		while(d == ghost.getDirection())
			d = rand.nextInt(4);
		ghost.setDirection(d);
	}
}
