package dm.runner;

import com.cucumber.listener.ExtentProperties;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import dm.utils.DataTierUtils;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@CucumberOptions(features = "classpath:features", plugin = { "json:target/cucumber-report-feature-composite.json",
		"com.cucumber.listener.ExtentCucumberFormatter:" },tags = {"@web","@webtest2"}, glue = { "steps", "hooks" }, dryRun = false)
/*tags = {"~@Ignore"},*/
public class TestRunner {
	private TestNGCucumberRunner testNGCucumberRunner;
	public static String client;
	public static String browser;
	public static String environment;
	static WebDriver driver;


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
	public void feature(CucumberFeatureWrapper cucumberFeature) {
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
}