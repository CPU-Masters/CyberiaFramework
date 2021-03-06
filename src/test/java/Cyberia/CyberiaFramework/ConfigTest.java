package Cyberia.CyberiaFramework;

import Cyberia.CyberiaFramework.config.Config;
import Cyberia.CyberiaFramework.config.ConfigBasicSetting;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ConfigTest extends TestCase {

	ConfigBasicSetting setting1 = new ConfigBasicSetting("setting 1", "A Value");
	ConfigBasicSetting setting2 = new ConfigBasicSetting("setting 2", "Fire Wind");
	
	public ConfigTest() {
		Config.configStorage.addConfigBasicSetting(setting1);
		Config.configStorage.addConfigBasicSetting(setting2);
		Config.setConfigName("unitTestConfig");
	}
	
	public static Test suite()
    {
        return new TestSuite( ConfigTest.class );
    }
	
	
	public void testConfigCreation() {
		
		String xmlOut = Config.getConfigXML();
		
		System.out.println(xmlOut);
		
		assertEquals(
				xmlOut.contains((String) setting1.getSettingValue())
				, true);
		
		
		
	}
	
	public void testConfigFileCreation() {

		
		Config.writeConfig();
		
	}
	
	public void testConfigRead() {
		//just in case config has not been written
		Config.writeConfig();
		
		Config.clearConfig();
		
		Config.readConfig();
		
		System.out.println(Config.configStorage.getHumanReadableConfig());
		
		assertEquals(Config.configStorage.getConfigValues().size() >=2, true);
		
	}
}
