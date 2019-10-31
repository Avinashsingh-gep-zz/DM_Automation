package Pages;

import org.openqa.selenium.WebDriver;

import Locators.LoginLocators;

public class Login extends LoginLocators {

	private static Login instance;
	
	public static WebDriver driver;

	public static synchronized Login getInstance(Object driver) {
		Login.driver = (WebDriver)driver;
		if (instance == null) {
			
			instance = new Login((WebDriver)driver);
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
