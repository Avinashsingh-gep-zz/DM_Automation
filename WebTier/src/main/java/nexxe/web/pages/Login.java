package nexxe.web.pages;

import org.openqa.selenium.WebDriver;

import nexxe.web.locators.LoginLocators;
import nexxe.web.utils.Utilities;


public class Login extends LoginLocators {

	private static volatile Login instance;

	public static WebDriver driver;

	private static String sessionId;

	public static Login getInstance(Object driver) {
		if (sessionId != Utilities.getSessionID(driver).toString()) {
			instance = null;
		}
		if (instance == null) {
			instance = new Login((WebDriver) driver);
			Login.sessionId = Utilities.getSessionID(driver).toString();
		}
		return instance;
	}

	public Login(WebDriver driver) {
		super(driver);
	}

	public void login(String username, String password) {
		waitAndType(userName, username);
		waitAndType(passWord, password);
		waitAndClick(login);
	}

}
