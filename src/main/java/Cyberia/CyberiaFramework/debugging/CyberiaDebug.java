package Cyberia.CyberiaFramework.debugging;

public class CyberiaDebug {

	/**
	 * The standard Cyberia Debug Excpetion handling.
	 * Change this to determine what happens when exceptions
	 * occur.
	 * @param e
	 */
	public static void HandleException(Exception e) {
		//Hard Error, stops exception
		e.printStackTrace();
	}
}
