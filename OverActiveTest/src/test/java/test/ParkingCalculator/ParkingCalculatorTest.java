package test.ParkingCalculator;


import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import page.ParkingCalculator.ParkingCalculator;
import testUtilities.Utlities;

public class ParkingCalculatorTest {
	WebDriver driver;
	String appURL;
	String browser;
	ParkingCalculator parker = new ParkingCalculator(driver);
	@DataProvider(name = "ParkingCalculator")
	public Object[][] dataProviderMethod() throws FileNotFoundException{
		String path = "src\\Test Data\\testdata.csv";
		Object[][] Object = Utlities.CSVFileReader(path);
		return Object;
	}
	
	@BeforeTest
	public void LaunchBrowserAndUrl() throws IOException {
		try {
			appURL = Utlities.ConfigReader("src\\Test Data\\configuration.properties", "url");
			browser = Utlities.ConfigReader("src\\Test Data\\configuration.properties", "browser");
			parker.browserLauncher(browser);
			parker.LaunchURL(appURL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test(dataProvider="ParkingCalculator")
	public void ParkingPriceCalculatorTest(String Lot, String InTime, String OutTime, String InDate, String OutDate, String ExpectedCost) {
		try {
//			ParkingCalculator parker = PageFactory.initElements(driver, ParkingCalculator.class);
			parker.setValues(Lot, InTime, InDate, OutTime, OutDate);
			parker.costCalculatorAndValidator(ExpectedCost);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@AfterTest
	public void QuitDriver() {
		parker.QuitDriver();
	}
}
