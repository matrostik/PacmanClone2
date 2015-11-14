package pacman;
import java.awt.Graphics;

public class Wall implements IGameObject 
{
	private int x,y;

	public Wall(Point p) 
	{
		x = p.x * 30;
		y = p.y * 30;
	}
	
	@Override
	public void draw(Graphics g) 
	{
		g.setColor(Constants.WallColor);
		g.fillRect(x, y, 30, 30);
	}

}
