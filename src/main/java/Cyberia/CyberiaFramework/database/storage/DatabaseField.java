package Cyberia.CyberiaFramework.database.storage;

import java.sql.SQLException;

import com.mysql.cj.jdbc.PreparedStatement;

import Cyberia.CyberiaFramework.debugging.CyberiaDebug;

/**
 * Class that holds a field for a database object.
 * 
 * @author Inhaler
 *
 * @param <T>
 *            The Java type
 */
public class DatabaseField<T> {

	String dbName;
	String mySqlType;
	T defaultValue;
	DatabaseObject dbObject;
	//ACTUAL VALUE
	T value;
	
	
	/**
	 * Creates a field that becomes a column in a database
	 * 
	 * Directly specifies the MySql Data type. (No automatic detection)
	 * @param dbName
	 * @param mySqlType
	 * @param defaultValue
	 * @param dbObject
	 */
	public DatabaseField(String dbName, String mySqlType, T defaultValue, DatabaseObject dbObject) {
		this.dbName = dbName;
		this.mySqlType = mySqlType;
		this.defaultValue = defaultValue;
		this.dbObject = dbObject;
		this.value = defaultValue;
		
		dbObject.addDatabaseField(this);
		
	}
	
	/* ----------------------------------------------------------------------------------------
	 * SQL Generation
	 * ----------------------------------------------------------------------------------------
	 */
	public String getTableCreationString() {
		return dbName + " " + mySqlType;
	}
	
	public PreparedStatement getTableInsertionPreparedStatement(int i,PreparedStatement statement) {
		try {
			/*
		if (value.getClass().isInstance(Integer.class)){
			statement.setInt(i, (Integer)value);
		} else if (value.getClass().isInstance(java.sql.Date.class)){
			statement.setDate(i, (java.sql.Date) value);
		} else {
		*/
			statement.setObject(i, value);
			
			return statement;
			//This should just be a warning if it converts to string
			//throw new UnsupportedOperationException("Class Type: " + value.getClass() + " is not supported by Cyberia Framework");
		
		//}
		
		} catch (SQLException e) {
			CyberiaDebug.HandleException(e);
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
