package com.automation.cruclub.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automation.cruclub.utilities.DriverManager;

public abstract class BasePage {

	protected WebDriver driver;

	public BasePage() {
		this.driver = DriverManager.getDriver();
	}

	public <T extends BasePage> T openPage(Class<T> clazz) {
		T page = null;
		driver = DriverManager.getDriver();
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 10);
		page = PageFactory.initElements(driver, clazz);
		PageFactory.initElements(factory, page);
		ExpectedCondition<WebElement> pageLoadCondition = page.getPageLoadCondition();
		waitForPageToLoad(pageLoadCondition);
		return page;
	}

	private void waitForPageToLoad(ExpectedCondition<WebElement> pageLoadCondition) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(pageLoadCondition);
	}

	protected abstract ExpectedCondition<WebElement> getPageLoadCondition();
}
