package nexxe.test.hooks;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.paulhammant.ngwebdriver.NgWebDriver;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import nexxe.api.utils.ApiUtils;
import nexxe.test.runner.TestRunner;
import nexxe.testtier.utils.ConfigurationProperties;
import nexxe.testtier.utils.JsonReader;
import nexxe.testtier.utils.Keys;
import nexxe.testtier.utils.PropertyReader;
import nexxe.testtier.utils.Report;
import nexxe.testtier.utils.ScenarioContext;

public class Hooks {
	public Hooks() {
		//Necessary to run
	}
	
	private static HashMap<String, String> configInfo;
	private static HashMap<String, String> baseurl;

	static WebDriver driver;
	static NgWebDriver ngDriver;
	private static String projectPath = System.getProperty("user.dir");
	
	Report report = (Report)ScenarioContext.getContext(Keys.REPORT);

	@Before("@web")
	public static void webSetUp(Scenario scenario) throws IOException{

		if (TestRunner.browser.equals("Chrome")) {

			configInfo = JsonReader.readJsonFile(
					projectPath + PropertyReader.readConfig(ConfigurationProperties.JSON_PATH),
					TestRunner.environment, TestRunner.client);

			baseurl = JsonReader.readJsonFile(
					projectPath + PropertyReader.readConfig(ConfigurationProperties.BASE_URL_CONFIG),
					TestRunner.environment, TestRunner.client);

			ScenarioContext.setContext(Keys.UserName, configInfo.get(Keys.UserName.toString()));
			ScenarioContext.setContext(Keys.PassWord, configInfo.get(Keys.PassWord.toString()));

			String chromePath = PropertyReader.readConfig(ConfigurationProperties.CHROME_EXE_PATH);
			chromePath = projectPath + chromePath;
			System.setProperty("webdriver.chrome.driver", chromePath);
			driver = new ChromeDriver();

			driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
			driver.manage().window().maximize();
			driver.get(configInfo.get(Keys.URL.toString()));
			ngDriver = new NgWebDriver((JavascriptExecutor) driver);
			ngDriver.waitForAngularRequestsToFinish();
			ScenarioContext.setContext(Keys.SCENARIO, scenario);
			ScenarioContext.setContext(Keys.WebDriver, driver);
			ApiUtils.getAccessToken(configInfo.get(Keys.UserName.toString()),
					configInfo.get(Keys.BuyerPartnerCode.toString()), configInfo.get(Keys.PartnerCode.toString()),
					configInfo.get(Keys.API_URL.toString()));

		} else if (TestRunner.browser.equals("IE")) {
			
			//write here

		}

		System.out.println("Web setup done!");
	}

	@After("@web")
	public void webCleanUp(Scenario scenario) {
		report.takeScreenshotEmbed(scenario);
		if (TestRunner.updateTestRailStatus) {
			Report.updateStatusToTestRail();
		}
		driver.close();
	}


	@Before("@api")
	public static void apiSetUp() throws IOException {
		configInfo = JsonReader.readJsonFile(
				projectPath + PropertyReader.readConfig(ConfigurationProperties.JSON_PATH),
				TestRunner.environment, TestRunner.client);

		baseurl = JsonReader.readJsonFile(
				projectPath + PropertyReader.readConfig(ConfigurationProperties.BASE_URL_CONFIG),
				TestRunner.environment, TestRunner.client);

		ApiUtils.getAccessToken(configInfo.get(Keys.UserName.toString()),
				configInfo.get(Keys.BuyerPartnerCode.toString()), configInfo.get(Keys.PartnerCode.toString()),
				configInfo.get(Keys.API_URL.toString()));

		ScenarioContext.setContext(Keys.Base_URLs, baseurl);
		ScenarioContext.setContext(Keys.UserName, configInfo.get(Keys.UserName.toString()));
		ScenarioContext.setContext(Keys.PassWord, configInfo.get(Keys.PassWord.toString()));
	}

	@After("@api")
	public void apiCleanUp() {
		//write here
	}
}
