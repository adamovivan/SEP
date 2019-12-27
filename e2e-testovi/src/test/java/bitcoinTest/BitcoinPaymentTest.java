package bitcoinTest;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import com.sep.e2etestovi.bitcoin.BitcoinMarkPayment;
import com.sep.e2etestovi.bitcoin.BitcoinPage;
import com.sep.e2etestovi.bitcoin.BitcoinSuccessCancelPage;
import com.sep.e2etestovi.bitcoin.ScientificPage;



public class BitcoinPaymentTest {
	
	private WebDriver browser; 
	
	BitcoinPage bitcoinPage; 
	BitcoinMarkPayment bitcoinMarkPaymentPage;
	ScientificPage choosenMagazinesPage;
	BitcoinSuccessCancelPage bitcoinSuccessCancelPage;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		browser = new ChromeDriver();
		browser.manage().window().maximize();
		browser.navigate().to("http://localhost:4300/magazines");
		
		//dodacemo jos stranicu za izbor caspoisa i kupovinu
		choosenMagazinesPage = PageFactory.initElements(browser, ScientificPage.class);
		bitcoinPage = PageFactory.initElements(browser, BitcoinPage.class);
		bitcoinMarkPaymentPage = PageFactory.initElements(browser, BitcoinMarkPayment.class);
		bitcoinSuccessCancelPage = PageFactory.initElements(browser, BitcoinSuccessCancelPage.class);
		
		//sad bi trebalo prvo da izaberemo neki casopis i da to prosledimo na pay
		
		//posle toga odlazi na stranicu za placanje 
		
	}
	
	@Test
	public void test_bitcoinPayment_success() {
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
