package steps;

import java.util.Optional;

import org.testng.Assert;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import responseDTO.QuicklinkResponseDTO;
import testTierUtils.Keys;
import testTierUtils.ScenarioContext;
import url.Inventory;
import utils.API_Verbs;
import utils.ApiUtils;

public class InventorySteps {

	private Response response;

	@When("^Get the Quicklinks$")
	public void get_the_Quicklinks() throws Throwable {

		/*
		 * AuthenticationDTO newBlog = new
		 * AuthenticationDTO().setBpc("Test").setPartnerCode("TEst")
		 * .setUserName("Test");
		 * 
		 * ApiUtils.getGenericRequest(API_Verbs.GET,
		 * ScenarioContext.getContext(Keys.Base_URL), Inventory.quickLinks,
		 * Optional.of(newBlog));
		 */

		response = ApiUtils.getGenericRequest(API_Verbs.GET, ScenarioContext.getContext(Keys.Base_URL),
				Inventory.quickLinks, Optional.empty());
	}

	@Then("^Verify Return Value \"([^\"]*)\"$")
	public void verify_Return_Value(String arg1){
		
		QuicklinkResponseDTO quicklinkResponseDTO = (QuicklinkResponseDTO) ApiUtils.retriveResponse(response,
				new QuicklinkResponseDTO());						
		Assert.assertTrue(quicklinkResponseDTO.getReturnValue().contains(arg1));
		System.err.println(quicklinkResponseDTO.getReturnValue());
	}
}
