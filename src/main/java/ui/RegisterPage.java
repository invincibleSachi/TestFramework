package ui;

import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;

import common.PropertyUtils;
import pojo.UserRegisterationData;

public class RegisterPage extends PageObject{
	
	WebDriver driver=null;
	Selenium selenium;
	Properties locator;
	ExtentTest test;
	public RegisterPage(WebDriver driver, ExtentTest test) {
		super(driver);
		this.driver=driver;
		this.test=test;
		locator=PropertyUtils.getLocaltors(this.getClass());
		selenium=new Selenium(driver,test);
		// TODO Auto-generated constructor stub
	}
	
	public WebDriver registration(UserRegisterationData data) {
		
		selenium.click(locator.getProperty("signUpBtn"));
		selenium.switchToFrame(locator.getProperty("iframe"));
		if(!data.getFirstName().trim().equals("")) {
			selenium.sendKeys(locator.getProperty("fName"), data.getFirstName());
		}
		
		if(!data.getLastName().trim().equals("")) {
			selenium.sendKeys(locator.getProperty("lName"), data.getLastName());
		}
		
		if(!data.getEmail().trim().equals("")) {
			selenium.sendKeys(locator.getProperty("email"), data.getEmail());
		}
		
		if(!data.getPassword().trim().equals("")) {
			selenium.sendKeys(locator.getProperty("password"), data.getPassword());
		}
		
		if(!data.getConfirmPassword().trim().equals("")) {
			selenium.sendKeys(locator.getProperty("cpassword"), data.getConfirmPassword());
		}
		selenium.click(locator.getProperty("pdCheck"));
		selenium.click(locator.getProperty("touCheck"));
		selenium.click(locator.getProperty("registerBtn"));
		return driver ;
	}
	public boolean isRegistrationSuccess() {
		return selenium.isElementPresent(locator.getProperty("successMsg"));
	}
	

}
