package abs;

import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import common.PropertyUtils;

public abstract class AbstractTest {
    public static HashMap<String, String> environment = new HashMap<String, String>();
    public static Logger log = MasterLogger.getInstance();
    public static Properties config=PropertyUtils.readProperties("config.properties");
    public static String os =System.getProperty("os.name");
    ExtentReports reports = new ExtentReports(Constants.EXTENT_REPORT, true);
    protected ExtentTest test = reports.startTest("SAP CONV AI TESTS");
    public static void loadEnv(String env) {


    }
    
    public WebDriver getDriver() {
    	WebDriver driver = null;
    	if(config.getProperty("browser").equalsIgnoreCase("chrome")) {
    		if(os.equalsIgnoreCase("windows")) {
    			System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER_WIN);
    		}else {
    			System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER);
    		}
    	    driver = new ChromeDriver();
    	    driver.manage().window().maximize();
    	    driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
    	    
    	}
    	driver.get(config.getProperty("url"));
    	test.log(LogStatus.INFO, "opened application:: "+config.getProperty("url"));
    	return driver;
    }
    
    @BeforeSuite
    public void beforeTestGlobal() {
    	ExtentTest test = reports.startTest("Test Starts");
    }
    
    @AfterSuite
    public void afterTest() {
    	reports.endTest(test);
    	reports.flush();
    }


}
