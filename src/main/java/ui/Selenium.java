package ui;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import abs.Constants;
import abs.MasterLogger;


public class Selenium{
    public static String highLightPropertyName = "outline";
    public static String highlightColor = "#00ff00 solid 3px";
    public WebDriver driver;
	public static Logger log = MasterLogger.getInstance();
	public static HashMap<String, String> env = new HashMap<String, String>();
	public static ExtentTest test;
	
	public Selenium(WebDriver driver, ExtentTest test) {
		this.driver=driver;
		this.test=test;
	}
    
    public static String getRandomText() {
    	String uuid = UUID.randomUUID().toString();
		return uuid.toString();
    	
    }
    public static By buildLocator(String xpath) throws Exception {
    	
		return By.xpath(xpath);
	}

	public WebElement findElement(String xpath) {
		if (driver != null) {
			WebElement element=driver.findElement(By.xpath(xpath));
			highlightElement(element);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			return this.driver.findElement(By.xpath(xpath));
		} else
			return null;
	}

	

	public void highlightElement(WebElement element) {
		if (highLightPropertyName.trim().isEmpty() || highlightColor.trim().isEmpty())
			return;

		try {
			JavascriptExecutor jsDriver = ((JavascriptExecutor) driver);

			// Retrieve current background color
			// String propertyName = "border"; //"outline";
			// propertyName = "outline";
			String originalColor = "none";
			// String highlightColor = "#00ff00 solid 3px";

			try {
				// This works with internet explorer
				originalColor = jsDriver
						.executeScript("return arguments[0].currentStyle." + highLightPropertyName, element).toString();
			} catch (Exception e) {

				// This works with firefox, chrome and possibly others
				originalColor = jsDriver.executeScript("return arguments[0].style." + highLightPropertyName, element)
						.toString();
			}

			try {
				jsDriver.executeScript("arguments[0].style." + highLightPropertyName + " = '" + highlightColor + "'",
						element);
				Thread.sleep(50);

				jsDriver.executeScript("arguments[0].style." + highLightPropertyName + " = '" + originalColor + "'",
						element);
				Thread.sleep(50);

				jsDriver.executeScript("arguments[0].style." + highLightPropertyName + " = '" + highlightColor + "'",
						element);
				Thread.sleep(50);

				jsDriver.executeScript("arguments[0].style." + highLightPropertyName + " = '" + originalColor + "'",
						element);
			} catch (Exception e2) {
				log.error("error in highlight function " + e2.getMessage());
			}

		} catch (Exception e) {
			log.error("error in highlight function " + e.getMessage());
		}
	}

	public void takeSnapShot(LogStatus... s) {
		LogStatus status;
		if(s==null || s.length==0) {
			status=LogStatus.INFO;
		}else {
			status=s[0];
		}
		String fileWithPath = Constants.SNAPSHOT_PATH + "/"
				+ getRandomText() + ".png";
		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
		File destFile = new File(fileWithPath);
		
		try {
			FileUtils.copyFile(srcFile, destFile);
			test.log(status,test.addScreenCapture(destFile.getAbsolutePath()));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public boolean isTextPresentInPage(String text2Search) {
		return driver.getPageSource().contains(text2Search);

	}
	
	public boolean isElementPresent(String xpath) {
		List<WebElement> element=driver.findElements(By.xpath(xpath));
		return (element.size()>0);
	}

	public void selectByOption(String xpath, String data) {
		WebElement element=findElement(xpath);
		try {
			Select sel = new Select(element);
			highlightElement(element);
			sel.selectByVisibleText(data);
			takeSnapShot();
			log.info("select (by option)" + data + " from the dropdown");
			test.log(LogStatus.INFO, "select (by option)" + data + " from the dropdown");
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			log.error("Webelement selection " + e.getMessage());
			test.log(LogStatus.ERROR, "Webelement selection " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			test.log(LogStatus.ERROR, "Webelement selection " + e.getMessage());
		}

	}

	public void selectByIndex(String xpath, int index) {
		WebElement element=findElement(xpath);
		try {
			Select sel = new Select(element);
			highlightElement(element);
			sel.selectByIndex(index);
			takeSnapShot();
			log.info("select (by index)" + index + " from the dropdown");
			test.log(LogStatus.INFO, "select (by index)" + index + " from the dropdown");

		} catch (Exception e) {
			log.error("Exception occured while select by Index " + e.getMessage());
			test.log(LogStatus.ERROR, "Exception occured while select by Index " + e.getMessage());
		}
	}

	public void selectByValue(String xpath, String value) {
		WebElement element=findElement(xpath);
		try {
			Select sel = new Select(element);
			highlightElement(element);
			sel.selectByValue(value);
			takeSnapShot();
			log.info("select (by value)" + value + " from the dropdown");
			test.log(LogStatus.INFO, "select (by value)" + value + " from the dropdown");

		} catch (Exception e) {
			log.error("Exception occured while select by Value " + e.getMessage());
			test.log(LogStatus.ERROR, "Exception occured while select by Value " + e.getMessage());

		}
	}

	public void sendKeys(String xpath, String data) {
		WebElement element=findElement(xpath);
		try {
			log.info("type " + data + " in input field.");
			highlightElement(element);
			element.sendKeys(data);
			takeSnapShot();		
		} catch (Exception e) {
			log.error("error while type in text field " + e.getMessage());
			test.log(LogStatus.ERROR, "error while type in text field " + e.getMessage());

		}
	}
	
	public void click(String xpath ) {
		try {
		WebElement element=findElement(xpath);
		highlightElement(element);
		element.click();
		takeSnapShot();
		log.info("clicked on webelement "+xpath);
		test.log(LogStatus.INFO, "clicked on webelement "+xpath);
		} catch (Exception e) {
			log.error("error while clicking on element " + xpath);
			e.printStackTrace();
			test.log(LogStatus.ERROR, "error while clicking on element "+xpath);
		}

	}

	public String getTitle() {

		return driver.getTitle();
	}
	
	public void switchToDefaultContent() {

		driver.switchTo().defaultContent();
	}

	public void maximiseWindow() {

		driver.manage().window().maximize();
	}

	// pop up handling

	public void acceptAlert() {

		driver.switchTo().alert().accept();
	}

	public void dismissAlert() {

		driver.switchTo().alert().dismiss();
	}

	public String getAlertText() {

		return driver.switchTo().alert().getText();
	}

	// handlinf frames

	public void switchToFrame(int frame) {
		try {
			driver.switchTo().frame(frame);
			log.info(("frame  navigation through id " + frame));
			test.log(LogStatus.INFO, "clicked on webelement "+frame);
		} catch (NoSuchFrameException e) {
			log.error(("Unable to locate frame with id " + frame + e.getStackTrace()));
			test.log(LogStatus.ERROR, ("Unable to locate frame with id " + frame + e.getStackTrace()));
		} 
	}

	public void switchToFrame(String xpath) {
		try {
			driver.switchTo().frame(findElement(xpath));
			log.info(("frame  navigation through id " + xpath));
			test.log(LogStatus.INFO, "clicked on webelement "+xpath);
		} catch (NoSuchFrameException e) {
			log.error(("Unable to locate frame with id " + xpath + e.getStackTrace()));
			test.log(LogStatus.ERROR, ("Unable to locate frame with id " + xpath + e.getStackTrace()));
		}
	}

	public void switchToFrame(String ParentFrame, String ChildFrame) {
		try {
			driver.switchTo().frame(ParentFrame).switchTo().frame(ChildFrame);
			log.info(("Navigated to innerframe with id " + ChildFrame + "which is present on frame with id"
					+ ParentFrame));
		} catch (NoSuchFrameException e) {
			log.error(("Unable to locate frame with id " + ParentFrame + " or " + ChildFrame + e.getStackTrace()));
			test.log(LogStatus.ERROR, ("Unable to locate frame with id " + ParentFrame + " or " + ChildFrame + e.getStackTrace()));

		} 
	}

	public void switchtoDefaultFrame() {
		try {
			driver.switchTo().defaultContent();
			log.info(("Navigated back to webpage from frame"));
		} catch (Exception e) {
			log.error(("unable to navigate back to main webpage from frame" + e.getStackTrace()));
			test.log(LogStatus.ERROR, ("unable to navigate back to main webpage from frame" + e.getStackTrace()));

		}
	}

	// quit driver

	public void quitDriver() {

		try {
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeDriver() {

		try {
			driver.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void checkCheckBox(String xpath) {
		WebElement we=findElement(xpath);
		if (!we.isSelected()) {
			we.click();
		}
	}

	public void uncheckCheckBox(String xpath) {
		WebElement we=findElement(xpath);
		if (we.isSelected()) {
			we.click();
		}
	}

}
