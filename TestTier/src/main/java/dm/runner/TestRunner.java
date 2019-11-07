package dm.runner;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.vimalselvam.cucumber.listener.ExtentProperties;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import dm.datatier.utils.DataTierUtils;
import dm.testtier.utils.BrowserFactory;
import dm.testtier.utils.ConfigurationProperties;
import dm.testtier.utils.JsonReader;
import dm.testtier.utils.Keys;
import dm.testtier.utils.PropertyReader;
import dm.testtier.utils.ScenarioContext;

@CucumberOptions(features = "src/test/java"
, plugin = { "com.vimalselvam.cucumber.listener.ExtentCucumberFormatter:" }
, tags = {"@web"}
, glue = { "dm.hooks", "dm.webTests.steps", "dm.apiTests.steps"}, dryRun = false)
/*tags = {"~@Ignore"},*/
public class TestRunner {
	private TestNGCucumberRunner testNGCucumberRunner;
	public static String client;
	public static String browser;
	public static String environment;
	static WebDriver driver;

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
	}

	@BeforeClass(alwaysRun = true)
	@Parameters({ "Browser", "Client", "Environment" })
	public void setUpClass(String Browser, String Client, String Environment) throws Exception {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
		TestRunner.client = Client;
		TestRunner.browser = Browser;
		TestRunner.environment = Environment;
		System.out.println("test runner started");
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Date date = new Date();
		String reportName = "DM_ShouldCost_Report_" +dateFormat.format(date).toString();
		ExtentProperties extentProperties = ExtentProperties.INSTANCE;
		extentProperties.setReportPath(System.getProperty("user.dir") + "/target/cucumber-reports/Reports/" +reportName+ ".html");
		DataTierUtils.getDbConnection(TestRunner.client, TestRunner.environment);
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
	public void afterSuite() {
		BrowserFactory.stopDriverService();
		BrowserFactory.quitDriver();
	}
}