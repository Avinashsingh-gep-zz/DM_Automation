package Locators;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.Utilities;

public class HomePageLocators extends Utilities{
	
	public HomePageLocators(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);

	}
	
	@FindBy(xpath = "//a[@data-tooltip='Create New Document']")
	protected WebElement createNewDoc;
	
	@FindBy(xpath = "//a[@data-tooltip='Create New Document']")
	protected WebElement createHoverRing;
	
	@FindBy(xpath = "//a[@data-tooltip='Should Cost']")
	protected WebElement shouldCostModule;
	
	@FindBy(xpath = "//a[text()='Using Blank Form']")
	protected WebElement usingBlankForm;

}
