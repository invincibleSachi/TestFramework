package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import abs.Constants;

public class PropertyUtils {
	
	public static Properties readProperties(String fileName) {
		Properties prop=new Properties();
		try {
			File f=new File(fileName);
			FileInputStream fis = new FileInputStream(f);
			prop.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	public static Properties getLocaltors(Class c) {
		Properties prop=new Properties();
		try {
			File f=new File(Constants.LOCATORS_PATH+c.getSimpleName()+".properties");
			FileInputStream fis = new FileInputStream(f);
			prop.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

}