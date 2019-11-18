package nexxe.web.pages;

import org.openqa.selenium.WebDriver;

import nexxe.web.locators.ShouldCostPageLocators;
import nexxe.web.utils.Utilities;

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
	}

	public void clickSaveAsDraft() {

		waitAndClick(saveAsDraftButton);
	}
}
