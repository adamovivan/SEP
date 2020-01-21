package authentificationPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChoosenPayment {

    private WebDriver driver;

    @FindBy(xpath = "(//div/label/input)[2]")
    private WebElement bitcoinRadioButton;

    @FindBy(xpath = "//button")
    private WebElement continueButton;

    public ChoosenPayment(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getBitcoinRadioButton() {
        return bitcoinRadioButton;
    }

    public WebElement getContinueButton() {
        return continueButton;
    }

    public void continueButtonIsDisplay() {
        (new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(continueButton));
    }

    public void bitcoinButtonIsDisplay() {
        (new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(bitcoinRadioButton));
    }
}
