package utils;

import java.time.Duration;
import java.util.function.Function;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Utilities {
	public static WebDriver driver;
	private static Capabilities cap;
	private static String session;

	public Utilities(WebDriver driver) {

		Utilities.driver = driver;

	}

	public static String getSessionID(Object driver) {

		cap = ((RemoteWebDriver) driver).getCapabilities();

		// String browsername = cap.getBrowserName();
		switch (cap.getBrowserName()) {
		case "chrome":
			session = ((ChromeDriver) driver).getSessionId().toString();
			break;

		case "firefox":
			session = ((FirefoxDriver) driver).getSessionId().toString();
			break;

		case "ie":
			session = ((InternetExplorerDriver) driver).getSessionId().toString();
			break;
		}

		return session;

	}

	public static WebElement fluentWait(final WebElement locator) {
		WebElement clickseleniumlink = null;
		try {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(Utilities.driver).withTimeout(Duration.ofMinutes(1))
					.pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class)
					.ignoring(ElementClickInterceptedException.class).ignoring(ElementNotInteractableException.class)
					.ignoring(ElementNotVisibleException.class);

			clickseleniumlink = wait.until(new Function<WebDriver, WebElement>() {

				public WebElement apply(WebDriver driver) {
					return locator;
				}
			});

		} catch (Exception e) {
			System.err.println(e);
		}
		return clickseleniumlink;
	}

	public void waitAndClick(final WebElement locator) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		explicitWait(locator);
		executor.executeScript("arguments[0].click();", locator);

	}

	public void waitAndType(final WebElement locator, String keys) {
		fluentWait(locator).sendKeys(keys);
	}

	public static String getText(final WebElement locator) {
		return fluentWait(locator).getText();
	}

	public WebElement explicitWait(final WebElement locator) {
		WebElement clickseleniumlink = null;
		try {

			WebDriverWait wait = new WebDriverWait(driver, 60);

			wait.until(ExpectedConditions.elementToBeClickable(locator));

			return locator;

		} catch (Exception e) {
			System.err.println(e);
		}
		return clickseleniumlink;
	}

}
