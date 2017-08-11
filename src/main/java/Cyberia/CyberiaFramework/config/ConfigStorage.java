package Cyberia.CyberiaFramework.config;

import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * Holds Config Settings. Can be used for default configs or loaded configs.
 * @author josh.benton
 *
 */
public class ConfigStorage {
	
	public LinkedHashMap<String, ConfigBasicSetting> configMap = new LinkedHashMap<String, ConfigBasicSetting>();
	
	public void addConfigBasicSetting(ConfigBasicSetting setting) {
		configMap.put(setting.settingName, setting);
	}
	
	public Object getSettingValue(String settingName) {
		ConfigBasicSetting cbs;
		cbs = configMap.get(settingName);
		return cbs.getSettingValue();
	}
	
	public Collection<ConfigBasicSetting> getConfigValues() {
		return configMap.values();
	}
	
}
