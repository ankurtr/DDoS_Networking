/**
 * 
 */
package control;

import java.io.InputStream;



/**
 * @author Zykoq
 * 
 */
public abstract class BasicPlayer{

	private InputStream inputStream;

	public abstract void start() throws Exception;
	
	public abstract void pause();

	public abstract void stop();

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
