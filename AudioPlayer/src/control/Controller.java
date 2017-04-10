/**
 * 
 */
package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

import javazoom.jl.decoder.JavaLayerException;
import gui.GuiPlayer;
import control.Mp3Player;

/**
 * @author Zykoq
 * 
 */
public class Controller {

	private static volatile Controller instance;

	private Mp3Player player;

	private File lastPlayed;

	private Properties properties;

	private File dir;

	private File[] playList;

	private Controller() {
		GuiPlayer gui = new GuiPlayer();
		gui.setVisible(true);
		properties = new Properties();
		try {
			properties.load(new FileInputStream("config.ini"));
			dir = new File(properties.getProperty("currentdir"));
			System.out.println(dir.getAbsolutePath());
		} catch (Exception e) {
			System.out.println("Could not find config file");
			dir = null; // default user directory
		}
	}

	public static Controller getInstance() {
		if (instance == null) {
			synchronized (Controller.class) {
				if (instance == null) {
					instance = new Controller();
				}
			}
		}
		return instance;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	private void init(File file) throws FileNotFoundException, JavaLayerException {
		if (isMusicFile(file.getName())) {
			player = new Mp3Player(new FileInputStream(file));
			// player.setInputStream(new FileInputStream(file));
		}
	}

	public boolean setFile(File file) {
		lastPlayed = file;
		return file.isFile();
	}

	public void setPlayList(File[] files) {
		this.playList = files;
	}

	public boolean play() {
		if (playList != null && playList.length > 0) {
			playList();
		} else {
			try {
				init(lastPlayed);
				player.play();
			} catch (Exception e) {
				System.out.println(e);
				player.close();
				return false;
			}
		}
		return true;
	}

	private void playList() {
		try {
			for (File file : playList) {
				init(file);
				player.play();
				player.close();
			}
		} catch (Exception e) {
			System.err.println("Exception " + e);
		}
	}

	public void stop() {
		if (player != null) {
			player.close();
		}
	}

	public void pause() {
		if (player != null) {
			// player.pause();
		}
	}

	private boolean isMusicFile(String filename) {
		return filename.contains("mp3");
	}

	public boolean isFileLoaded() {// player != null &&
		return lastPlayed != null || (playList != null && playList.length > 0);
	}

	/**
	 * @param dir
	 *            the dir to set
	 */
	public void setDir(File dir) {
		this.dir = dir;
		properties.setProperty("currentdir", dir.getAbsolutePath());
		try {
			properties.store(new FileOutputStream(new File("config.ini")), null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

	/**
	 * @return the dir
	 */
	public File getDir() {
		return dir;
	}

	public void setFrames(int value) {
		System.out.println("Setting frame: " + value);
	}

}
