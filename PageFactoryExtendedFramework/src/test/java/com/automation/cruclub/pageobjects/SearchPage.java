package com.automation.cruclub.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchPage extends BasePage {

	public SearchPage minPrice() {
		sortDrop.click();
		minPriceDrop.click();
		return this;
	}

	@FindBy(id = "drpSortBy")
	public WebElement sortDrop;
	@FindBy(xpath = "//a[@data-sbv='price']")
	public WebElement minPriceDrop;

	@Override
	protected ExpectedCondition<WebElement> getPageLoadCondition() {
		return ExpectedConditions.visibilityOf(sortDrop);
	}
}
