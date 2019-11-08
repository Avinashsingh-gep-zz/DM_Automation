package dm.webTests.steps;

import java.io.IOException;
import java.util.Random;

import org.testng.Assert;

import com.vimalselvam.cucumber.listener.Reporter;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dm.datatier.utils.DataTierUtils;
import dm.dbDTO.ShouldCostDTO;
import dm.runner.TestRunner;
import dm.testtier.utils.Keys;
import dm.testtier.utils.ScenarioContext;
import dm.utils.Utilities;
import dm.webTests.objectFactory.ObjectFactory;

public class DemoWebSteps extends ObjectFactory {

	@Given("^Login using valid credentials")
	public void login_using_valid_credentials() throws Throwable {

		Reporter.addStepLog("Launch URL with cucumber");
		login.login(ScenarioContext.getContext(Keys.UserName).toString(),
				ScenarioContext.getContext(Keys.PassWord).toString());
	}

	@When("^Create Should Cost From Blank$")
	public void create_Should_Cost_From_Blank() throws Throwable {

		homePage.createSCFromBlank();
	}

	@Given("^Valid test data available to fill Basic information$")
	public void valid_test_data_available_to_fill_Basic_information() throws Throwable {

		Random r = new Random();

		ShouldCostDTO curr = scSql.getCurrency().stream().skip(r.nextInt(scSql.getCurrency().size() - 1)).findFirst()
				.get();
		ScenarioContext.setContext(Keys.Currency, curr.getCurrencyCode());

	}

	@When("^Fill Basic information$")
	public void fill_Basic_information() throws Throwable {
		scPage.fillBasicDetails(ScenarioContext.getContext(Keys.Currency).toString());
	}

	@Then("^Click on Done$")
	public void click_on_Done() throws Throwable {	
	System.out.println(Utilities.getText(scPage.saveAsDraftButton));
	}
	
	@Then("^Verify Save As Draft button is visible$")
	public void verify_Save_As_Draft_button_is_visible() throws Throwable {
//		Assert.assertEquals(Utilities.getText(scPage.saveAsDraftButton), "SAVE AS DRAFT", "Save as draft is displayed");
		Assert.assertEquals(Utilities.getText(scPage.saveAsDraftButton), "SAVE AS", "Save as draft is displayed");
	}

	@When("^Click on Save as draft$")
	public void click_on_Save_as_draft() throws Throwable {	   
		scPage.clickSaveAsDraft();
	}

	@Given("^Establish Database Connection$")
	public void establishDatabaseConnection() throws IOException {
		DataTierUtils.getDbConnection(TestRunner.client, TestRunner.environment);
	}


}
