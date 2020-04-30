package com.automation.cruclub.rough;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeSuite;

import com.automation.cruclub.utilities.DriverFactory;
import com.automation.cruclub.utilities.DriverManager;

public class BaseTest {
	private WebDriver driver;
	private Properties config = new Properties();
	private FileInputStream fis;
	public Logger log = Logger.getLogger(BaseTest.class);

	@BeforeSuite
	public void setUpFramework() {
		configureLogging();
		DriverFactory.setGridPath("http://localhost:4444/wd/hub");
		DriverFactory.setConfigPropertyFile("src/test/resources/properties/Config.properties");

		if (System.getProperty("os.name").contains("Windows")) {
			DriverFactory.setCromeDriverExePath("src/test/resources/executables/chromedriver.exe");
			DriverFactory.setFirefoxDriverExePath("src/test/resources/executables/geckodriver.exe");
		} else if (System.getProperty("os.name").contains("Mac")) {
			DriverFactory.setCromeDriverExePath("src/test/resources/executables/chromedriver");
			DriverFactory.setFirefoxDriverExePath("src/test/resources/executables/geckodriver");
		}

		try {
			fis = new FileInputStream(DriverFactory.getConfigPropertyFile());
			config.load(fis);
			log.info("Configuration file are loaded");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void configureLogging() {
		String log4jConfigFile = "src/test/resources/properties/log4j.properties";
		PropertyConfigurator.configure(log4jConfigFile);
	}

	public void openBrowser(String browser) {
		System.out.println("Launching from: " + browser);

		DriverFactory.setIsRemote(false);

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
			if (browser.equals("chrome")) {
				System.setProperty("webdriver.chrome.driver", DriverFactory.getCromeDriverExePath());
				driver = new ChromeDriver();
				log.info("Chrome browser launched");
			} else if (browser.equals("firefox")) {
				System.setProperty("webdriver.gecko.driver", DriverFactory.getFirefoxDriverExePath());
				driver = new FirefoxDriver();
				log.info("Firefox browser launched");
			}
		}

		DriverManager.setDriver(driver);
		DriverManager.getDriver().manage().window().maximize();
		DriverManager.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void quit() {
		DriverManager.getDriver().quit();
	}
}
