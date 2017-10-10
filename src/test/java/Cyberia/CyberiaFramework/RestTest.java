package Cyberia.CyberiaFramework;

import Cyberia.CyberiaFramework.config.Config;
import Cyberia.CyberiaFramework.config.ConfigBasicSetting;
import Cyberia.CyberiaFramework.rest.RestClient;
import junit.framework.TestCase;

public class RestTest extends TestCase {
	
	public void testAuthLogin() {
		ConfigBasicSetting userSetting = new ConfigBasicSetting("User");
		//Used for unit testing
		ConfigBasicSetting passSetting = new ConfigBasicSetting("Pass");
		
		Config.configStorage.addConfigBasicSetting(userSetting);
		Config.configStorage.addConfigBasicSetting(passSetting);
		
		Config.setConfigName("RestTestCreds.cfg");
		Config.readConfig();
		
		
		
		RestClient rc = new RestClient();
		rc.authenticate("https://api.robinhood.com/api-token-auth/", "user", "pass");
	}
}
