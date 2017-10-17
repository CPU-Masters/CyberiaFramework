package Cyberia.CyberiaFramework.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
	private static final String INFORMATION_SCHEMA_TABLES = "information_schema.tables";
	public String databaseURL = "localhost/test?";
	public String databaseName = "test";
	public String databaseUser = "admin";
	public String databasePass = "password";
	
	Connection conn = null;
	
	public void connect() {
	
	
	try {
	    conn =
	       DriverManager.getConnection("jdbc:mysql://"+databaseURL + "/" + databaseName + "?"+
	                                   "user="+databaseUser+"&password="+databasePass);
	    
	    // Do something with the Connection

	} catch (SQLException ex) {
	    // handle any errors
	    System.out.println("SQLException: " + ex.getMessage());
	    System.out.println("SQLState: " + ex.getSQLState());
	    System.out.println("VendorError: " + ex.getErrorCode());
	    handleError(ex);
	}
	}
	
	public void disconnect() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			handleError(e);
		}
	}
	
	/**
	 * Execute a select query.
	 * @param query - the select query
	 * @return The ResultSet or null
	 */
	public ResultSet simpleQuery(String query) {
		Statement stmt;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			handleError(e);
		}
	    
	    return rs;
	}
	public void simpleExecute(String statement) {
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(statement);
		} catch (SQLException e) {
			handleError(e);
		}
	}
	
	public boolean containsTable(String tableName) {
		String query = "select * from " + INFORMATION_SCHEMA_TABLES;
		query += " where table_schema = '" + databaseName + "' AND ";
		query += " table_name = '" + tableName + "'";
		query += " Limit 1";
		boolean last;
		try {
			last = simpleQuery(query).last();
		} catch (SQLException e) {
			handleError(e);
			last = false;
		}
		return last;
	}
	/*
	 * ERROR HANDLING
	 */
	public void handleError(SQLException e) {
		e.printStackTrace();
	}




}
