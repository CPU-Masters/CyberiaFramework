package Cyberia.CyberiaFramework.database.storage;

import java.util.LinkedHashMap;

import Cyberia.CyberiaFramework.database.DatabaseConnection;

public abstract class DatabaseObject {
	public LinkedHashMap<String,DatabaseField<?>> databaseFields = new LinkedHashMap<>();
	public void addDatabaseField(DatabaseField<?> d) {
		databaseFields.put(d.dbName, d);
	}
	
	public abstract String getTableName();
	public abstract DatabaseConnection getDatabaseConnection();
	/**
	 * Attempts to generate a table if one doesn't already exist for this
	 * Database Object
	 */
	public void genTable() {
		//check if table exists
		if (!getDatabaseConnection().containsTable(getTableName())) {
		//if the table doesn't exist, create it
		if (databaseFields.size() > 0) {
			String tableCreationString = "Create Table ";
			tableCreationString += getTableName();
			// add variables
			databaseFields.values().stream()
			.map(x -> x.getTableCreationString())
			.reduce((i,j) -> i + "," + j);
			
			getDatabaseConnection().simpleExecute(tableCreationString);
			
		}
		
		}
		
		//if the table exists, check to make sure it has all the proper columns
		//if not add the column
	}
}
