package Cyberia.CyberiaFramework;

import Cyberia.CyberiaFramework.config.Config;
import Cyberia.CyberiaFramework.config.ConfigBasicSetting;
import junit.framework.TestCase;

public class ConfigTest extends TestCase {

	public void ConfigCreationTest() {
		ConfigBasicSetting setting1 = new ConfigBasicSetting("setting 1", "A Value");
		ConfigBasicSetting setting2 = new ConfigBasicSetting("setting 2", "Fire Wind");
		
		Config.configStorage.addConfigBasicSetting(setting1);
		Config.configStorage.addConfigBasicSetting(setting2);
		
		String xmlOut = Config.getConfigXML();
		
		System.out.println(xmlOut);
		
		assertEquals(
				xmlOut.contains((String) setting1.getSettingValue())
				, true);
		
		
		
	}
}
