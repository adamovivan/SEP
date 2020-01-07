package scientific;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import scientificPage.LoginPage;
import scientificPage.ViewMagazinPage;

import static org.junit.Assert.assertTrue;

public class LoginAndChoosenMagazineTest {
    private WebDriver browser;

    LoginPage scientificLoginPage;
    ViewMagazinPage viewMagazinPage;

    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        browser =  new ChromeDriver();
        browser.manage().window();
        browser.navigate().to("http://localhost:4300/login");

        scientificLoginPage = PageFactory.initElements(browser, LoginPage.class);
        viewMagazinPage = PageFactory.initElements(browser, ViewMagazinPage.class);
    }

    @Test
    public void loginTest(){
        scientificLoginPage.loginButtonIsDisplayed();
        scientificLoginPage.usernameFieldIsDisplayed();
        scientificLoginPage.passwordFieldIsDisplayed();

        assertTrue(scientificLoginPage.getUsernameField().isDisplayed());
        scientificLoginPage.setUsernameField("peraperic");

        assertTrue(scientificLoginPage.getPasswordField().isDisplayed());
        scientificLoginPage.setPasswordField("12345");

        assertTrue(scientificLoginPage.getLoginButton().isDisplayed());
        scientificLoginPage.getLoginButton().click();


        viewMagazinPage.addButtonIsDisplay();
        viewMagazinPage.proceedPaymentButtonIsDisplay();
        assertTrue(browser.getCurrentUrl().equals("http://localhost:4300/magazines"));
        assertTrue(viewMagazinPage.getAddButton().isDisplayed());
        viewMagazinPage.getAddButton().click();
        assertTrue(viewMagazinPage.getShoppingCartButton().isDisplayed());
        viewMagazinPage.getShoppingCartButton().click();

        viewMagazinPage.proceedPaymentButtonIsDisplay();
        assertTrue(browser.getCurrentUrl().equals("http://localhost:4300/shopping-cart"));
        assertTrue(viewMagazinPage.getProceedPaymentButton().isDisplayed());
        viewMagazinPage.getProceedPaymentButton().click();

    }

}
