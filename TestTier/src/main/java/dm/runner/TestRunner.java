package dm.runner;

import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.github.mkolisnyk.cucumber.reporting.CucumberResultsOverview;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import dm.testtier.utils.BrowserFactory;
import dm.testtier.utils.ConfigurationProperties;
import dm.testtier.utils.JsonReader;
import dm.testtier.utils.Keys;
import dm.testtier.utils.PropertyReader;
import dm.testtier.utils.Report;
import dm.testtier.utils.ScenarioContext;

@CucumberOptions(features = "src/test/java"
, plugin = { "com.vimalselvam.cucumber.listener.ExtentCucumberFormatter:","json:target/cucumber-reports/cucumber-report.json" }
//, tags = {"@web", "@api"}
//, tags = {"@api"}
, glue = { "dm.hooks", "dm.webTests.steps", "dm.apiTests.steps"}, dryRun = false)
/*tags = {"~@Ignore"},*/
public class TestRunner {
	private TestNGCucumberRunner testNGCucumberRunner;
	public static String client;
	public static String browser;
	public static String environment;
	static WebDriver driver;
	public static String BasicToken;
	public static boolean UpdateTestRailStatus;

	@BeforeSuite
	@Parameters({ "Browser", "Client", "Environment" })
	public void beforeSuite(String browser, String client, String environment) throws IOException {
		HashMap<String, String> configInfo = JsonReader.readJsonFile(
				System.getProperty("user.dir") + PropertyReader.readConfig(ConfigurationProperties.JSON_PATH),
				environment, client);
		ScenarioContext.setContext(Keys.URL, configInfo.get(Keys.URL.toString()));
		ScenarioContext.setContext(Keys.UserName, configInfo.get(Keys.UserName.toString()));
		ScenarioContext.setContext(Keys.PassWord, configInfo.get(Keys.PassWord.toString()));
		ScenarioContext.setContext(Keys.BROWSER, browser);
		ScenarioContext.setContext(Keys.CLIENT, client);
		ScenarioContext.setContext(Keys.ENVIRONMENT, environment);
		System.out.println("Environment is" + environment);
		ScenarioContext.setContext(Keys.REPORT, new Report());
	}

	@BeforeClass(alwaysRun = true)
	@Parameters({ "Browser", "Client", "Environment", "UpdateTestRailStatus" })
	public void setUpClass(String Browser, String Client, String Environment, Boolean UpdateTestRailStatus) throws Exception {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
		TestRunner.client = Client;
		TestRunner.browser = Browser;
		TestRunner.environment = Environment;
		TestRunner.UpdateTestRailStatus = UpdateTestRailStatus;
		System.out.println("test runner started");

	}

	@Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
	public void feature(CucumberFeatureWrapper cucumberFeature) throws Throwable {
		testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
	}

	@DataProvider
	public Object[][] features() {
		return testNGCucumberRunner.provideFeatures(); 
	}

	@AfterClass(alwaysRun = true)
	public void tearDownClass() throws Exception {
		testNGCucumberRunner.finish();
	}

	@AfterSuite
	public void afterSuite() throws Exception {
		BrowserFactory.stopDriverService();
		CucumberResultsOverview results = new CucumberResultsOverview();
		results.setOutputDirectory("target/cucumber-reports/advanced-reports");
		results.setOutputName("cucumber-results");
		results.setSourceFile("./target/cucumber-reports/cucumber-report.json");
		results.execute();
	}
}