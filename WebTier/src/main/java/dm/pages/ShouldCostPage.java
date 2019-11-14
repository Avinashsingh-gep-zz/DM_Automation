package dm.pages;

import org.openqa.selenium.WebDriver;

import dm.locators.ShouldCostPageLocators;


public class ShouldCostPage extends ShouldCostPageLocators {
	private static ShouldCostPage instance;

	public static WebDriver driver;

	public static synchronized ShouldCostPage getInstance(Object driver) {
		ShouldCostPage.driver = (WebDriver) driver;
		if (instance == null) {
			instance = new ShouldCostPage((WebDriver) driver);
		}
		return instance;
	}

	public ShouldCostPage(WebDriver driver) {
		super(driver);
	}

	public void fillBasicDetails(String curr) {

		waitAndType(currency, curr);
		waitAndClick(currencySuggestion);
//		waitAndClick(saveAsDraftButton);
	}

	public void clickSaveAsDraft() {

		waitAndClick(saveAsDraftButton);
	}
}
