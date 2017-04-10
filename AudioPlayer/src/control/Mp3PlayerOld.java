/**
 * 
 */
package control;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;

/**
 * @author Zykoq
 * 
 */
public class Mp3PlayerOld extends BasicPlayer {

	private AdvancedPlayer player;
	private int frames = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see player.control.BasicPlayer#start()
	 */
	@Override
	public void start() throws JavaLayerException {
		player = new AdvancedPlayer(getInputStream());
		System.out.println("want to play");
		while(player.play(1)){
			//player.play();//(frames, frames + 1);
			frames++;
			System.out.println(frames);
		}			
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see player.control.BasicPlayer#stop()
	 */
	@Override
	public void stop() {
		player.close();
		//frames = 0;
	}

	@Override
	public void pause() {
		player.close();
		
	}

}
