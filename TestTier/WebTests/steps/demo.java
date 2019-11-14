package steps;

import org.testng.Assert;

import com.vimalselvam.cucumber.listener.Reporter;

import Pages.HomePage;
import Pages.Login;
import Pages.ShouldCostPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import testTierUtils.Keys;
import testTierUtils.ScenarioContext;
import utils.Utilities;

public class demo  {

	public demo() {
		super();
	}

	@Given("^Login using valid credentials")
	public void login_using_valid_credentials() throws Throwable {

		Reporter.addStepLog("Launch URL with cucumber");

		Login.getInstance(ScenarioContext.getContext(Keys.WebDriver)).login(
				ScenarioContext.getContext(Keys.UserName).toString(),
				ScenarioContext.getContext(Keys.PassWord).toString());
	}

	@When("^Create Should Cost From Blank$")
	public void create_Should_Cost_From_Blank() throws Throwable {

		HomePage.getInstance(ScenarioContext.getContext(Keys.WebDriver)).createSCFromBlank();
	}

	@Given("^Valid test data avialable to fill Basic information$")
	public void valid_test_data_avialable_to_fill_Basic_information() throws Throwable {
/*
		ShouldCostDTO curr = scSql.getCurrency().stream().skip(random.nextInt(scSql.getCurrency().size() - 1))
				.findFirst().get();*/
//		ScenarioContext.setContext(Keys.Currency, curr.getCurrencyCode());

		/*
		 * String ROOT_URI = "http://localhost:7000/employees"; Response
		 * response = get(ROOT_URI + "/list");
		 * System.out.println(response.asString()); response =
		 * response.when().get(ENDPOINT_GET_BOOK_BY_ISBN);
		 * response.then().body("id", hasItems(1, 2));
		 * response.then().body("name", hasItems("Pankaj"));
		 */
	}

	@When("^Fill Basic information$")
	public void fill_Basic_information() throws Throwable {
		ShouldCostPage.getInstance(ScenarioContext.getContext(Keys.WebDriver))
				.fillBasicDetails(ScenarioContext.getContext(Keys.Currency).toString());
	}

	@Then("^Click on Done$")
	public void click_on_Done() throws Throwable {
		
	}

	@Then("^Verify Save As Draft button is visible$")
	public void verify_Save_As_Draft_button_is_visible() throws Throwable {
		Assert.assertEquals(Utilities.getText(ShouldCostPage.getInstance(ScenarioContext.getContext(Keys.WebDriver)).saveAsDraftButton), "SAVE AS DRAFT", "Save as draft is displayed");
		// Assert.assertEquals(Utilities.getText(scPage.saveAsDraftButton),
		// "SAVE AS ", "Save as draft is displayed");
	}

	@When("^Click on Save as draft$")
	public void click_on_Save_as_draft() throws Throwable {
		ShouldCostPage.getInstance(ScenarioContext.getContext(Keys.WebDriver)).clickSaveAsDraft();
	}

	@Then("^Update TestRail Status (\\d+)$")
	public void update_TestRail_Status(int arg1) throws Throwable {
		System.out.println(arg1);
	}
}
