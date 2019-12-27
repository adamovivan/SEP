package com.sep.e2etestovi.bitcoin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BitcoinSuccessCancelPage {
	
	private WebDriver driver; 
	
	@FindBy(xpath="//button")
	private WebElement completeButton;

	public BitcoinSuccessCancelPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getCompleteButton() {
		return completeButton;
	}

	public void setCompleteButton(WebElement completeButton) {
		this.completeButton = completeButton;
	} 
	

	public void completeButtonIsDisplay() {
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(completeButton));
	}
	

}
