package nexxe.webtest.steps;

import java.io.IOException;
import java.util.Random;

import org.testng.Assert;

import com.vimalselvam.cucumber.listener.Reporter;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import nexxe.data.utils.DataTierUtils;
import nexxe.dbDTO.ShouldCostDTO;
import nexxe.sqlOperations.ShouldCostSql;
import nexxe.test.runner.TestRunner;
import nexxe.testtier.utils.Keys;
import nexxe.testtier.utils.ScenarioContext;
import nexxe.web.pages.HomePage;
import nexxe.web.pages.Login;
import nexxe.web.pages.ShouldCostPage;
import nexxe.web.utils.Utilities;

public class TempDemoWebSteps {
	public TempDemoWebSteps() {
		super();
	}

	@Given("^Login using valid credentials")
	public void loginsdwehvgu() throws Throwable {

		Reporter.addStepLog("Launch URL with cucumber");
		Login.getInstance(ScenarioContext.getContext(Keys.WebDriver)).login(
				ScenarioContext.getContext(Keys.UserName).toString(),
				ScenarioContext.getContext(Keys.PassWord).toString());

	}

	@When("^Create Should Cost From Blank$")
	public void create_Should_Cost_From_Blank() throws Throwable {

		HomePage.getInstance(ScenarioContext.getContext(Keys.WebDriver)).createSCFromBlank();
	}

	@Given("^Valid test data available to fill Basic information$")
	public void valid_test_data_available_to_fill_Basic_information() throws Throwable {

		Random r = new Random();

		ShouldCostDTO curr = ShouldCostSql.getInstance().getCurrency().stream().skip(r.nextInt(ShouldCostSql.getInstance().getCurrency().size() - 1)).findFirst()
				.get();
		ScenarioContext.setContext(Keys.Currency, curr.getCurrencyCode());

	}

	@When("^Fill Basic information$")
	public void fill_Basic_information()  {
		ShouldCostPage.getInstance(ScenarioContext.getContext(Keys.WebDriver))
		.fillBasicDetails(ScenarioContext.getContext(Keys.Currency).toString());
	}

	@Then("^Click on Done$")
	public void click_on_Done() throws Throwable {	
	System.out.println(Utilities.getText(ShouldCostPage.getInstance(ScenarioContext.getContext(Keys.WebDriver)).saveAsDraftButton));
	}
	
	@Then("^Verify Save As Draft button is visible$")
	public void verify_Save_As_Draft_button_is_visible()  {
//		Assert.assertEquals(Utilities.getText(scPage.saveAsDraftButton), "SAVE AS DRAFT", "Save as draft is displayed");
		Assert.assertEquals(Utilities.getText(ShouldCostPage.getInstance(ScenarioContext.getContext(Keys.WebDriver)).saveAsDraftButton), "SAVE AS", "Save as draft is displayed");
	}

	@When("^Click on Save as draft$")
	public void click_on_Save_as_draft() throws Throwable {	   
		ShouldCostPage.getInstance(ScenarioContext.getContext(Keys.WebDriver)).clickSaveAsDraft();
	}

	@Given("^Establish Database Connection$")
	public void establishDatabaseConnection() throws IOException {
		DataTierUtils.getDbConnection(TestRunner.client, TestRunner.environment);
	}


}
