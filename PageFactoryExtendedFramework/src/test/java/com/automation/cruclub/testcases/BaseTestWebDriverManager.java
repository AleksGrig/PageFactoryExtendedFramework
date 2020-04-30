package com.automation.cruclub.testcases;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.grid.selenium.GridLauncherV3;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeSuite;

import com.automation.cruclub.listeners.ExtentListeners;
import com.automation.cruclub.utilities.DriverFactory;
import com.automation.cruclub.utilities.DriverManager;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTestWebDriverManager {
	private WebDriver driver;
	private Properties config = new Properties();
	private FileInputStream fis;
	protected Logger log = Logger.getLogger(this.getClass());
	private boolean isGrid = false;

	@BeforeSuite
	public void setUpFramework() throws InterruptedException {
		configureLogging();
		DriverFactory.setGridPath("http://localhost:4444/wd/hub");
		DriverFactory.setConfigPropertyFile("src/test/resources/properties/Config.properties");

		try {
			fis = new FileInputStream(DriverFactory.getConfigPropertyFile());
			config.load(fis);
			log.info("Configuration file are loaded");
		} catch (IOException e) {
			e.printStackTrace();
		}

		WebDriverManager.chromedriver().setup();
		WebDriverManager.firefoxdriver().setup();

		if (System.getenv("ExecutionType") != null && System.getenv("ExecutionType").equals("Grid")) {
			isGrid = true;
		}
		DriverFactory.setIsRemote(isGrid);

		if (DriverFactory.getIsRemote()) {
			StartHub();
			RegisterChrome();
			RegisterFirefox();
		}
	}

	protected static void logExtentReports(String message) {
		Markup m = MarkupHelper.createLabel(message, ExtentColor.LIME);
		ExtentListeners.testReport.get().info(m);
	}

	private static void StartHub() {
		GridLauncherV3.main(new String[] { "-role", "hub", "-port", "4444" });
	}

	private static void RegisterChrome() {
		GridLauncherV3.main(new String[] { "-role", "node", "-hub", "http://localhost:4444/grid/register", "-browser",
				"browserName=chrome, maxInstances=5", "-port", "5555" });
	}

	private static void RegisterFirefox() {
		GridLauncherV3.main(new String[] { "-role", "node", "-hub", "http://localhost:4444/grid/register", "-browser",
				"browserName=firefox, maxInstances=5", "-port", "5556" });
	}

	private static void configureLogging() {
		String log4jConfigFile = "src/test/resources/properties/log4j.properties";
		PropertyConfigurator.configure(log4jConfigFile);
	}

	private WebDriver getBrowserDriver(String browserName) {

		WebDriver driver;

		switch (browserName.toLowerCase()) {
		case "chrome":
		default:
			driver = new ChromeDriver();
			log.info("Chrome browser launched");
			break;
		case "firefox":
			driver = new FirefoxDriver();
			log.info("Firefox browser launched");
			break;
		}
		return driver;
	}

	protected void openBrowser(String browser) {
		if (DriverFactory.getIsRemote()) {
			DesiredCapabilities capabilities = null;
			if (browser.equals("firefox")) {
				capabilities = DesiredCapabilities.firefox();
				capabilities.setBrowserName("firefox");
				capabilities.setPlatform(Platform.ANY);
			} else if (browser.equals("chrome")) {
				capabilities = DesiredCapabilities.chrome();
				capabilities.setBrowserName("chrome");
				capabilities.setPlatform(Platform.ANY);
			}

			try {
				driver = new RemoteWebDriver(new URL(DriverFactory.getGridPath()), capabilities);
				log.info("Starting session on Grid");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else {
			driver = getBrowserDriver(browser);
		}

		DriverManager.setDriver(driver);
		DriverManager.getDriver().manage().window().maximize();
		DriverManager.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	protected void quit() {
		DriverManager.getDriver().quit();
	}
}
