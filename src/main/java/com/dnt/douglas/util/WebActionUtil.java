package com.dnt.douglas.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.dnt.douglas.baseutil.BaseTest;
import com.dnt.douglas.baseutil.CreateDriver;
import com.dnt.douglas.reports.ExtentManager;

public class WebActionUtil {
	public static WebDriver driver;
	public WebDriverWait wait;
	public long ETO;
	public static JavascriptExecutor jsExecutor;
	public Actions action;
	public String mainWindowID;

	public static BaseTest b;

	public WebActionUtil(WebDriver driver, long ETO) {
		this.driver = driver;
		this.ETO = ETO;
		wait = new WebDriverWait(driver, ETO);
		jsExecutor = (JavascriptExecutor) driver;
		action = new Actions(driver);
		b = new BaseTest();
	}

	public static void info(String message) {
		BaseTest.logger.info(message);
	}

	public static void error(String message) {
		BaseTest.logger.error(message);
	}

	public static void extentinfo(String message) {
		ExtentManager.getTestReport().info(message);
	}

	public static void pass(String message) {
		ExtentManager.getTestReport().pass(MarkupHelper.createLabel(message, ExtentColor.GREEN));
	}

	public static void validationinfo(String message, String color) {
		if (color.equalsIgnoreCase("blue"))
			ExtentManager.getTestReport().info(MarkupHelper.createLabel(message, ExtentColor.BLUE));
		else if (color.equalsIgnoreCase("green"))
			ExtentManager.getTestReport().pass(MarkupHelper.createLabel(message, ExtentColor.GREEN));
		else if (color.equalsIgnoreCase("red"))
			ExtentManager.getTestReport().fail(MarkupHelper.createLabel(message, ExtentColor.RED));
	}

	public static void fail(String message) {
		ExtentManager.getTestReport().fail(MarkupHelper.createLabel(message, ExtentColor.RED));
	}

	public static String getCurrentDateTime() {
		DateFormat customFormat = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss");
		Date currentDate = new Date();
		return customFormat.format(currentDate);
	}

	public static String getCurrentDateTime1() {
		DateFormat customFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date currentDate = new Date();
		return customFormat.format(currentDate);
	}

	public void clearText(WebElement element, String elementName) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.clear();
			info("Cleared the text present in" + elementName);
		} catch (Exception e) {
			error("Unable to clear the text in " + elementName);
			Assert.fail("Unable to clear the text in " + elementName);
		}
	}

	public void scrollToElement(WebElement element, String elementName) {
		info("Scroll till the " + elementName);
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
			info("Scroll till the " + elementName + " completed");
		} catch (Exception e) {
			error("Scroll till the " + elementName + " failed");
			Assert.fail("Scroll till the " + elementName + " failed");
		}
	}

	public synchronized static void poll(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public synchronized void waitForElement(WebElement element, String elementName) {
		try {
			info("Wait for " + elementName);
			wait.until(ExpectedConditions.visibilityOf(element));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			Assert.assertTrue(wait.until(ExpectedConditions.visibilityOf(element)) != null);

		} catch (Exception e) {
			error(elementName + " is not visible");
			Assert.fail(elementName + " is not visible");
		}
	}

	public void waitForElements(List<WebElement> elements, String elementName) {
		try {
			info("Wait for " + elementName);
			wait.until(ExpectedConditions.visibilityOfAllElements(elements));
			Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfAllElements(elements)) != null);
		} catch (Exception e) {
			error(elementName + " is not visible");
			Assert.fail(elementName + " is not visible");

		}
	}

	public synchronized void waitForVisibilityOfElement(WebElement element, String elementName) {
		info("Wait for Visiblity of " + elementName);
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			error(elementName + " is not visible");
			Assert.fail(elementName + " is not visible");

		}
	}

	public synchronized boolean isElementVisible(WebElement element, String elemantName) {

		try {
			info("Validate " + elemantName + " is visible");
			wait.until(ExpectedConditions.visibilityOf(element));
			info(elemantName + " is visible");
			return true;
		} catch (Exception e) {
			info(elemantName + " is not visible");
			return false;
		}
	}

	public synchronized void validateUrl(String expectedUrl, String elementName) {
		try {
			String actualUrl = driver.getCurrentUrl();
			Assert.assertEquals(expectedUrl, actualUrl);
			info(elementName + " page is displayed");
			validationinfo(elementName + " page is displayed", "green");
		} catch (Exception e) {
			error(elementName + " page is not displayed");
			fail(elementName + " page is not displayed");
			Assert.fail(elementName + " page is not displayed");
		}
	}

	public synchronized void validateisElementDisplayed(WebElement element, String elementName,
			String validationPassMessage, String validationFailMessage, String color) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			Assert.assertTrue(element.isDisplayed());
			validationinfo(validationPassMessage, color);
			info(validationPassMessage);
		} catch (AssertionError e) {
			fail(validationFailMessage);
			error(validationFailMessage);
			Assert.fail(validationFailMessage);
		}
	}

	public synchronized String getText(WebElement element, String elementName) {
		String text = null;
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			info(elementName + " is visible");
			text = element.getText();
			info(text + " text is retrieved from the element " + elementName);
			return text;
		} catch (Exception e) {
			error("Failed to retrieve " + text + " text from the element " + elementName);
			return null;
		}
	}

	public synchronized void clickOnElement(WebElement element, String elementName) {
		if (isElementClickable(element, elementName)) {
			element.click();
			info("Clicked on " + elementName);
			extentinfo("Clicked on " + elementName);
		} else {
			error("Unable to click on " + elementName);
			fail("Unable to click on " + elementName);
			Assert.fail("Unable to click on " + elementName);

		}
	}

	public synchronized boolean isElementClickable(WebElement element, String elementName) {

		info("Validate " + elementName + " is clickable");
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			return true;
		} catch (Exception e) {
			error(elementName + " is not clickable");
			return false;
		}
	}

	public synchronized void waitForPageToLoad() {
		try {
			boolean load = jsExecutor.executeScript("return document.readyState").toString().equals("complete");
			if (load)
				info("Page Loaded Successfully");
		} catch (Exception e) {
			error("Unable to Load the Page");
			Assert.fail("Unable to Load the Page");
		}
	}
	
	public static synchronized void deleteDirectory(String folderToDelete) {
		try {
			File dir = new File(folderToDelete);
			File[] files = dir.listFiles();
			if (files.length > 1) {
				for (File file : files) {
					String name = file.getName();
					if (name.toLowerCase().contains("automation")) {
						String[] dateTime = name.split("- ");
						if (calculateDateDifference(dateTime[1]) > 30) {
							String tempXLFile = new StringBuffer(folderToDelete).append(File.separator).append(name)
									.toString();
							deleteDir(tempXLFile);
						}
					}
				}
			}
		} catch (Exception e) {
			error("Unable to delete the directory");
			fail("Unable to delete the directory");
		}
	}
	
	public static synchronized long calculateDateDifference(String actualDateTime) {
		// Create an instance of the SimpleDateFormat class
		DateFormat customFormat = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss");
		long days_difference = 0;
		String currentDateTime = getCurrentDateTime();
		try {
			// Use parse method to get date object of both dates
			Date date1 = customFormat.parse(actualDateTime);
			Date date2 = customFormat.parse(currentDateTime);
			// Calculate time difference in milliseconds
			long time_difference = date2.getTime() - date1.getTime();
			// Calculate time difference in days using TimeUnit class
			days_difference = TimeUnit.MILLISECONDS.toDays(time_difference) % 365;
		} catch (Exception e) {
			error("Unable to calculate Date difference");
			fail("Unable to calculate Date difference");

		}
		return days_difference;
	}
	
	public static void deleteDir(String pathToDeleteFolder) {
		File extefolder = new File(pathToDeleteFolder);
		if ((extefolder.exists())) {
			deleteFolderDir(extefolder);
		}
	}
	
	public static void deleteFolderDir(File folderToDelete) {
		File[] folderContents = folderToDelete.listFiles();
		if (folderContents != null) {
			for (File folderfile : folderContents) {
				if (!Files.isSymbolicLink(folderfile.toPath())) {
					deleteFolderDir(folderfile);
				}
			}
		}
		folderToDelete.delete();
	}
	
	public static String formatDuration(long millis) {
		long seconds = (millis / 1000) % 60;
		long minutes = (millis / (1000 * 60)) % 60;
		long hours = millis / (1000 * 60 * 60);

		StringBuilder b = new StringBuilder();
		b.append(hours == 0 ? "00" : hours < 10 ? String.valueOf("0" + hours) : String.valueOf(hours));
		b.append(":");
		b.append(minutes == 0 ? "00" : minutes < 10 ? String.valueOf("0" + minutes) : String.valueOf(minutes));
		b.append(":");
		b.append(seconds == 0 ? "00" : seconds < 10 ? String.valueOf("0" + seconds) : String.valueOf(seconds));
		return b.toString();
	}

	public synchronized static String getScreenShot(String path, String className) {
		TakesScreenshot screenshot = (TakesScreenshot) CreateDriver.getInstance().getDriver();
		File src = (screenshot.getScreenshotAs(OutputType.FILE));
		String currentDateTime = getCurrentDateTime();
		String destinationPath = path + className + "-" + currentDateTime + ".png";
		File destination = new File(destinationPath);
		try {
			FileUtils.copyFile(src, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "./Screenshots/" + className + "-" + currentDateTime + ".png";
	}
}
