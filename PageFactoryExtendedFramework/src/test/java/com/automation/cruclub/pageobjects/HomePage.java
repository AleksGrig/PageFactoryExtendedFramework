package com.automation.cruclub.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.automation.cruclub.utilities.DriverManager;

public class HomePage extends BasePage {

	public HomePage open(String url) {
		DriverManager.getDriver().navigate().to(url);
		return openPage(HomePage.class);
	}

	public SearchPage gotoSearchPage() {
		submitCruiseOptionsButton.click();
		return openPage(SearchPage.class);
	}

	public void login(String userName, String password) {
		userNameField.sendKeys(userName);
		passwordField.sendKeys(password);
		// loginButton.click();
	}

	// @FindBys ({ @FindBy, @FindBy}) - finding element inside another element
	@FindAll({ @FindBy(name = "ctl24$Login$UserName"), @FindBy(id = "ctl24_Login_UserName") })
	public WebElement userNameField;
	@FindBy(id = "ctl24_Login_Password")
	public WebElement passwordField;
	@FindBy(id = "ctl24_Login_LoginButton")
	public WebElement loginButton;
	@FindBy(id = "ddRegion_link")
	public WebElement regionLink;
	@FindBy(id = "rpDuration")
	public WebElement numberOfDaysLink;
	@FindBy(css = ".ui-slider-handle.ui-state-default.ui-corner-all")
	public WebElement daySlider;
	@FindBy(id = "ddRegion_li_1")
	public WebElement MediterreneanOption;
	@FindBy(id = "btnSubmit")
	public WebElement submitCruiseOptionsButton;
	@FindBy(css = "span[class='linkselect-link-text']")
	public List<WebElement> cruiseSearchOptions;

	@Override
	protected ExpectedCondition<WebElement> getPageLoadCondition() {
		return ExpectedConditions.visibilityOf(submitCruiseOptionsButton);
	}
}
