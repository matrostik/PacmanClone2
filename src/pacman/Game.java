package pacman;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Game extends Canvas implements Runnable 
{
	private static final long serialVersionUID = 1;
	public static int SCORES = 0;
	private static boolean running;
	private static boolean win;

	private GameMap map;
	private IGameObject objects[];
	private Pacman pacman;
	private Ghost ghost[]; 

	public void pacmanIsOn(Point curCoord) 
	{
		if(map.foodIsOn(curCoord)) 
		{
			SCORES--;
			if(SCORES == 0) {
				win = true;
				stopGame();
				return;
			}
			map.delFoodAt(curCoord);
			objects[ map.getNumOfObjOn(curCoord) ] = new Nothing(curCoord);
		}
	}

	public static void stopGame() 
	{
		Game.running = false;
	}

	/*
	 * Draw end of the game
	 */
	private void theEnd() 
	{
		BufferStrategy bs = getBufferStrategy();
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0,0,Constants.Width,Constants.Height);
		g.setFont(new Font("Monospaced",Font.BOLD,20));
		g.setColor(Color.red);
		String str;
		if(win == true)
			str = "YOU WON";
		else
			str = "YOU LOST";
		Sound.playDie();
		g.drawString(str, Constants.Width/2-str.length()*10+15, Constants.Height/2-10);
		g.dispose();
		bs.show();
	}

	public void start() 
	{
		running = true;
		new Thread(this).start();
		Sound.playIntro();
	}

	public void run() 
	{
		init();
		while(running) 
		{
			update();
			render();
		}
		theEnd();
	}

	public void init() 
	{
		running = true;
		win = false;
		Image[][] pacmanImages = getPacmanImages();
		Image[] ghostImages = getGhostImages();

		createBufferStrategy(2);
		requestFocus();
		BufferStrategy bs = getBufferStrategy();
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0,0,Constants.Width,Constants.Height);

		int lenOfMap = Constants.TextOfMap.length;
		objects = new IGameObject[lenOfMap*lenOfMap];
		map = new GameMap();
		ghost = new Ghost[Constants.GhostsCount];

		int indOfObj = 0; 
		int indOfGhost = 0;
		for(int i = 0; i < lenOfMap; i++)
			for(int j = 0; j < lenOfMap; j++) 
			{
				char value = Constants.TextOfMap[i][j];
				Point p = new Point(j,i);

				if(value == ' ') {
					SCORES += 1;
					map.setFoodAt(p);
					objects[indOfObj] = new Food(p); 
				}
				else if(value == '#') {
					map.setWallAt(p);
					objects[indOfObj] = new Wall(p);

				}
				else if(value == 'p') {
					map.setHeroAt(p, Constants.Pacman);
					objects[indOfObj] = new Nothing(p);
					pacman = new Pacman(p,0,pacmanImages);
				}
				else if(value == 'g') {
					map.setHeroAt(p, Constants.Ghost);
					objects[indOfObj] = new Nothing(p);
					ghost[indOfGhost] = new Ghost(p,0,ghostImages);
					indOfGhost += 1;
				}
				else // if(value == 'x')
					objects[indOfObj] = new Nothing(p);
				objects[indOfObj].draw(g);
				indOfObj += 1;
			}
		g.dispose();
		bs.show();
		addKeyListener(new KeyboardHandler());
	}

	private class KeyboardHandler extends KeyAdapter 
	{
		public void keyPressed(KeyEvent e) 
		{
			if(e.getKeyCode() == KeyEvent.VK_LEFT) 
				pacman.setDirection(Constants.Left);
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT) 
				pacman.setDirection(Constants.Right);
			else if(e.getKeyCode() == KeyEvent.VK_DOWN) 
				pacman.setDirection(Constants.Down);
			else
				pacman.setDirection(Constants.Up);
		}
	}

	private Image[] getGhostImages() 
	{
		Image ar[] = new Image[4];
		ar[0] = getImage("ghost_u.png");
		ar[1] = getImage("ghost_r.png");
		ar[2] = getImage("ghost_d.png");
		ar[3] = getImage("ghost_l.png");
		return ar;
	}

	private Image[][] getPacmanImages() 
	{
		Image ar[][] = new Image[4][2];
		ar[0][0] = getImage("pacman_u_o.png");
		ar[0][1] = getImage("pacman_u_c.png");
		ar[1][0] = getImage("pacman_r_o.png");
		ar[1][1] = getImage("pacman_r_c.png");
		ar[2][0] = getImage("pacman_d_o.png");
		ar[2][1] = getImage("pacman_d_c.png");
		ar[3][0] = getImage("pacman_l_o.png");
		ar[3][1] = getImage("pacman_l_c.png");
		return ar;
	}

	private Image getImage(String path) 
	{
		BufferedImage sourceImage = null;
		try 
		{
			URL url = Game.class.getResource("/pacman/files/" + path);
			sourceImage = ImageIO.read(url);
		}
		catch(IOException e) 
		{
			e.printStackTrace();
		}
		Image result = Toolkit.getDefaultToolkit().createImage(sourceImage.getSource());
		return result;
	}

	private void render() 
	{ 
		BufferStrategy bs = getBufferStrategy();
		Graphics g = bs.getDrawGraphics();
		objects[map.getNumOfObjOn(pacman.getPrevCoord())].draw(g);

		pacman.draw(g);

		for(int i = 0; i < Constants.GhostsCount; i ++) 
		{
			Ghost gh = ghost[i];
			Point previous = gh.getPrevCoord();
			Point current = gh.getCurCoord();
			if(previous.equal(current) == false )
				if(map.whoIsOn(previous) == Constants.Nobody)
					objects[map.getNumOfObjOn(previous)].draw(g);

			objects[ map.getNumOfObjOn(gh.getCurCoord())].draw(g);
			gh.draw(g);
		}
		// draw score
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, 30, 30);
		g.setColor(Color.white);
		g.setFont(new Font("Serif",1,14));
		g.drawString("Score: " + Integer.toString(SCORES),0, 15);
		g.dispose();
		bs.show();
		try 
		{
			Thread.sleep(Constants.GameSpeed);
		}
		catch(Exception e) 
		{
			System.out.println(" Error: " + e.getMessage()); 
		}
	}
	
	private void update() 
	{
		pacman.move();
		map.heroMoved(pacman);
		Point pacmanCoord = pacman.getCurCoord();
		pacmanIsOn(pacmanCoord);

		for(int i = 0; i < Constants.GhostsCount; i++) 
		{
			ghost[i].move();
			map.heroMoved(ghost[i]);
			if(pacmanCoord.onOneLineWith(ghost[i].getCurCoord()) && map.lineIsFree(pacmanCoord, ghost[i].getCurCoord()))
				ghost[i].pacmanIsVisible(pacmanCoord, pacman.getDirection());
			ghost[i].launchAI();
		}
	}
}
