package pacman;

import java.net.URL;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class Sound 
{
	private static Sequencer sound_player ;
	private static Sequence currentSound ;
	private static URL eating = Game.class.getResource("/pacman/files/eating.mid");
	private static URL intro = Game.class.getResource("/pacman/files/intro.mid");
	private static URL die = Game.class.getResource("/pacman/files/die.mid");
	
	public static void playIntro()
	{
		System.out.println ( " Sound: " + intro );
		play(intro);
	}

	public static void playEating()
	{
		play(eating);
	}
	
	public static void playDie()
	{
		play(die);
	}
	
	private static void play(URL sound) 
	{
		try 
		{ 
			sound_player = MidiSystem.getSequencer();
			sound_player.open() ;                     
			currentSound= MidiSystem.getSequence(sound) ; 
			sound_player.setSequence(currentSound) ;
			sound_player.start() ;
		} 
		catch ( Exception exc ) 
		{ 
			System.out.println( " Error: " + exc.getMessage() ) ; 
		}
	}    
}
