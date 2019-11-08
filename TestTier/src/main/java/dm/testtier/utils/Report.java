package dm.testtier.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.google.common.io.Files;
import com.vimalselvam.cucumber.listener.ExtentProperties;
import com.vimalselvam.cucumber.listener.Reporter;

import cucumber.api.Scenario;

public class Report {

	/**
	 * This method will take screenshot and embed to HTML report .
	 *
	 * @param driver
	 * @param scenario
	 */

	private static String reportPrefix = "DM_ShouldCost_Report_";
	private static String systemPath = System.getProperty("user.dir");
	private static String reportGeneratePath = systemPath + "/target/cucumber-reports/Reports/";
	private static String reportScreenshotPath = systemPath + "/target/cucumber-reports/screenshots/";

	public Report () {
		String reportName = reportPrefix + getDateStamp();
		ExtentProperties extentProperties = ExtentProperties.INSTANCE;
		extentProperties.setReportPath(reportGeneratePath + reportName + ".html");
	}

	public void takeEmbeedScreenshot(Scenario scenario) {
		String base64Screenshot = null;
		if (scenario.isFailed()) {
			String screenshotName = scenario.getName().replaceAll(" ", "_").concat(getDateStamp());
			try {
				File sourcePath = ((TakesScreenshot) ScenarioContext.getContext(Keys.WebDriver)).getScreenshotAs(OutputType.FILE);
				File directoryScreenshots = new File(reportScreenshotPath);
				directoryScreenshots.mkdir();	
				File destinationPath = new File(reportScreenshotPath + screenshotName + ".png");
				Files.copy(sourcePath, destinationPath);
				base64Screenshot = ((TakesScreenshot) ScenarioContext.getContext(Keys.WebDriver)).getScreenshotAs(OutputType.BASE64);                     
				FileOutputStream out = new FileOutputStream(destinationPath);
				byte[] decodeScreenshot = Base64.decodeBase64(base64Screenshot.getBytes());
				out.write(decodeScreenshot);
				scenario.embed(decodeScreenshot, "image/png");
				out.close();
				Reporter.addScreenCaptureFromPath(destinationPath.toString());
				String s = "<img src=data:image/gif;base64," + base64Screenshot + " alt=Base64 encoded image width=670 height=430/>";
				Reporter.addStepLog(s);
				Reporter.loadXMLConfig(PropertyReader.getReportConfigPath());
			} catch (IOException e) {
			}
		}
	}

	private String getDateStamp() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Date date = new Date();
		return dateFormat.format(date).toString();
	}
	
	public static void updateStatusToTestRail() {

        int status = 0;
        String caseID = null;
        String runID = null;
		Scenario scenario = (Scenario) ScenarioContext.getContext(Keys.SCENARIO);
		Collection<String> tags = scenario.getSourceTagNames();
		System.out.println(tags);
		for (String s : tags) {
	        System.out.println("value= " + s);
	        if(s.contains("@C")) {
				caseID = s.substring(2);
			} else if (s.contains("@R")) {
				runID = s.substring(2);
			}
	        
	    }

		System.out.println("scen status" +scenario.getStatus());
		switch (scenario.getStatus()) {
		case "passed":
			status = 1;
			break;
		case "undefined":
			status = 2;
			break;
		case "pending":
			status = 3;
			break;
		case "skipped":
			status = 4;
			break;
		case "failed":
			status = 5;
			break;
		default:
			System.out.println(status + " is not a valid Status");
			break;
		}
		
		APIClient client = new APIClient("https://smartbygep.testrail.net/");
        client.setUser("avinash.singh@gep.com ");
        client.setPassword("Testrail@123");
        Map data = new HashMap();
        data.put("status_id", status);
        data.put("comment", "Automation POC Test - Test Executed - Status updated automatically from Selenium test automation.");
        try {
			client.sendPost("add_result_for_case/"+runID+"/"+caseID+"",data );
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (APIException e) {
			e.printStackTrace();
		}

    }
}
