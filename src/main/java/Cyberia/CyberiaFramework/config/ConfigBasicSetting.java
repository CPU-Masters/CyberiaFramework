package Cyberia.CyberiaFramework.config;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ConfigBasicSetting {

	
	public static final String SETTING_NAME_TAG = "Name";
	public static final String SETTING_VALUE_TAG = "Value";
	
	protected String settingName,settingValue;
	
	
	/**
	 * Create a setting without a setting name.
	 */
	public ConfigBasicSetting() {
	}
	
	/**
	 * Create a setting with a setting name.
	 * @param settingName
	 */
	public ConfigBasicSetting(String settingName) {
		this.settingName = settingName;
		
	}
	
	public ConfigBasicSetting(String settingName,String settingValue) {
		this.settingName = settingName;
		this.settingValue = settingValue;
	}

	public Element populateElement(Document doc, Element settingElement) {
		
		Element settingName = doc.createElement(SETTING_NAME_TAG);
		Element settingValue = doc.createElement(SETTING_VALUE_TAG);
		
		settingName.appendChild(doc.createTextNode(this.settingName));
		settingValue.appendChild(doc.createTextNode(this.settingValue));
		
		settingElement.appendChild(settingName);
		settingElement.appendChild(settingValue);
		
		return settingElement;
	}

	public Object getSettingValue() {
		// TODO Auto-generated method stub
		return settingValue;
	}
	
	
}
