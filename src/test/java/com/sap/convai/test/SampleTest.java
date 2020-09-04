package com.sap.convai.test;

import static org.testng.Assert.assertEquals;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import abs.AbstractTest;
import common.ExcelUtils;

import pojo.UserRegisterationData;
import ui.RegisterPage;

public class SampleTest extends AbstractTest{
	
	@Test(description="Register into Conversational AI Platform",dataProvider="userRegData")
	public void register(String description, UserRegisterationData data) {
		WebDriver driver=getDriver();
		RegisterPage registerPage=new RegisterPage(driver,test);
		registerPage.registration(data);
		boolean isSuccess=registerPage.isRegistrationSuccess();
		if(data.isRegistrationSuccess() == isSuccess ){
			test.log(LogStatus.PASS, "Registration status is same");
		}else {
			test.log(LogStatus.FAIL, "Registration status is not as expected");
		}
		driver.close();
		assertEquals(data.isRegistrationSuccess(),isSuccess, "Expected and actual registration status match");

	}
	
	@DataProvider(name="userRegData")
	public Object[][] getData(){
		
		JSONArray arr=ExcelUtils.readExcelAsJSON("data.xlsx", "Sheet1");
		Object[][] obj=new Object[arr.length()][2];
		UserRegisterationData data;
		for(int i=0;i<arr.length();i++) {
			JSONObject o=arr.getJSONObject(i);
			obj[i][0]=o.get("TC_Description");
			data=new UserRegisterationData();
			data.setEmail(o.getString("Email"));
			data.setFirstName(o.getString("First_Name"));
			data.setLastName(o.getString("Last_Name"));
			data.setPassword(o.getString("Password"));
			data.setConfirmPassword(o.getString("Confirm_Password"));
			if(o.getString("is_positive").equalsIgnoreCase("TRUE")) {
				data.setRegistrationSuccess(true);
			}else {
				data.setRegistrationSuccess(false);
			}
			obj[i][1]=data;
		}
		return obj;
	}
	@AfterMethod
	public void cleanup() {
		
	}

}
