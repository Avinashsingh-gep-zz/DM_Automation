package dm.testtier.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.google.common.io.Files;
import com.vimalselvam.cucumber.listener.Reporter;

import cucumber.api.Scenario;

public class PageElement {
	
	/**
	 * This method will take screenshot and embed to HTML report .
	 *
	 * @param driver
	 * @param scenario
	 */
			
	public static void takeEmbeedScreenshot(Scenario scenario)
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Date date = new Date();
		String base64Screenshot = null;
		if (scenario.isFailed()) {
			String screenshotName = scenario.getName().replaceAll(" ", "_").concat(dateFormat.format(date).toString());
			try {
				File sourcePath = ((TakesScreenshot) ScenarioContext.getContext(Keys.WebDriver)).getScreenshotAs(OutputType.FILE);
				String Destpath = System.getProperty("user.dir") + "/target/cucumber-reports/screenshots/";
				File directoryScreenshots = new File(Destpath);
				directoryScreenshots.mkdir();	
				File destinationPath = new File(Destpath + screenshotName + ".png");
				Files.copy(sourcePath, destinationPath);
				base64Screenshot = ((TakesScreenshot) ScenarioContext.getContext(Keys.WebDriver)).getScreenshotAs(OutputType.BASE64);                     
	            FileOutputStream out = new FileOutputStream(destinationPath);
	            byte[] decodeScreenshot = Base64.decodeBase64(base64Screenshot.getBytes());
	            out.write(decodeScreenshot);
	            scenario.embed(decodeScreenshot, "image/png");
	            out.close();
				Reporter.addScreenCaptureFromPath(destinationPath.toString());
				String s = "<img src=data:image/gif;base64,"+base64Screenshot+" alt=Base64 encoded image width=670 height=430/>";
				Reporter.addStepLog(s);
			} catch (IOException e) {
			}
		}
		Reporter.loadXMLConfig(PropertyReader.getReportConfigPath());
	}

}
