package pacman;
import java.awt.Color;
import java.awt.Graphics;

public class Nothing implements IGameObject 
{
	private int x,y;

	public Nothing(Point p) 
	{
		x = p.x * 30;
		y = p.y * 30;
	}

	@Override
	public void draw(Graphics g) 
	{
		g.setColor(Color.BLACK);
		g.fillRect(x, y, 30, 30);
	}
}
