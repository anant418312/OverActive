package testUtilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import gherkin.lexer.No;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class Utlities {

	static WebDriver driver;
	public Utlities(WebDriver driver) {
		this.driver = driver;
	}
	/**
	 * 
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Object[][] CSVFileReader(String path) throws FileNotFoundException {
		Object data[][]=null;
		String[] SplitData = null;
		String[] SplitData2=null;
		try {
			int i=0;
			int j=0;
			File file = new File(path);
			Scanner inputStream = new Scanner(file);
			while(inputStream.hasNext()) {
				String rawData = inputStream.next();
				SplitData = rawData.split(",");
//				System.out.println(rawData+"*****");
				i++;
			}
//			System.out.println("No of rows are "+i);
//			System.out.println("No of columns are "+SplitData.length);
			inputStream.close();
			Scanner inputStream2 = new Scanner(file);
			data = new Object[i-1][SplitData.length];
			inputStream2.next();
			while(inputStream2.hasNext()) {
				String rawData = inputStream2.next();
				if(rawData.equals(null)||rawData.equals("")){
					break;
				}
				SplitData = rawData.split(",");
				data[j][0] = SplitData[0];
				data[j][1] = SplitData[1];
				data[j][2] = SplitData[2];
				data[j][3] = SplitData[3];
				data[j][4] = SplitData[4];
				data[j][5] = SplitData[5];
				j++;
			}

			inputStream2.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 
	 * @param fileName
	 * @param ValueToLookFor
	 * @return
	 * @throws IOException
	 */
	public static String ConfigReader(String fileName, String ValueToLookFor) throws IOException {
		String value = null;
		File file = new File(fileName);
		FileInputStream stream = new FileInputStream(file);
		Properties prop = new Properties();
		prop.load(stream);
		value = prop.getProperty(ValueToLookFor);
		stream.close();
		return value;
	}
	/**
	 * 
	 * @param Name
	 * @throws IOException
	 */
	public static void screenshot(String Name, WebDriver driver) throws IOException {
		TakesScreenshot scrShot =((TakesScreenshot)driver);
		File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile=new File("src//Screenshots//Screen"+Name+".jpeg");
		 FileUtils.copyFile(SrcFile, DestFile);
	}
}
