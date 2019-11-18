package nexxe.apitest.steps;

import java.util.HashMap;
import java.util.List;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import nexxe.testtier.utils.Keys;
import nexxe.testtier.utils.ScenarioContext;

public class API_BackgroundSteps {

	@SuppressWarnings("unchecked")
	@Given("^Feature base url ModuleName$")
	public void feature_base_url_ModuleName(DataTable arg) throws Throwable {
		List<String> list = arg.asList(String.class);
		HashMap<String, String> context = (HashMap<String, String>) ScenarioContext.getContext(Keys.Base_URLs);
		ScenarioContext.setContext(Keys.Base_URL, context.get(list.get(0)));
	}


}


