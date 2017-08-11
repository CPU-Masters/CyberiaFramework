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

	
	
	public static void writeConfig() {

		BufferedWriter configWriter = null;
		try {
			String configLocation = CONFIG_BASE_LOCATION + configName;
			// add cfg file extension if not present.
			if (!configName.contains(CONFIG_FILE_EXTENSION))
				configLocation += CONFIG_FILE_EXTENSION;

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
