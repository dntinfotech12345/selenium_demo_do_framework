package com.dnt.douglas.baseutil;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.mail.EmailException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.dnt.douglas.commonutils.FileOperation;
import com.dnt.douglas.reports.ExtentManager;
import com.dnt.douglas.reports.ExtentReport;
import com.dnt.douglas.util.WebActionUtil;
import com.mysql.cj.jdbc.Driver;


public class BaseTest {
	public WebDriver driver;
	public static Driver driverDB;
	public static final int ITO = 6;
	public static final int ITO_High = 10;
	public static final int ETO = 60;
	public static final int pageLoadTimeout = 15;
	public static Logger logger = LoggerFactory.getLogger(BaseTest.class);
	public static WebActionUtil actionUtil;
	public String testCaseName;
	public static String className;
	public static Properties properties;
	public static Properties prop_constants;
	public static String sDirPath = System.getProperty("user.dir");
	public static final String driverPath = sDirPath + "/drivers";
	public static final String TESTDATAEXCELPATH = sDirPath + "/src/main/resources/TestData/_Data.xlsx";
	public static final String CONFIGPATH = sDirPath + "/src/main/resources/EnvironmentVariables/config.properties";
	public static final String VALIDATIONCONSTANTS = sDirPath
			+ "/src/main/resources/TestData/Validation_Constants.properties";
	public static String currentDateAndTime;
	public static String currentDateAndTimeNewFormat;
	public static InitializePages pages;

	static {
		try {
			properties = new Properties();
			prop_constants = new Properties();
			FileInputStream fis = new FileInputStream(CONFIGPATH);
			properties.load(fis);
			FileInputStream fis1 = new FileInputStream(VALIDATIONCONSTANTS);
			prop_constants.load(fis1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@BeforeSuite(alwaysRun = true)
	public synchronized void createFiles() {
		try {

			WebActionUtil.info("Folder creation for Extent");
			FileOperation fileOperation = new FileOperation();
			fileOperation.CreateFiles();

		} catch (Exception e) {
			WebActionUtil.info("Exception while report inititation");
		}
	}


	@Parameters({ "browserName" })
	@BeforeTest(alwaysRun = true)
	public synchronized void downloadDriverExecutables(String browserName) {

		CreateDriver.getInstance().setupWebDriver(browserName);

	}


	@Parameters("browserName")
	@BeforeClass
	public synchronized void launchApp(String browserName) {

		className = getClass().getSimpleName();
		logger = LoggerFactory.getLogger(getClass().getName());
		ExtentTest parentExtentTest = ExtentReport.createTest(getClass().getSimpleName());
		ExtentManager.setParentReport(parentExtentTest);
	}


	@Parameters("browserName")
	@BeforeMethod
	public synchronized void setExtentReport(String browserName, Method methodName) {

		CreateDriver.getInstance().setDriver(browserName);
		driver = CreateDriver.getInstance().getDriver();
		driver.manage().timeouts().implicitlyWait(pageLoadTimeout, TimeUnit.SECONDS);
		actionUtil = new WebActionUtil(CreateDriver.getInstance().getDriver(), ETO);
		pages = new InitializePages(driver, ETO, actionUtil);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(properties.getProperty("App_URL"));
		logger = LoggerFactory.getLogger(getClass().getName());

		this.testCaseName = methodName.getName();
		String description = methodName.getAnnotation(Test.class).description();
		ExtentTest testReport = ExtentManager.getParentReport().createNode(testCaseName, "Description: " + description);
		ExtentManager.setTestReport(testReport);

		if (driver.getWindowHandles().size() > 0) {
			actionUtil.validationinfo(browserName.toUpperCase() + " Browser is launched", "blue");
			actionUtil.info(browserName.toUpperCase() + " Browser is launched");
		} else {
			actionUtil.fail("Failed to launch the Browser");
			actionUtil.error("Failed to launch the Browser");
		}
	}

	@AfterMethod(alwaysRun = true)
	public synchronized void closeBrowser() {
		try {
			CreateDriver.cleanDriver();
		} catch (Exception e) {

		}
	}


	@AfterSuite(alwaysRun = true)
	@Parameters("browserName")
	public synchronized void killTask(String browserName) throws EmailException {

		CreateDriver.killBrowserTask(browserName);

	}
}