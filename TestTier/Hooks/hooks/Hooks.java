package hooks;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.common.io.Files;
import com.paulhammant.ngwebdriver.NgWebDriver;
import com.vimalselvam.cucumber.listener.Reporter;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import requestDTO.AuthenticationRequestDTO;
import runner.TestRunner;
import testTierUtils.ConfigurationProperties;
import testTierUtils.JsonReader;
import testTierUtils.Keys;
import testTierUtils.PropertyReader;
import testTierUtils.ScenarioContext;
import utils.API_Verbs;
import utils.ApiUtils;

public class Hooks {

	private HashMap<String, String> configInfo;
	private HashMap<String, String> baseurl;

	public Hooks() {

	}

	static WebDriver driver;
	static NgWebDriver ngDriver;

	@Before("@web")
	public void webSetUp(Scenario scenario) throws IOException, ParseException, SQLException {

		if (TestRunner.browser.equals("Chrome")) {

			configInfo = JsonReader.readJsonFile(
					System.getProperty("user.dir") + PropertyReader.readConfig(ConfigurationProperties.JSON_PATH),
					TestRunner.environment, TestRunner.client);

			baseurl = JsonReader.readJsonFile(
					System.getProperty("user.dir") + PropertyReader.readConfig(ConfigurationProperties.BASE_URL_CONFIG),
					TestRunner.environment, TestRunner.client);

			ScenarioContext.setContext(Keys.UserName, configInfo.get(Keys.UserName.toString()));
			ScenarioContext.setContext(Keys.PassWord, configInfo.get(Keys.PassWord.toString()));

			String chromePath = PropertyReader.readConfig(ConfigurationProperties.CHROME_EXE_PATH);
			chromePath = System.getProperty("user.dir") + chromePath;
			System.setProperty("webdriver.chrome.driver", chromePath);
			driver = new ChromeDriver();

			driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
			driver.manage().window().maximize();
			driver.get(configInfo.get(Keys.URL.toString()));
			ngDriver = new NgWebDriver((JavascriptExecutor) driver);
			ngDriver.waitForAngularRequestsToFinish();
			ScenarioContext.setContext(Keys.WebDriver, driver);
			ApiUtils.getAccessToken(configInfo.get(Keys.UserName.toString()),
					configInfo.get(Keys.BuyerPartnerCode.toString()), configInfo.get(Keys.PartnerCode.toString()),
					configInfo.get(Keys.API_URL.toString()));

		} else if (TestRunner.browser.equals("IE")) {

		}

		System.out.println("web setup");
	}

	@After("@web")
	public void webCleanUp(Scenario scenario) {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Date date = new Date();

		if (scenario.isFailed()) {
			String screenshotName = scenario.getName().replaceAll(" ", "_").concat(dateFormat.format(date).toString());
			try {

				File sourcePath = ((TakesScreenshot) ScenarioContext.getContext(Keys.WebDriver))
						.getScreenshotAs(OutputType.FILE);
				File destinationPath = new File(System.getProperty("user.dir") + "/target/cucumber-reports/screenshots/"
						+ screenshotName + ".png");
				Files.copy(sourcePath, destinationPath);
				Reporter.addScreenCaptureFromPath(destinationPath.toString());
			} catch (IOException e) {
			}
		}
		driver.close();

		Reporter.loadXMLConfig(PropertyReader.getReportConfigPath());
	}

	@Before("@api")
	public void apiSetUp() throws IOException {
		
		


		configInfo = JsonReader.readJsonFile(
				System.getProperty("user.dir") + PropertyReader.readConfig(ConfigurationProperties.JSON_PATH),
				TestRunner.environment, TestRunner.client);

		baseurl = JsonReader.readJsonFile(
				System.getProperty("user.dir") + PropertyReader.readConfig(ConfigurationProperties.BASE_URL_CONFIG),
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
		// driver.close();
	}
}
