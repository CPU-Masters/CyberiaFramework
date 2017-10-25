package Cyberia.CyberiaFramework.database.storage;

import java.sql.SQLException;

import com.mysql.cj.jdbc.PreparedStatement;

import Cyberia.CyberiaFramework.DatabaseTest.DatabaseTestObj;
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

	public static final String VAR_CHAR = "varchar(255)";
	
	
	String dbName;
	String mySqlType;
	T defaultValue;
	DatabaseObject dbObject;
	//ACTUAL VALUE
	T value;
	boolean primaryKey = false;
	
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
	
	
	public DatabaseField(String dbName, String mySqlType, T defaultValue,DatabaseTestObj dbObject, boolean primaryKey) {
		this.dbName = dbName;
		this.mySqlType = mySqlType;
		this.defaultValue = defaultValue;
		this.dbObject = dbObject;
		this.value = defaultValue;
		
		this.primaryKey = primaryKey;
		
		dbObject.addDatabaseField(this);
	}


	/* ----------------------------------------------------------------------------------------
	 * SQL Generation
	 * ----------------------------------------------------------------------------------------
	 */
	public String getTableCreationString() {
		String rtrn = dbName + " " + mySqlType;
		if (primaryKey)
			rtrn += " NOT NULL";
		return rtrn;
		
	}
	public String getInsertFields() {
		return dbName;
	}

	/* ----------------------------------------------------------------------------------------
	 * Getters / Setters
	 * ----------------------------------------------------------------------------------------
	 */
	/**
	 * Gets the database version of the object
	 * by default this is the same object,
	 * but if you needed to do some sort of conversion
	 * you would override this method.
	 * @return value
	 */
	public Object getDbValue() {
		return value;
	}

	public void set(T val) {
		this.value = val;
	}
	
}
