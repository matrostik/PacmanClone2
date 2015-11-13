package pacman;

import java.awt.Graphics;
import java.awt.Image;


public class Pacman extends Hero implements IGameObject {
	private int type; // Пасть открыта/закрыта
	private Sprite sprites[][];

	public Pacman(Point p, int direction,Image images[][]) {
		super(p,direction);

		sprites = new Sprite[4][2];
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < images[i].length; j++)
				sprites[i][j] = new Sprite(images[i][j]);
		}

		type = 0;
	}

	@Override
	public void draw(Graphics g) {
		Point p = getCurCoord();
		int x = p.x * 30;
		int y = p.y * 30;
		sprites[direction][type].draw(g, new Point(x,y));
		type += 1;
		if(type == 2)
			type = 0;
	}

}
