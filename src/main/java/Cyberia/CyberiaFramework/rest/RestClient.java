package Cyberia.CyberiaFramework.rest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import javax.json.*;
import javax.json.stream.JsonParser;

import Cyberia.CyberiaFramework.debugging.CyberiaDebug;

public class RestClient {
	
	
	public String username,password,authUrl;
	public String authToken;
	
	/**
	 * JSON style token authentication
	 * @param urlString
	 * @param user
	 * @param pass
	 */
	public void authenticate(String urlString,final String user,final String pass) {
		try {
		URL url = new URL(""+urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Accept", "application/json");
		conn.setDoOutput(true);


		//add body of post
		String userPass = "username="+user+"&"+"password="+pass;
		
		conn.setRequestProperty("Content-Length", Integer.toString(userPass.length()));
		conn.getOutputStream().write(userPass.getBytes());
		
		 
		CyberiaDebug.output("Response code: " + conn.getResponseCode());
		CyberiaDebug.output("Response code: " + conn.getResponseMessage());
		//store url / user / pass for future authentications (?)
		this.authUrl = urlString;
		this.username = user;
		this.password = pass;
		//parse auth token
		String lToken = Json.createReader(conn.getInputStream()).readObject().getString("token");
		
		this.authToken = lToken;
		
		CyberiaDebug.output("Auth token: " + this.authToken);
		
		} catch (Exception e) {
			CyberiaDebug.HandleException(e);
		}
	}

	/**
	 * Simple get request based on a url.
	 * Uses authorization from authenticate
	 * @param urlString
	 */
	public InputStream authGetRequest(String urlString) {
		try {
		URL url = new URL(""+urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Authorization", "Token " +this.authToken);
		conn.setRequestProperty("Accept", "application/json");
		
		
		if (conn.getResponseCode()!=200) {
			//failed to make a connection
			CyberiaDebug.HandleRestError("authGetRequest got response : " + conn.getResponseMessage());
			return null;
		}
		
		return conn.getInputStream();
		} catch (Exception e) {
			CyberiaDebug.HandleException(e);
		}
		return null;
	}
	

	//Static methods
	/**
	 * Simple static get request. useable for services without 
	 * @param urlString
	 * @return
	 */
	public static BufferedReader requestGetReader(String urlString) {
		
		try {
		URL url = new URL(""+urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		
		if (conn.getResponseCode() != 200) {
			CyberiaDebug.HandleRestError("Failed : HTTP error code : "
					+ conn.getResponseCode());
			return null;
		}
		
		return new BufferedReader(new InputStreamReader((conn.getInputStream())));
		} catch (Exception e) {
			CyberiaDebug.HandleException(e);
		}
		return null;
		
	}
	
	public static InputStream authGetRequest(String urlString,String authToken) {
		try {
		URL url = new URL(""+urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Authorization", "Token " + authToken);
		conn.setRequestProperty("Accept", "application/json");
		
		
		if (conn.getResponseCode()!=200) {
			//failed to make a connection
			CyberiaDebug.HandleRestError("authGetRequest got response : " + conn.getResponseMessage());
			return null;
		}
		
		return conn.getInputStream();
		} catch (Exception e) {
			CyberiaDebug.HandleException(e);
		}
		return null;
	}
}
