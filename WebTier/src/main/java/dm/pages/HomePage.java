package dm.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import dm.locators.HomePageLocators;


public class HomePage extends HomePageLocators {
	private static HomePage instance;

	public static WebDriver driver;

	public static synchronized HomePage getInstance(Object driver) {
		HomePage.driver = (WebDriver) driver;
		if (instance == null) {
			instance = new HomePage((WebDriver) driver);
		}
		return instance;
	}

	public HomePage(WebDriver driver) {
		super(driver);
	}

	public void createSCFromBlank() throws InterruptedException {
		waitAndClick(createNewDoc);
		waitAndClick(MODULE_LIST_LEFT_ARROW);
		waitAndClick(shouldCostModule);
		waitAndClick(usingBlankForm);
	}
}
