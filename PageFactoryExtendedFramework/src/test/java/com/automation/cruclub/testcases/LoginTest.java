package com.automation.cruclub.testcases;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.automation.cruclub.pageobjects.HomePage;
import com.automation.cruclub.utilities.Constants;
import com.automation.cruclub.utilities.DataProviders;
import com.automation.cruclub.utilities.DataUtil;
import com.automation.cruclub.utilities.ExcelReader;

public class LoginTest extends BaseTestWebDriverManager {

	@Test(dataProviderClass = DataProviders.class, dataProvider = "masterDP")
	public void loginTest(HashMap<String, String> data) throws InterruptedException {
		ExcelReader excel = new ExcelReader(Constants.SUITE1_XL_PATH);
		DataUtil.checkExecution("master", "LoginTest", data.get("Runmode"), excel);

		openBrowser(data.get("browser"));
		logExtentReports("Launched browser: " + data.get("browser"));

		HomePage home = new HomePage().open("https://www.cruclub.ru/");
		Thread.sleep(2000);
		home.login(data.get("userName"), data.get("password"));
		quit();
	}

	@AfterMethod
	public void cleanUp() {
		logExtentReports("Login Test completed");
		quit();
	}
}
