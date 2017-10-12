package Cyberia.CyberiaFramework.rest.robinhood;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;

import Cyberia.CyberiaFramework.debugging.CyberiaDebug;
import Cyberia.CyberiaFramework.rest.RestClient;
import Cyberia.CyberiaFramework.util.codeGen.CodeGen;

public class RobinhoodAccount {
	public static final String AUTH_URL = "https://api.robinhood.com/api-token-auth/";
	public String username,password;
	public String authToken;
	
	/**
	 * JSON style token authentication for robinhood
	 * @param user
	 * @param pass
	 */
	public void authenticate(final String user,final String pass) {
		try {
		URL url = new URL(""+AUTH_URL);
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
	
	public void authenticate() {
		authenticate(username, password);
	}
	
	public void loadAccountDetails() {
		JsonObject jsonObj = Json.createReader(RestClient.authGetRequest("https://api.robinhood.com/accounts/",authToken)).readObject();
		
		//make a java object from the json
		
		CodeGen.genClassForJson(jsonObj);
		
		
	}
}
