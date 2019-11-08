package dm.apiTests.steps;

import java.io.File;
import java.util.HashMap;

import com.vimalselvam.cucumber.listener.Reporter;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dm.runner.TestRunner;
import dm.testtier.utils.Assertion;
import dm.testtier.utils.ConfigurationProperties;
import dm.testtier.utils.PropertyReader;
import io.restassured.specification.RequestSpecification;

public class DemoApiSteps {
	public static String ProjectPath = System.getProperty("user.dir");
	static apihelper.RestCalls restCalls;
	static apihelper.LogHelperFunctions LogHelperFunctions;
	
	public static apihelper.APIResponse responseData;

	public DemoApiSteps() {
		
		restCalls = new apihelper.RestCalls();
		LogHelperFunctions = new apihelper.LogHelperFunctions();
	
	}

	@Given("^Generate Basic Toekn$")
	public void generate_Basic_Toekn() throws Throwable {
	  System.out.println("The Basic Token is "+TestRunner.BasicToken);
	}

	@When("^Post API with ([^\"]*)$")
	public void post_API_with_mngr(String fileName) throws Throwable {
	   
		System.out.println("The Test with --> " + fileName);
		
		String jsonFilePath = ProjectPath + File.separator+ "jsonFile" + File.separator + fileName;
		String payload = apihelper.SampleApiReadJsonFile.JSONtoString(jsonFilePath);
		String PrettyJsonReq = LogHelperFunctions.writeJSONFormat(payload);
		Reporter.addStepLog("Request Payload is ===>"+ PrettyJsonReq);
		String URL =  PropertyReader.readConfig(ConfigurationProperties.APIshouldcost);
		@SuppressWarnings("serial")
		RequestSpecification requestSpec = restCalls.getGenericRequestSpec(apihelper.RestCalls.APIMethodType.POST, payload, new HashMap<String, String>() {{}}, TestRunner.BasicToken);
		 responseData = restCalls.createRequest(apihelper.RestCalls.APIMethodType.POST,URL , requestSpec);		
		String responsePayload =restCalls.responseAsString;
		String PrettyJsonResponse = LogHelperFunctions.writeJSONFormat(responsePayload);
		Reporter.addStepLog("Response Payload is ===>"+ PrettyJsonResponse);
		
	}

	@Then("^Staus should be Ok$")
	public void staus_should_be_Ok() throws Throwable {
		 HashMap<String, String> hmap = new HashMap<String, String>();
		 hmap = restCalls.getResponsedata(responseData);	
		 String id = hmap.get("StatusCode");
		Assertion.isEqual(id, "200", "The Post is sucessful");
		System.out.println("The Post is sucessfully");
	}
}
