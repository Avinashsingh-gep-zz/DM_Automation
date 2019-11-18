package nexxe.testtier.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.vimalselvam.cucumber.listener.ExtentProperties;
import com.vimalselvam.cucumber.listener.Reporter;

import cucumber.api.Scenario;
import nexxe.test.runner.TestRunner;

public class Report {

	/**
	 * This method will take screenshot and embed to HTML report .
	 *
	 * @param driver
	 * @param scenario
	 */

	private static String reportPrefix = "NEXXE_"+ TestRunner.documentName +"_" + TestRunner.environment;
	private String projectPath = System.getProperty("user.dir");
	private String reportGeneratePath = projectPath + "/target/cucumber-reports/extent-reports/";
	private String reportScreenshotPath = projectPath + "/target/cucumber-reports/extent-reports/screenshots/";

	public Report () {
		String reportName = reportPrefix +"_"+ getDateStamp();
		File directoryExtentReport = new File(reportGeneratePath);
		directoryExtentReport.mkdir();	
		ExtentProperties extentProperties = ExtentProperties.INSTANCE;
		extentProperties.setReportPath(reportGeneratePath + reportName + ".html");
	}

	public void takeScreenshotEmbed(Scenario scenario) {
		if (scenario.isFailed()) {
			String screenshotName = scenario.getName().replaceAll(" ", "_").concat(getDateStamp());
			try {
				File sourcePath = ((TakesScreenshot) ScenarioContext.getContext(Keys.WebDriver)).getScreenshotAs(OutputType.FILE);
				File directoryScreenshots = new File(reportScreenshotPath);
				directoryScreenshots.mkdir();	
				File destinationPath = new File(reportScreenshotPath + screenshotName + ".png");
				FileUtils.copyFile(sourcePath, destinationPath);
				scenario.embed(FileUtils.readFileToByteArray(destinationPath), "image/png");
				InputStream is = new FileInputStream(destinationPath);
				byte[] imageBytes = IOUtils.toByteArray(is);
				String base64 = Base64.getEncoder().encodeToString(imageBytes);
				Reporter.addScreenCaptureFromPath("data:image/png;base64," +base64);
				
			} catch (IOException e) {
			}
		}
	}

	private String getDateStamp() {
		return new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss").format(new GregorianCalendar().getTime());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void updateStatusToTestRail() {

        int status = 0;
        String caseID = null;
        String runID = null;		
		String testRailURL = PropertyReader.readConfig(ConfigurationProperties.testRailURL);
		String testRailUsername = PropertyReader.readConfig(ConfigurationProperties.testRailUsername);
		String testRailPassword = PropertyReader.readConfig(ConfigurationProperties.testRailPassword);
		String testRailComment = PropertyReader.readConfig(ConfigurationProperties.testRailComment);
		
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
		
		APIClient client = new APIClient(testRailURL);
        client.setUser(testRailUsername);
        client.setPassword(testRailPassword);
        Map data = new HashMap();
        data.put("status_id", status);
        data.put("comment", testRailComment); 
        
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
