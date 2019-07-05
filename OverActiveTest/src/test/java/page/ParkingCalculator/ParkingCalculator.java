package page.ParkingCalculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import testUtilities.Utlities;

public class ParkingCalculator {

//	String LotType;
//	String InTime;
//	String InDate;
//	String OutTime;
//	String OutDate;
//	String ActualCost;
	public static WebDriver driver;
	WebDriverWait waiter;

//	ParkingCalculator(String LotType, String InTime, String InDate, String OutTime){
	public ParkingCalculator(WebDriver driver) {
		this.driver = driver;
	}

	public void QuitDriver() {
		driver.quit();
	}
	@FindBy(id = "Lot")
	WebElement DDN_ChooseALot;

	@FindBy(id = "EntryTime")
	WebElement Edt_InTimeofParking;

	@FindBy(name = "EntryTimeAMPM")
	List<WebElement> Rdg_InTimeAMPM;

	@FindBy(id = "EntryDate")
	WebElement Edt_InDateOfParking;

	@FindBy(id = "ExitTime")
	WebElement Edt_OutTimeofParking;

	@FindBy(name = "ExitTimeAMPM")
	List<WebElement> Rdg_OutTimeAMPM;

	@FindBy(id = "ExitDate")
	WebElement Edt_OutDateOfParking;

	@FindBy(xpath = "//input[@type='submit' and @name='Submit' and @value='Calculate']")
	WebElement Btn_Calculate;

	@FindBy(xpath = "//b[contains(text(), '$')]")
	WebElement Elm_Cost;

	public void browserLauncher(String browser) {
		if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "src\\ThirdParty\\geckodriver.exe");
			driver = new FirefoxDriver();
			PageFactory.initElements(this.driver, this);
		} 
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "src\\ThirdParty\\chromedriver.exe");
			driver = new ChromeDriver();
			PageFactory.initElements(this.driver, this);
		} 
		driver.manage().window().maximize();
		System.out.println("Launched "+browser);
	}

	public void LaunchURL(String url) {
		driver.get(url);
		System.out.println("Launched URL");
	}

	/**
	 * 
	 * @param LotType
	 * @param InTime
	 * @param InDate
	 * @param OutTime
	 * @param OutDate
	 * @throws IOException 
	 */
	public void setValues(String LotType, String InTime, String InDate, String OutTime, String OutDate) throws IOException {
		
		//Selecting the value from dropdown
		Select ChooseALot = new Select(DDN_ChooseALot);
		if (LotType.contains("Short")) {
			ChooseALot.selectByValue("STP");
			System.out.println("Short Term Parking was selected");
		} else if (LotType.contains("Garage")) {
			ChooseALot.selectByValue("LTG");
			System.out.println("Long Term Garage Parking was selected");
		} else if (LotType.contains("Surface")) {
			ChooseALot.selectByValue("LTS");
			System.out.println("Long Term Surface Parking was selected");
		}

		// Setting  In-time
		Edt_InTimeofParking.clear();
		Edt_InTimeofParking.sendKeys(InTime.substring(0, 5)); 
		
		// Setting in-time AM PM
		for (int i = 0; i < Rdg_InTimeAMPM.size(); i++) {
			String valueToSelect = Rdg_InTimeAMPM.get(i).getAttribute("value");
			if (valueToSelect.contentEquals(InTime.substring(5))) {
				Rdg_InTimeAMPM.get(i).click(); 
				System.out.println("In-time was seleted");
			}
		}
		
		// Setting  In-date
		Edt_InDateOfParking.clear();
		Edt_InDateOfParking.sendKeys(InDate); 
		System.out.println("In-date was entered");
		
		//Setting Out-time
		Edt_OutTimeofParking.clear();
		Edt_OutTimeofParking.sendKeys(OutTime.substring(0, 5));
		
		// Setting out-time AM PM
		for (int i = 0; i < Rdg_OutTimeAMPM.size(); i++) {
			String valueToSelect = Rdg_OutTimeAMPM.get(i).getAttribute("value");
			if (valueToSelect.contentEquals(OutTime.substring(5))) {
				Rdg_OutTimeAMPM.get(i).click(); 
				System.out.println("Out-time was seleted");
			}
		}
		
		// Setting  In-date
		Edt_OutDateOfParking.clear();
		Edt_OutDateOfParking.sendKeys(OutDate);
		System.out.println("Out-date was entered");
		Utlities.screenshot("Data Entered for "+LotType+" lot type", driver);
	}

	/**
	 * 
	 * @param ExpectedCost
	 */
	public void costCalculatorAndValidator(String ExpectedCost) {
		Btn_Calculate.click();
		waiter = new WebDriverWait(driver, 10);	
		WebElement ActualCostInApp = waiter.until(ExpectedConditions.visibilityOf(Elm_Cost));
		String ActualCost = ActualCostInApp.getText().substring(2);
		System.out.println("Calculate was clicked");
//		Double ActualCostFromApp = Double.parseDouble(ActualCostFromAppRaw);
//		Double ExpectedCostChanged = Double.parseDouble(ExpectedCost);
		assertThat(ActualCost).isEqualTo(ExpectedCost);
		System.out.println("Actual cost is equal to expected");
	}

}
