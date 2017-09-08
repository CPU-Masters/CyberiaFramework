package Cyberia.CyberiaFramework.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
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
	public ResultSet SimpleQuery(String query) {
		Statement stmt;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			handleError(e);
		}
	    
	    return rs;
	}
	
	/*
	 * ERROR HANDLING
	 */
	public void handleError(SQLException e) {
		e.printStackTrace();
	}
}
