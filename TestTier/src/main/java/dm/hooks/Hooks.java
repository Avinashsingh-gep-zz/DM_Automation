package dm.hooks;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.paulhammant.ngwebdriver.NgWebDriver;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import dm.testtier.utils.BrowserFactory;
import dm.testtier.utils.Keys;
import dm.testtier.utils.Report;
import dm.testtier.utils.ScenarioContext;

public class Hooks {

	static WebDriver driver;
	static NgWebDriver ngDriver;
	Report report = (Report)ScenarioContext.getContext(Keys.REPORT);
	
	public Hooks() {

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
		System.out.println("web setup");
	}

	@After("@web")
	public void afterScenario(Scenario scenario) {
		report.takeEmbeedScreenshot(scenario);
	}

	@Before("@api")
	public void mobileSetUp() {
		System.out.println(" api setup");
	}

	@After
	public void mobileCleanUp() {

	}
}
