package ui;

import java.util.HashMap;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import abs.MasterLogger;

public class PageObject {
	public WebDriver driver;
	public static Logger log = MasterLogger.getInstance();
	public static HashMap<String, String> env = new HashMap<String, String>();

	public PageObject(WebDriver driver) {
		this.driver = driver;
		}

	public PageObject(RemoteWebDriver driver) {
		this.driver = driver;
	}
	
	

}
