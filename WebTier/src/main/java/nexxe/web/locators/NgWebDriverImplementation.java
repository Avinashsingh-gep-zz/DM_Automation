package nexxe.web.locators;

import org.openqa.selenium.WebElement;
import com.paulhammant.ngwebdriver.ByAngularBinding;
import com.paulhammant.ngwebdriver.ByAngularButtonText;
import com.paulhammant.ngwebdriver.ByAngularCssContainingText;
import com.paulhammant.ngwebdriver.ByAngularExactBinding;
import com.paulhammant.ngwebdriver.ByAngularModel;
import com.paulhammant.ngwebdriver.ByAngularOptions;
import com.paulhammant.ngwebdriver.ByAngularPartialButtonText;
import com.paulhammant.ngwebdriver.ByAngularRepeater;
import com.paulhammant.ngwebdriver.ByAngularRepeaterCell;
import com.paulhammant.ngwebdriver.ByAngularRepeaterColumn;
import com.paulhammant.ngwebdriver.ByAngularRepeaterRow;

public class NgWebDriverImplementation {

	@ByAngularBinding.FindBy(rootSelector = "butter", binding = "cheese")
	public WebElement findBy_binding;

	@ByAngularButtonText.FindBy(rootSelector = "butter", buttonText = "cheese")
	public WebElement findBy_buttonText;

	@ByAngularCssContainingText.FindBy(rootSelector = "butter", cssSelector = "cheese", searchText = "crackers")
	public WebElement findBy_cssContainingText;

	@ByAngularExactBinding.FindBy(rootSelector = "butter", exactBinding = "cheese")
	public WebElement findBy_exactBinding;

	@ByAngularModel.FindBy(rootSelector = "butter", model = "cheese")
	public WebElement findBy_model;

	@ByAngularOptions.FindBy(rootSelector = "butter", options = "cheese")
	public WebElement findBy_options;

	@ByAngularPartialButtonText.FindBy(rootSelector = "butter", partialButtonText = "cheese")
	public WebElement findBy_partialButtonText;

	@ByAngularRepeater.FindBy(rootSelector = "butter", repeater = "cheese", exact = true)
	public WebElement findBy_repeater;

	@ByAngularRepeaterCell.FindBy(rootSelector = "butter", repeater = "cheese", exact = true, row = 99, column = "cracker")
	public WebElement findBy_repeaterCell;

	@ByAngularRepeaterColumn.FindBy(rootSelector = "butter", repeater = "cheese", exact = true, column = "cracker")
	public WebElement findBy_repeaterColumn;

	@ByAngularRepeaterRow.FindBy(rootSelector = "butter", repeater = "cheese", exact = true, row = 99)
	public WebElement findBy_repeaterRow;

}
