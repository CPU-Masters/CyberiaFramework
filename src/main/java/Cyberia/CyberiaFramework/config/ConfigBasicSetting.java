package Cyberia.CyberiaFramework.config;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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

	//OVERRIDE THESE METHODS when making more complex settings.
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

	public void loadFromNode(Node n) {
		
		NodeList nList = n.getChildNodes();
		
		for (int i = 0; i<nList.getLength();i++) {
			Node node = nList.item(i);
			//Case statement based on node name
			switch (node.getNodeName()) {
			
			case (SETTING_NAME_TAG):
				this.settingName = node.getTextContent();
				break;
			case (SETTING_VALUE_TAG):
				this.settingValue = node.getTextContent();
				
			}
			
		}
	}

	public String getHumanReadableSetting() {
		return this.settingName + " : " + this.settingValue;
	}
	
	
	
	//End of override required methods
	
	
}
