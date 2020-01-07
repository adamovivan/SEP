package bitcoinPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BitcoinMarkPayment {
	
	private WebDriver driver;

	@FindBy(xpath = "(//button[@class='ant-btn ant-btn-dashed'])[1]")
	private WebElement markPaidButton; 
	
	@FindBy(xpath="(//button[@class='ant-btn ant-btn-dashed'])[2]")
	private WebElement markInvalidButton; 
	
	@FindBy(xpath="//div/span/button")
	private WebElement backToMerchantButton;
	
	
	

	public BitcoinMarkPayment(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getMarkPaidButton() {
		return markPaidButton;
	}

	public void setMarkPaidButton(WebElement markPaidButton) {
		this.markPaidButton = markPaidButton;
	}

	public WebElement getMarkInvalidButton() {
		return markInvalidButton;
	}

	public void setMarkInvalidButton(WebElement markInvalidButton) {
		this.markInvalidButton = markInvalidButton;
	}

	public WebElement getBackToMerchantButton() {
		return backToMerchantButton;
	}

	public void setBackToMerchantButton(WebElement backToMerchantButton) {
		this.backToMerchantButton = backToMerchantButton;
	} 
	
	public void bitcoinMarkPaidIsDisplay() {
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(markPaidButton));
	}
	
	public void bitcoinMarkInvalidIsDisplay() {
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(markInvalidButton));
	}
	
	public void bitcoinMerchantBackIsDisplay() {
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(backToMerchantButton));
	}
	
	
}
