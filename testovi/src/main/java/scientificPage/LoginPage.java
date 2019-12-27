package scientificPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private WebDriver driver;

    @FindBy(xpath="//input[@name='username']")
    private WebElement usernameField;

    @FindBy(xpath="//input[@name='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//button")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }


    public WebElement getUsernameField() {
        return usernameField;
    }

    public WebElement getPasswordField() {
        return passwordField;
    }

    public WebElement getLoginButton() {
        return loginButton;
    }

    public void loginButtonIsDisplayed(){
        (new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(loginButton));
    }

    public void usernameFieldIsDisplayed(){
        (new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(usernameField));
    }

    public void passwordFieldIsDisplayed(){
        (new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(passwordField));
    }

    public void setUsernameField(String username){
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    public void setPasswordField(String password){
        passwordField.clear();
        passwordField.sendKeys(password);
    }
}
