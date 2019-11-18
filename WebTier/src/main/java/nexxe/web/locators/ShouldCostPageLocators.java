package nexxe.web.locators;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import nexxe.web.utils.Utilities;


public class ShouldCostPageLocators extends Utilities {
	public ShouldCostPageLocators(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);

	}

	@FindBy(id = "mat-input-4")
	protected WebElement currency;

	@FindBy(xpath = "//mat-option[@id='mat-option-4']//span//div//div")
	protected WebElement currencySuggestion;

	@FindBy(xpath = "//a[contains(text(),'Save As Draft')]")
	public WebElement saveAsDraftButton;

}
