package Cyberia.CyberiaFramework.debugging;

import java.sql.SQLException;

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
	
	public static void HandleSQLException(SQLException e) {
		//Hard Error, stops exception
		e.printStackTrace();
	}
	
	public static void HandleRestError(String error) {
		System.out.println(error);
	}

	public static void output(String output) {
		System.out.println(output);
		
	}
}
