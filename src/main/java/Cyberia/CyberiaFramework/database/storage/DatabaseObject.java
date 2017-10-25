package Cyberia.CyberiaFramework.database.storage;

import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.mysql.cj.jdbc.PreparedStatement;

import Cyberia.CyberiaFramework.database.DatabaseConnection;
import Cyberia.CyberiaFramework.debugging.CyberiaDebug;

public abstract class DatabaseObject {
	
	//TODO primary key support
	public String primaryKey;
	
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
			
			String varCreation = "("+ databaseFields.values().stream()
			.map(x -> x.getTableCreationString())
			.reduce((i,j) -> i + "," + j).get();
			
			
			if (databaseFields.values().stream().filter(x -> x.primaryKey==true).count() >= 2) {
				//multiple primary keys
				varCreation += ",CONSTRAINT PK_Gen_"+this.getTableName()+" PRIMARY KEY ("+databaseFields.values().stream().filter(x -> x.primaryKey==true).map(x -> x.dbName).reduce((i,j) -> i + ","+j).get() + ")";
			} else if (databaseFields.values().stream().filter(x -> x.primaryKey==true).count() >= 1) {
				varCreation += ", PRIMARY KEY ("+databaseFields.values().stream().filter(x -> x.primaryKey==true).map(x -> x.dbName).reduce((i,j) -> i + ","+j).get() + ")";
			}
			varCreation += ")";
			tableCreationString += varCreation;
			
			CyberiaDebug.output(tableCreationString);
			
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
		fields += databaseFields.values().stream().map(x -> x.getInsertFields()).reduce((i,j) -> i + "," + j).get();
		fields += ") values (";
		
		fields += databaseFields.values().stream().map(x -> "?").reduce((i,j) -> i + "," + j).get();
		fields += ")";
		return fields;
	}
	public void store() {
		java.sql.PreparedStatement stmt = getDatabaseConnection().genPreparedStatement(getTableName(),this);
		int i = 0;
		try {
		for (DatabaseField<?> df : databaseFields.values()) {
			i++;
			stmt.setObject(i, df.getDbValue());
		}
		
		stmt.execute();

		} catch (SQLException e) {
			DatabaseConnection.handleError(e);
		}
	}
}
