package Cyberia.CyberiaFramework.database.storage;

import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.mysql.cj.jdbc.PreparedStatement;

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
		//TODO add support for modifying a table schema
		
		//if the table exists, check to make sure it has all the proper columns
		//if not add the column
	}
	public String getInsertStatement() {
		String fields = "(";
		//Must be an ordered collection for this to work
		fields += databaseFields.values().stream().map(x -> x.getInsertFields()).reduce((i,j) -> i + "," + j);
		fields += ") values (";
		
		fields += databaseFields.values().stream().map(x -> "?").reduce((i,j) -> i + "," + j);
		fields += ")";
		return fields;
	}
	public void store() {
		PreparedStatement stmt = getDatabaseConnection().genPreparedStatement(getTableName(),this);
		int i = 0;
		for (DatabaseField<?> df : databaseFields.values()) {
			i++;
			try {
				stmt.setObject(i, df.getDbValue());
			} catch (SQLException e) {
				DatabaseConnection.handleError(e);
			}
		}

		
	}
}
