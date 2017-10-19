package qla.modules.confuguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;


public class AppConfiguration {
	private static Properties properties;
	private static Properties systemProperties;
	public static final String BASE_FOLDER = System.getProperty("base.folder");
	private static String CONFIG_FOLDER = BASE_FOLDER + File.separator + "config";
	private static String SETTINGS_FILE = CONFIG_FOLDER + File.separator + "config.properties";
	private static String SYSTEM_CONFIG = CONFIG_FOLDER + File.separator + "system.properties";
	
	public static void init(){
		try {
			properties = new Properties();
			systemProperties = new Properties();
			System.out.println("Loading settings file:"+SETTINGS_FILE);
			properties.load(new FileReader(SETTINGS_FILE));
			System.out.println("Loading settings beans from file:" + "beans.xml");
			ApplicationContext context = new FileSystemXmlApplicationContext(CONFIG_FOLDER + File.separator+"beans.xml");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	private static String _getProperty(String key) {
		String value =  properties.getProperty(key);
		if(value == null ) {
			value = systemProperties.getProperty(key);
		}
		return value;
	}
	
	public static String getProperty(String key) {
		return _getProperty(key);
	}
	
	public static String getProperty(String key, String defaultValue) {
		String value = _getProperty(key);
		return value == null ? defaultValue : value;
	}
	
	public static void setProperty(String key, String value) {
		properties.setProperty(key, value);
	}
	
	public static void save(){
		try {
			properties.store(new PrintWriter(SETTINGS_FILE), "Application configs");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateProperty (String key, String value) {
		properties.setProperty(key, value);
		save();
	}
	
	@SuppressWarnings("unchecked")
	public static Enumeration<String> propertyNames() {
		return (Enumeration<String>) properties.propertyNames();
	}
}
