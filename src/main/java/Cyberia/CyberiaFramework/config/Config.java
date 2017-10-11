package Cyberia.CyberiaFramework.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Cyberia.CyberiaFramework.debugging.CyberiaDebug;
import Cyberia.CyberiaFramework.util.CyberiaUtils;

public class Config {
	//This should be changed
	protected static String configName = "defaultConfig";

	public static final String CONFIG_BASE_LOCATION = System.getProperty("user.home") + "/";
	public static final String CONFIG_FILE_EXTENSION = ".cfg";

	public static final String ROOT_DOC_STRING = "config";

	public static final String SETTING_ELEMENT_STRING = "setting";

	public static ConfigStorage configStorage = new ConfigStorage();

	
	
	//Writing Config Files----------------------------------------------------------------------------------------------------------------
	
	public static void writeConfig() {

		BufferedWriter configWriter = null;
		try {
			String configLocation = getConfigPath();

			configWriter = new BufferedWriter(new FileWriter(configLocation));

			String xml = getConfigXML();

			configWriter.write(xml);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (configWriter != null) {
				try {
					configWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static String getConfigXML() {

		try {

			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			Element rootElement = doc.createElement(ROOT_DOC_STRING);
			doc.appendChild(rootElement);

			String xml = "";

			for (ConfigBasicSetting setting : configStorage.getConfigValues()) {
				Element settingElement = doc.createElement(SETTING_ELEMENT_STRING);
				settingElement = setting.populateElement(doc, settingElement);
				rootElement.appendChild(settingElement);
			}
			
			xml += CyberiaUtils.docToXmlText(doc);
			
			return xml;

		} catch (Exception e) {

			// failed to create XML
			CyberiaDebug.HandleException(e);
		}
		
		return null;
	}
	
	//End Writing Config Files----------------------------------------------------------------------------------------------------------------
	
	/**
	 * Attempts to read the config file if it exists.
	 * if it doesn't it writes the config file first.
	 */
	public static void readConfig() {
		try {
			String configLocation = getConfigPath();
			File configFile = new File(configLocation);
			
			if (!configFile.exists()) {
				writeConfig();
				configFile = new File(configLocation);
			}
			
			DocumentBuilderFactory dbFactory 
            = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(configFile);
			
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName(SETTING_ELEMENT_STRING);
			
			for (int i = 0; i<nList.getLength();i++) {
				Node n = nList.item(i);
				if (n.hasChildNodes()) {
					//loading code may become more complex
					ConfigBasicSetting configSetting = new ConfigBasicSetting();
					configSetting.loadFromNode(n);
					
					addOrUpdateBasicSetting(configSetting);
				}
			}
			
			
			
		} catch (Exception e) {
			CyberiaDebug.HandleException(e);
		}
		
	}
	
	
	

	//Utility functions
	public static String getConfigPath() {
		String configLocation = CONFIG_BASE_LOCATION + configName;
		// add cfg file extension if not present.
		if (!configName.contains(CONFIG_FILE_EXTENSION))
			configLocation += CONFIG_FILE_EXTENSION;
		
		return configLocation;
	}
	
	public static void addOrUpdateBasicSetting(ConfigBasicSetting configSetting) {
		configStorage.addConfigBasicSetting(configSetting);
	}
	
	/**
	 * Clears the config
	 */
	public static void clearConfig() {
		configStorage.configMap.clear();
	}
	//Change Settings
	/**
	 * Change the filename of a config.
	 * This needs to be done before creating Config Files
	 * or attempting to load a config file.
	 * @param configName the name of the config file, ex "myConfig"
	 */
	public static void setConfigName(String configName) {
		Config.configName = configName;
	}

}
