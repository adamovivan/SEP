package bitcoinTest;

import static org.junit.Assert.assertTrue;

import authentificationPage.LoginPageAuthentification;
import bitcoinPage.BitcoinMarkPayment;
import bitcoinPage.BitcoinPage;
import bitcoinPage.BitcoinSuccessCancelPage;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import scientificPage.LoginPage;
import scientificPage.ViewMagazinPage;
import sun.rmi.runtime.Log;


public class BitcoinPaymentTest {
	
	private WebDriver browser; 
	
	BitcoinPage bitcoinPage;
	BitcoinMarkPayment bitcoinMarkPaymentPage;
	LoginPage scientificLoginPage;
	ViewMagazinPage viewMagazinPage;
	BitcoinSuccessCancelPage bitcoinSuccessCancelPage;
	LoginPageAuthentification loginPageAuthentification;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		browser = new ChromeDriver();
		browser.manage().window().maximize();
		browser.navigate().to("http://localhost:4200");


		//dodacemo jos stranicu za izbor caspoisa i kupovinu
		scientificLoginPage = PageFactory.initElements(browser, LoginPage.class);
		viewMagazinPage = PageFactory.initElements(browser, ViewMagazinPage.class);
		bitcoinPage = PageFactory.initElements(browser, BitcoinPage.class);
		bitcoinMarkPaymentPage = PageFactory.initElements(browser, BitcoinMarkPayment.class);
		bitcoinSuccessCancelPage = PageFactory.initElements(browser, BitcoinSuccessCancelPage.class);
		loginPageAuthentification = PageFactory.initElements(browser, LoginPageAuthentification.class);

		//prijavimo se na autentification server
		loginPageAuthentification.getSignOut().isDisplayed();
		loginPageAuthentification.getSignOut().click();

		loginPageAuthentification.loginLinkIsDisplay();
		loginPageAuthentification.getLoginLink().click();
		loginPageAuthentification.usernameFieldIsDisplay();
		loginPageAuthentification.setUsername("mikamikic");
		loginPageAuthentification.passwordFieldIsDisplay();
		loginPageAuthentification.setPassword("mika");

		browser.navigate().to("http://localhost:4300/login");
		scientificLoginPage.usernameFieldIsDisplayed();
		scientificLoginPage.setUsernameField("peraperic");
		scientificLoginPage.passwordFieldIsDisplayed();
		scientificLoginPage.setPasswordField("12345");
		scientificLoginPage.loginButtonIsDisplayed();
		scientificLoginPage.getLoginButton().click();
	}
	
	@Test
	public void test_bitcoinPayment_success() {

		viewMagazinPage.addButtonIsDisplay();
		assertTrue(browser.getCurrentUrl().equals("http://localhost:4300/magazines"));
		assertTrue(viewMagazinPage.getAddButton().isDisplayed());
		viewMagazinPage.getAddButton().click();

		assertTrue(viewMagazinPage.getShoppingCartButton().isDisplayed());
		viewMagazinPage.getShoppingCartButton().click();

		viewMagazinPage.proceedPaymentButtonIsDisplay();
		assertTrue(browser.getCurrentUrl().equals("http://localhost:4300/shopping-cart"));
		assertTrue(viewMagazinPage.getProceedPaymentButton().isDisplayed());
		viewMagazinPage.getProceedPaymentButton().click();
		/*
		bitcoinPage.bitcoinButtonIsDisplay();
		bitcoinPage.payButtonIsDisplay();
		assertTrue(browser.getCurrentUrl().startsWith("https://sandbox.coingate.com/invoice")); //mozemo da poredimo samo da li pocinje sa nekim url, zato sto ne znamo token 
		
		//izabrali smo placanje putem bitcoina
		assertTrue(bitcoinPage.getBitcoinButton().isDisplayed());
		bitcoinPage.getBitcoinButton().click();
		
		//kliknemo plati 
		assertTrue(bitcoinPage.getPayButton().isDisplayed());
		assertTrue(bitcoinPage.getPayButton().isEnabled());
		bitcoinPage.getPayButton().click();
		
		//odlazi na drugu stranicu gde treba da potvrdi da je placeno
		bitcoinMarkPaymentPage.bitcoinMarkPaidIsDisplay();
		bitcoinMarkPaymentPage.bitcoinMarkInvalidIsDisplay();
		assertTrue(browser.getCurrentUrl().startsWith("https://sandbox.coingate.com/invoice"));
		
		//kliknemo na mark as paid
		assertTrue(bitcoinMarkPaymentPage.getMarkPaidButton().isDisplayed());
		bitcoinMarkPaymentPage.getMarkPaidButton().click();
		
		//pojavi se stranica za potvrdjenu kupovinu
		bitcoinMarkPaymentPage.bitcoinMerchantBackIsDisplay();
		assertTrue(browser.getCurrentUrl().startsWith("https://sandbox.coingate.com/invoice"));
		
		//kliknemo za povratak na merchant-a
		assertTrue(bitcoinMarkPaymentPage.getBackToMerchantButton().isDisplayed());
		bitcoinMarkPaymentPage.getBackToMerchantButton().click();
		
		//sad treba da nas odvede na stranicu od bitcoin fronta
		//moramo malo da sacekamo 
		bitcoinSuccessCancelPage.completeButtonIsDisplay();
		assertTrue(bitcoinSuccessCancelPage.getCompleteButton().isDisplayed());
		assertTrue(browser.getCurrentUrl().startsWith("http://localhost:4202/success"));
		*/
	}
	
	@Test
	public void test_bitcoinPayment_cancel() {
		bitcoinPage.bitcoinButtonIsDisplay();
		bitcoinPage.payButtonIsDisplay();
		assertTrue(browser.getCurrentUrl().startsWith("https://sandbox.coingate.com/invoice")); //mozemo da poredimo samo da li pocinje sa nekim url, zato sto ne znamo token 
		
		//izabrali smo placanje putem bitcoina
		assertTrue(bitcoinPage.getBitcoinButton().isDisplayed());
		bitcoinPage.getBitcoinButton().click();
		
		//kliknemo plati 
		assertTrue(bitcoinPage.getPayButton().isDisplayed());
		assertTrue(bitcoinPage.getPayButton().isEnabled());
		bitcoinPage.getPayButton().click();
		
		//odlazi na drugu stranicu gde treba da potvrdi da je placeno
		bitcoinMarkPaymentPage.bitcoinMarkPaidIsDisplay();
		bitcoinMarkPaymentPage.bitcoinMarkInvalidIsDisplay();
		assertTrue(browser.getCurrentUrl().startsWith("https://sandbox.coingate.com/invoice"));
		
		//kliknemo na mark as paid
		assertTrue(bitcoinMarkPaymentPage.getMarkInvalidButton().isDisplayed());
		bitcoinMarkPaymentPage.getMarkInvalidButton().click();
		
		//pojavi se stranica za potvrdjenu kupovinu
		bitcoinMarkPaymentPage.bitcoinMerchantBackIsDisplay();
		assertTrue(browser.getCurrentUrl().startsWith("https://sandbox.coingate.com/invoice"));
		
		//kliknemo za povratak na merchant-a
		assertTrue(bitcoinMarkPaymentPage.getBackToMerchantButton().isDisplayed());
		bitcoinMarkPaymentPage.getBackToMerchantButton().click();
		
		//sad treba da nas odvede na stranicu od bitcoin fronta
		//moramo malo da sacekamo 
		bitcoinSuccessCancelPage.completeButtonIsDisplay();
		assertTrue(bitcoinSuccessCancelPage.getCompleteButton().isDisplayed());
		assertTrue(browser.getCurrentUrl().startsWith("http://localhost:4202/cancel"));
		
	}
}
