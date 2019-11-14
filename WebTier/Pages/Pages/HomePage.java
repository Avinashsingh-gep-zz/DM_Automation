package Pages;

import org.openqa.selenium.WebDriver;

import Locators.HomePageLocators;
import utils.Utilities;

public class HomePage extends HomePageLocators {
	private static HomePage instance;

	public static WebDriver driver;

	private static String sessionId;

	public static synchronized HomePage getInstance(Object driver) {
		if (sessionId != Utilities.getSessionID(driver).toString()) {
			instance = null;
		}
		if (instance == null) {
			instance = new HomePage((WebDriver) driver);
			HomePage.sessionId = Utilities.getSessionID(driver).toString();
		}
		return instance;
	}

	public HomePage(WebDriver driver) {
		super(driver);
	}

	public void createSCFromBlank() throws InterruptedException {
		waitAndClick(createNewDoc);
		waitAndClick(shouldCostModule);
		waitAndClick(usingBlankForm);

	}

}
