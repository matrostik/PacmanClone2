package pacman;
import java.awt.Graphics;
import java.awt.Image;

public class Ghost extends Hero implements IGameObject 
{
	private Sprite sprites[];
	private AI ai;

	public Ghost(Point p, int direction,Image[] images) 
	{
		super(p, direction);
		sprites = new Sprite[4];
		for(int i = 0; i < 4; i++)
			sprites[i] = new Sprite(images[i]);
		ai = new AI(this);
	}
	
	public void draw(Graphics g) 
	{
		Point p = getCurCoord();
		int x = p.x * 30;
		int y = p.y * 30;
		sprites[direction].draw(g, new Point(x,y));
	}

	public void launchAI() 
	{
		ai.launch();
	}
	
	public void pacmanIsVisible(Point p, int direction) 
	{
		ai.setPacman(p, direction);
	}
	
	public void cancelMove()
	{
		super.cancelMove();
		ai.collision();
	}
}
