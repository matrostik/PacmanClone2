package pacman;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.swing.JButton;
import javax.swing.JFrame;

public class StartWindow 
{
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(StartWindow.class.getResourceAsStream("/pacman/files/config")));
			String s = br.readLine(); // Длина
			int len = Integer.parseInt(s);
			Constants.LenOfMap = len;
			Constants.Width = Constants.Height = len * 30;
			Constants.TextOfMap = new char[len][len];
			s = br.readLine(); // Призраки
			Constants.GhostsCount = Integer.parseInt(s);
			s = br.readLine(); // глубина рекурсии
			Constants.RecurDeep = Integer.parseInt(s);

			for(int i = 0; i < len; i++) { // Копирование карты
				s = br.readLine();
				s.getChars(0,len,Constants.TextOfMap[i], 0);
			}
		}
		catch(Exception e) 
		{
			System.out.println("Error: file");
			e.printStackTrace();
			return;
		}

		JFrame frame = new JFrame(Constants.Title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridBagLayout());
		frame.setSize(250,250);
		frame.setResizable(false);
		frame.setVisible(true);

		JButton b = new JButton("START GAME");
		b.setSize(40,40);
		b.setLocation(12,12);
		b.setVisible(true);
		b.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				newGame();
			}
		});
		frame.getContentPane().add(b);
	}

	private static void newGame() 
	{
		JFrame frame = new JFrame("PACMAN");
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setSize(250,250);
		frame.setResizable(false);
		frame.setVisible(true);

		Game g = new Game();
		g.setPreferredSize(new Dimension(Constants.Width,Constants.Height));
		frame.add(g);
		frame.pack();
		g.start();
	}
}
