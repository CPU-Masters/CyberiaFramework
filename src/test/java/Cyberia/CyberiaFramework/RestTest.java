package Cyberia.CyberiaFramework;

import Cyberia.CyberiaFramework.config.Config;
import Cyberia.CyberiaFramework.config.ConfigBasicSetting;
import Cyberia.CyberiaFramework.rest.RestClient;
import junit.framework.TestCase;

public class RestTest extends TestCase {
	
	public void testAuthLogin() {
		ConfigBasicSetting userSetting = new ConfigBasicSetting("User","User");
		
		//Used for unit testing
		ConfigBasicSetting passSetting = new ConfigBasicSetting("Pass","Pass");
		
		Config.configStorage.addConfigBasicSetting(userSetting);
		Config.configStorage.addConfigBasicSetting(passSetting);
		
		Config.setConfigName("RestTestCreds");
		Config.readConfig();
		
		String user = (String)Config.configStorage.configMap.get("User").getSettingValue();
		String pass = (String)Config.configStorage.configMap.get("Pass").getSettingValue();
		
		
		RestClient rc = new RestClient();
		rc.authenticate("https://api.robinhood.com/api-token-auth/", user, pass);
	}
}
