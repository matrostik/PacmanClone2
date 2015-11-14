package pacman;
import java.awt.Color;


public class Constants {
	public static double Version = 1.90;
	public static String Title = "PACMAN v" + Version;
	public static int Width;
	public static int Height;
	public static char[][] TextOfMap;
	public static int GhostsCount; // Кол-во призраков. Считывается из файла
	public static int LenOfMap; // Подразумевается, что карта квадратная. Считывается из файла
	public static Color WallColor = Color.BLUE; // Цвет стены)))

	static public int GameSpeed = 150; // Thread.sleep()  в  Game.render()
	static public int Right = 1, Down = 2, Left = 3, Up = 0;
	static public byte Pacman = 1;
	static public byte Ghost = 2;
	static public byte Nobody = 0;
	static public int RecurDeep; // Глубина расчета призраков. Считывается из файла
}