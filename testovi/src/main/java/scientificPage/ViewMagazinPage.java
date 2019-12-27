package scientificPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ViewMagazinPage {
    private WebDriver driver;

    @FindBy(xpath = "(//div/mat-table/tr)[2]/td/button")
    private WebElement addButton;

    @FindBy(xpath = "//app-magazine/button")
    private WebElement shoppingCartButton;

    @FindBy(xpath = "//div/button")
    private WebElement proceedPaymentButton;

    public ViewMagazinPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getAddButton() {
        return addButton;
    }

    public WebElement getShoppingCartButton() {
        return shoppingCartButton;
    }

    public WebElement getProceedPaymentButton() {
        return proceedPaymentButton;
    }

    public void addButtonIsDisplay() {
        (new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(addButton));
    }

    public void shoopingCartButtonIsDisplay() {
        (new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(shoppingCartButton));
    }

    public void proceedPaymentButtonIsDisplay() {
        (new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(proceedPaymentButton));
    }


}
