package Cyberia.CyberiaFramework;

import Cyberia.CyberiaFramework.config.Config;
import Cyberia.CyberiaFramework.database.DatabaseConnection;
import Cyberia.CyberiaFramework.database.storage.DatabaseField;
import Cyberia.CyberiaFramework.database.storage.DatabaseObject;
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
		
		//attempt to store an object
		
		DatabaseTestObj testobj1 = new DatabaseTestObj();
		testobj1.dbConnection = dbConnection;
		testobj1.genTable();
		
		testobj1.name.set("x");
		
		testobj1.store();
				
		dbConnection.disconnect();
		
		assert(true);
	}
	
	public class DatabaseTestObj extends DatabaseObject {
		public  DatabaseConnection dbConnection;
		public DatabaseField<String> name = new DatabaseField<String>("testName", DatabaseField.VAR_CHAR, "", this, true);
		@Override
		public String getTableName() {
			return "dbTestObj";
		}

		@Override
		public DatabaseConnection getDatabaseConnection() {
			return dbConnection;
		}
		
	}
}
