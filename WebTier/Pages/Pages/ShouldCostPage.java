package Pages;

import org.openqa.selenium.WebDriver;

import Locators.ShouldCostPageLocators;
import utils.Utilities;

public class ShouldCostPage extends ShouldCostPageLocators {
	private static ShouldCostPage instance;

	public static WebDriver driver;

	private static String sessionId;

	public static synchronized ShouldCostPage getInstance(Object driver) {
		if (sessionId != Utilities.getSessionID(driver).toString()) {
			instance = null;
		}
		if (instance == null) {
			instance = new ShouldCostPage((WebDriver) driver);
			ShouldCostPage.sessionId = Utilities.getSessionID(driver).toString();
		}
		return instance;
	}

	public ShouldCostPage(WebDriver driver) {
		super(driver);
	}

	public void fillBasicDetails(String curr) {

		waitAndType(currency, curr);
		waitAndClick(currencySuggestion);
		// waitAndClick(saveAsDraftButton);
	}

	public void clickSaveAsDraft() {

		waitAndClick(saveAsDraftButton);
	}
}
