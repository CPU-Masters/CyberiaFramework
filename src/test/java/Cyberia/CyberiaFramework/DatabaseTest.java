package Cyberia.CyberiaFramework;

import Cyberia.CyberiaFramework.config.Config;
import Cyberia.CyberiaFramework.database.DatabaseConnection;
import junit.framework.TestCase;

public class DatabaseTest extends TestCase {
	
	/**
	 * Determines if the MySQL database connection worked
	 * as intended.
	 */
	public void testLocalMySqlConnection() {
		//Create a connection
		DatabaseConnection dbConnection = new DatabaseConnection();
		dbConnection.databaseURL = "localhost";
		dbConnection.databaseName = "test";
		dbConnection.databaseUser = "UnitTester";//Account must be created before this test can pass.
		dbConnection.databasePass = "password";
		
		dbConnection.connect();
		
		//assertEquals();
		
		
		
	}
}
