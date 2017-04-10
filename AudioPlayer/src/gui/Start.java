/**
 * 
 */
package gui;

import control.Controller;
import dos.Client;

/**
 * @author Zykoq
 * 
 */
public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
            
                Client c=new Client();
                c.doAction();                
		Controller.getInstance();
                
	}
}
