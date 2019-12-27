package com.sep.e2etestovi.bitcoin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BitcoinPage {
	private WebDriver driver; 
	
	@FindBy(xpath="(//div[@class='currency-card'])[1]")
	private WebElement bitcoinButton; 
	
	@FindBy(xpath="//button[@id='invoice-checkout-button']")
	private WebElement payButton;

	public BitcoinPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getBitcoinButton() {
		return bitcoinButton;
	}

	public void setBitcoinButton(WebElement bitcoinButton) {
		this.bitcoinButton = bitcoinButton;
	}

	public WebElement getPayButton() {
		return payButton;
	}

	public void setPayButton(WebElement payButton) {
		this.payButton = payButton;
	} 
	
	
	public void bitcoinButtonIsDisplay() {
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(bitcoinButton));
	}
	
	public void payButtonIsDisplay() {
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(payButton));
	}
	
}
