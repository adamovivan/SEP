package authentificationPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPageAuthentification {

	private WebDriver driver; 
	
	@FindBy(xpath="//a[@href='/login']")
	private WebElement loginLink; 
	
	@FindBy(xpath="//input[@formcontrolname='username']")
	private WebElement usernameField;
	
	@FindBy(xpath="//input[@formcontrolname='password']")
	private WebElement passwordField; 
	
	@FindBy(xpath="(//div[@class='form-group']/button)[1]")
	private WebElement signInButton;

	@FindBy(xpath = "//a[3]")
	private  WebElement signOut;

	public LoginPageAuthentification(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getLoginLink() {
		return loginLink;
	}

	public void setLoginLink(WebElement loginLink) {
		this.loginLink = loginLink;
	}

	public WebElement getUsernameField() {
		return usernameField;
	}


	public WebElement getPasswordField() {
		return passwordField;
	}
	public void setPasswordField(WebElement passwordField) {
		this.passwordField = passwordField;
	}

	public WebElement getSignInButton() {
		return signInButton;
	}

	public WebElement getSignOut() {
		return signOut;
	}

	public void loginLinkIsDisplay() {
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(loginLink));
	}
	
	public void loginButtonIsDisplay() {
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(signInButton));
	}
	
	public void usernameFieldIsDisplay() {
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(usernameField));
	}
	public void passwordFieldIsDisplay() {
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(passwordField));
	}
	public void signOutIsDisplay() {
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(signOut));
	}
	public void setPassword(String password) {
		passwordField.clear();
		passwordField.sendKeys(password);
	}
	
	public void setUsername(String username) {
		usernameField.clear();
		usernameField.sendKeys(username);
	}
	
}
