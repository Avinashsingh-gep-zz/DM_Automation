package nexxe.web.locators;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import nexxe.web.utils.Utilities;


public class HomePageLocators extends Utilities{
	
	public HomePageLocators(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);

	}
	
	@FindBy(xpath = "//a[@data-tooltip='Create New Document']")
	protected static WebElement createNewDoc;
	
	@FindBy(xpath = "//a[@data-tooltip='Create New Document']")
	protected WebElement createHoverRing;
	
	@FindBy(xpath = "//a[@data-tooltip='Should Cost']")
	protected WebElement shouldCostModule;
	
	@FindBy(xpath = "//a[text()='Using Blank Form']")
	protected WebElement usingBlankForm;
	
	@FindBy(xpath = "//a[@data-tooltip='%s' or @data-tooltip='%s']")
	protected WebElement MODULE_ICON;
	
	@FindBy(xpath = "//i[contains(@class,'rightArrow')]")
	protected WebElement MODULE_LIST_RIGHT_ARROW;
	
	@FindBy(xpath = "//i[contains(@class,'leftArrow')]")
	protected WebElement MODULE_LIST_LEFT_ARROW;
	
	@FindBy(xpath = "//i[contains(@class,'btmArrow')]")
	protected WebElement MODULE_LIST_DOWN_ARROW;
	
	@FindBy(xpath = "//i[contains(@class,'topArrow')]")
	protected WebElement MODULE_LIST_TOP_ARROW;
	
	@FindBy(xpath = "(//div[contains(normalize-space(@class),'scrollBarClass')])[last()]")
	protected WebElement MODULE_LIST;
	
	}
