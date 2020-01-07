package authentification;

import authentificationPage.LoginPageAuthentification;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertTrue;

public class AuthentificationLoginTest {
    private WebDriver browser;

    LoginPageAuthentification loginPageAuthentification;

    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        browser =  new ChromeDriver();
        browser.manage().window();
        browser.navigate().to("http://localhost:4200");

        loginPageAuthentification = PageFactory.initElements(browser, LoginPageAuthentification.class);
    }

    @Test
    public void authentificationLoginTest() {

        loginPageAuthentification.getSignOut().isDisplayed();
        loginPageAuthentification.getSignOut().click();

        loginPageAuthentification.loginLinkIsDisplay();
        assertTrue(loginPageAuthentification.getLoginLink().isDisplayed());
        loginPageAuthentification.getLoginLink().click();

        loginPageAuthentification.loginButtonIsDisplay();
        assertTrue(browser.getCurrentUrl().equals("http://localhost:4200/login"));
        assertTrue(loginPageAuthentification.getUsernameField().isDisplayed());
        loginPageAuthentification.setUsername("mikamikic");
        assertTrue(loginPageAuthentification.getPasswordField().isDisplayed());
        loginPageAuthentification.setPassword("mika");

        assertTrue(loginPageAuthentification.getSignInButton().isDisplayed());
        loginPageAuthentification.getSignInButton().click();

        loginPageAuthentification.signOutIsDisplay();
        assertTrue(browser.getCurrentUrl().equals("http://localhost:4200/"));
        assertTrue(loginPageAuthentification.getSignOut().isDisplayed());
        loginPageAuthentification.getSignOut().click();

    }

    @After
    public void cleanBrowser(){
        browser.close();
    }
}
