package pacman;

public abstract class Hero 
{
	protected int direction;
	private Point coord;
	private Point prevCoord;

	public Hero(Point p,int direction) 
	{
		this.direction = direction;
		this.coord = p;
		this.prevCoord = new Point(-1,-1);
	}

	public void move() 
	{
		prevCoord = coord.clone();
		if(direction == Constants.Right) 
			coord.x += 1;
		else if(direction == Constants.Left ) 
			coord.x -= 1;
		else if(direction == Constants.Down) 
			coord.y += 1;
		else
			coord.y -= 1;
	}

	public void cancelMove() 
	{
		coord = prevCoord;
	}

	public void setDirection(int direction) 
	{
		this.direction = direction;
	}

	public int getDirection() 
	{
		return direction;
	}

	public Point getPrevCoord() 
	{
		return new Point(prevCoord.x,prevCoord.y);
	}

	public Point getCurCoord() 
	{
		return new Point(coord.x,coord.y);
	}
}
