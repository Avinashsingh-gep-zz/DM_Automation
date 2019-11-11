package dm.hooks;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.paulhammant.ngwebdriver.NgWebDriver;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import dm.runner.TestRunner;
import dm.testtier.utils.BrowserFactory;
import dm.testtier.utils.ConfigurationProperties;
import dm.testtier.utils.JsonReader;
import dm.testtier.utils.Keys;
import dm.testtier.utils.PropertyReader;
import dm.testtier.utils.Report;
import dm.testtier.utils.ScenarioContext;
import io.restassured.specification.RequestSpecification;

public class Hooks {

	static WebDriver driver;
	static NgWebDriver ngDriver;
	Report report = (Report)ScenarioContext.getContext(Keys.REPORT);
	static apihelper.RestCalls restCalls;
	HashMap<String, String> configInfo = new HashMap<String, String>();
	public static String ProjectPath = System.getProperty("user.dir");
	
	
	public Hooks() throws IOException {
		restCalls = new apihelper.RestCalls();
		configInfo = JsonReader.readJsonFile(
				System.getProperty("user.dir") + PropertyReader.readConfig(ConfigurationProperties.JSON_PATH),
				TestRunner.environment, TestRunner.client);

	}

	@Before("@web")
	public void webSetUp(Scenario scenario) throws IOException, ParseException, SQLException {
		driver = BrowserFactory.getDriver(ScenarioContext.getContext(Keys.BROWSER).toString());
		ScenarioContext.setContext(Keys.WebDriver, driver);
		driver = (WebDriver) ScenarioContext.getContext(Keys.WebDriver);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
		driver.manage().window().maximize();
		driver.get(ScenarioContext.getContext(Keys.URL).toString());
		ngDriver = new NgWebDriver((JavascriptExecutor) driver);
		ngDriver.waitForAngularRequestsToFinish();
		ScenarioContext.setContext(Keys.SCENARIO, scenario);
		System.out.println("web setup");
	}

	@After("@web")
	public void afterScenario(Scenario scenario) {
		report.takeEmbeedScreenshot(scenario);
		Report.updateStatusToTestRail(); 
	}

	@Before("@api")
	public void apiSetUp(Scenario scenario) throws IOException {
		System.out.println(" api setup");
		HashMap<String, String> TokenData = new HashMap<String, String>();
		String jsonFilePath = ProjectPath + File.separator+ "jsonFile" + File.separator + "TokenGenerated.json";
		String payload = apihelper.SampleApiReadJsonFile.JSONtoString(jsonFilePath);
		String URL = configInfo.get(Keys.APIBasicTokenURL.toString());
		@SuppressWarnings("serial")
		RequestSpecification requestSpec = restCalls.getGenericRequestSpec(apihelper.RestCalls.APIMethodType.POST, payload, new HashMap<String, String>() {{}}, TestRunner.BasicToken);
		apihelper.APIResponse responseData = restCalls.createRequest(apihelper.RestCalls.APIMethodType.POST,URL , requestSpec);
		TokenData = restCalls.getResponsedata(responseData,"IssueJWTTokenResult.securityJWTToken");
		TestRunner.BasicToken  = "Bearer" + " " + TokenData.get("uniqueid");
		ScenarioContext.setContext(Keys.SCENARIO, scenario);
		System.out.println("The Token Generation for API ----> " +TestRunner.BasicToken);
	}

	@After
	public void mobileCleanUp() {

	}
}
