package nexxe.web.locators;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.paulhammant.ngwebdriver.ByAngularBinding;

import nexxe.web.utils.Utilities;


public class LoginLocators extends Utilities {

	public LoginLocators(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);

	}
	
	@ByAngularBinding.FindBy(rootSelector = "butter", binding = "cheese")
    public WebElement findBy_binding;

	@FindBy(id = "UserName")
	protected

	WebElement userName;

	@FindBy(id = "Password")
	protected

	WebElement passWord;

	@FindBy(id = "login")
	protected

	WebElement login;

}
