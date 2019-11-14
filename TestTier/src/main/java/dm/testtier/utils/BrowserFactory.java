package dm.testtier.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.service.DriverService;


public class BrowserFactory {

	static private WebDriver driver;
	static private DriverService driverService;

	public static WebDriver getDriver(String browser) {
		DesiredCapabilities capability = new DesiredCapabilities();
		if (Keys.IE.toString().equalsIgnoreCase(browser)) {
			System.out.println(":::: Initiating " + browser + " Driver ::::");
			String iePath = PropertyReader.readConfig(ConfigurationProperties.IE_EXE_PATH);
			iePath = System.getProperty("user.dir") + iePath;
			System.setProperty("webdriver.ie.driver", iePath);
			capability = DesiredCapabilities.internetExplorer();
			capability.setCapability("enablePersistentHover", false);
			capability.setCapability("ignoreZoomSetting", true);
			capability.setCapability("nativeEvents", false);
			capability.setCapability("ignoreProtectedModeSettings", true);
			capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
			capability.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
			capability.setCapability("ie.ensureCleanSession", true);
			capability.setJavascriptEnabled(true);
			
			InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions(capability);

			driverService = new InternetExplorerDriverService.Builder()
					.usingDriverExecutable(new File(iePath))
					.usingAnyFreePort()
					.build();
			try {
				driverService.start();
				driver = new InternetExplorerDriver((InternetExplorerDriverService)driverService, internetExplorerOptions);
				System.out.println(":::: " + browser + " Driver initiated ::::");
			} catch (IOException e) {
				System.out.println(":::: I/O error InternetExplorerDriver path ::::");
			}

		} else if (Keys.FIREFOX.toString().equalsIgnoreCase(browser)) {
			System.out.println(":::: Initiating " + browser + " Driver ::::");
			capability = DesiredCapabilities.firefox();
			FirefoxOptions options = new FirefoxOptions(capability);
			driver = new FirefoxDriver(options);
			System.out.println(":::: " + browser + " Driver initiated ::::");

		} else if (Keys.CHROME.toString().equalsIgnoreCase(browser)) {
			System.out.println(":::: Initiating " + browser + " Driver ::::");
			String chromePath = PropertyReader.readConfig(ConfigurationProperties.CHROME_EXE_PATH);
			chromePath = System.getProperty("user.dir") + chromePath;
			System.setProperty("webdriver.chrome.driver", chromePath);

			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized");

			driverService = new ChromeDriverService.Builder()
					.usingDriverExecutable(new File(chromePath))
					.usingAnyFreePort()
					.build();
			try {
				driverService.start();
				driver = new ChromeDriver((ChromeDriverService)driverService, options);
				System.out.println(":::: " + browser + " Driver initiated ::::");
			} catch (IOException e) {
				System.out.println(":::: I/O error in ChromeDriver path ::::");
			}

		} else if (Keys.REMOTE.toString().equalsIgnoreCase(browser)) {
			System.out.println(":::: Initiating " + browser + " Driver ::::");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized");
			options.addArguments("disable-infobars");
			options.setCapability(CapabilityType.VERSION, "");
			options.setCapability("platform", "linux");

			try {
				String hubUrl = PropertyReader.readConfig(ConfigurationProperties.HUB_URL);
				driver = new RemoteWebDriver(new URL(hubUrl), options);
				System.out.println(":::: " + browser + " Driver initiated ::::");
			} catch (MalformedURLException e) {
				System.out.println(":::: MalformedURLException error in Remote Driver ::::");
			}
		} else {
			System.out.println(":::: Invalid driver : '" + browser + "' ::::");
		}
		return driver;
	}

	public static boolean quitDriver() {
		if (driver != null) {
			driver.quit();
		}
		return true;
	}

	public static boolean stopDriverService() {
		if (driverService != null) {
			driverService.stop();
		}
		return true;
	}
}
