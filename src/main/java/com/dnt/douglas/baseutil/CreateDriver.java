package com.dnt.douglas.baseutil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.dnt.douglas.util.WebActionUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateDriver {

	public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	private static CreateDriver instance = null;

	private CreateDriver() {
	}

	public static CreateDriver getInstance() {
		if (instance == null) {
			instance = new CreateDriver();
		}
		return instance;
	}

	public WebDriver setDriver(String browserName) {

		if (driver.get() == null) {
			try {
				driver.set(createDriver(browserName));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return driver.get();
	}

	public static WebDriver getDriver() {
		return driver.get();
	}

	public static void cleanDriver() {

		driver.get().quit();
		driver.remove();

	}

	public static WebDriver createDriver(String browserName) {
		if (browserName.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", BaseTest.sDirPath + "/drivers/chromedriver.exe");
			 ChromeOptions chromeOptions = new ChromeOptions();
		        chromeOptions.addArguments("--disable-notifications");

		        Map<String, Object> prefs = new HashMap<>();
		        prefs.put("safebrowsing.enabled", "true");
		        chromeOptions.setExperimentalOption("prefs", prefs);

		        return new ChromeDriver(chromeOptions);
		}
		return null;
	}


	public static void setupWebDriver(String browserName) {
		try {
			killBrowserTask(browserName);
			if (browserName.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().avoidOutputTree().setup();
			}
		} catch (Exception e) {
			WebActionUtil.error("Unable to download " + browserName + " driver executable");
		}

	}

	public static void killBrowserTask(String browserName) {
		try {

			if (browserName.equalsIgnoreCase("chrome")) {
				Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
			}

		} catch (IOException e) {
			WebActionUtil.error("Unable to kill the " + browserName + " browser task");
		}
	}

}
