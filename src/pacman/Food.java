package pacman;
import java.awt.Color;
import java.awt.Graphics;


public class Food implements IGameObject {
	private int x,y;

	public Food(Point p) {
		x = p.x * 30;
		y = p.y * 30;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(x, y, 30, 30);

		g.setColor(Color.white);
		g.fillOval(x+12,y + 12,3,3);
	}

}
