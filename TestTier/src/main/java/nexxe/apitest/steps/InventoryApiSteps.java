package nexxe.apitest.steps;

import java.util.Optional;

import org.testng.Assert;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import nexxe.api.dto.responseDTO.QuicklinkResponseDTO;
import nexxe.api.urls.Inventory;
import nexxe.api.utils.API_Verbs;
import nexxe.api.utils.ApiUtils;
import nexxe.testtier.utils.Keys;
import nexxe.testtier.utils.ScenarioContext;

public class InventoryApiSteps {

	private Response response;

	@When("^Get the Quicklinks$")
	public void get_the_Quicklinks() throws Throwable {

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
