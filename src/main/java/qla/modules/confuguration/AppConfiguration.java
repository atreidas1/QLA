package qla.modules.confuguration;

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
	private static String CONFIG_FOLDER = System.getProperty("config.folder");
	private static String SETTINGS_FILE = CONFIG_FOLDER + "\\config.properties";
	
	public static void init(){
		try {
			properties = new Properties();
			System.out.println("Loading settings file:"+SETTINGS_FILE);
			properties.load(new FileReader(SETTINGS_FILE));
			System.out.println("Loading settings beans from file:" + "beans.xml");
			ApplicationContext context = new FileSystemXmlApplicationContext(CONFIG_FOLDER + "\\beans.xml");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
	
	public static String getProperty(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
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
