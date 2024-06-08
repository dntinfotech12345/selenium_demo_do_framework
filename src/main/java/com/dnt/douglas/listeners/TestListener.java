package com.dnt.douglas.listeners;

import java.util.ArrayList;
import java.util.Properties;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.dnt.douglas.commonutils.FileVariables;
import com.dnt.douglas.reports.ExtentManager;
import com.dnt.douglas.reports.ExtentReport;
import com.dnt.douglas.util.WebActionUtil;

public class TestListener implements ITestListener {

	public String className;
	public static int iPassCount = 0;
	public static int iFailCount = 0;
	public static int iSkippedCount = 0;
	public static String profile = null;
	public static ArrayList sTestName = new ArrayList<String>();
	public static ArrayList sStatus = new ArrayList<String>();
	public static long totPassedTime = 0;
	public static long totFailedTime = 0;
	public static long totSkippedTime = 0;
	public static long totalTimeTaken = 0;
	public static String pass_Time = "0";
	public static String fail_Time = "0";
	public static String skip_Time = "0";
	public static String tot_Time = "0";
	public static Properties prop;
	public static String current_className;
	public static ExtentTest test;
	static {
		profile = System.getProperty("profile");
		profile = "JavaMail";
		System.setProperty("profile", profile);
	}

	public void onFinish(ITestContext context) {
		iPassCount = context.getPassedTests().size();
		iFailCount = context.getFailedTests().size();
		iSkippedCount = context.getSkippedTests().size();
		int iTotal_Executed = iPassCount + iFailCount + iSkippedCount;
		totalTimeTaken = totPassedTime + totFailedTime + totSkippedTime;
		tot_Time = WebActionUtil.formatDuration(totalTimeTaken);
		ExtentReport.getExtent().flush();
	}

	public void onStart(ITestContext context) {

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	public void onTestStart(ITestResult result) {

	}

	public void onTestSuccess(ITestResult result) {
		iPassCount = iPassCount + 1;
		ExtentManager.getTestReport().log(Status.PASS, result.getMethod().getMethodName() + "-Test case passed");
	}

	public void onTestFailure(ITestResult result) {
		try {
			iFailCount = iFailCount + 1;
			ExtentManager.getTestReport().log(Status.FAIL, result.getMethod().getMethodName() + "-Test case failed");

			ExtentManager.getTestReport()
					.addScreenCaptureFromPath(WebActionUtil.getScreenShot(FileVariables.getScreenShotPath(),
							result.getTestClass().getRealClass().getSimpleName().toString()));
		} catch (Exception e) {
			WebActionUtil.error("Unable to attach the screenshot");
		}
	}

	public void onTestSkipped(ITestResult result) {
		String testCaseName = result.getMethod().getMethodName();
		String description = result.getMethod().getDescription();
		ExtentTest testReport = ExtentManager.getParentReport().createNode(testCaseName, "Description: " + description);
		ExtentManager.setTestReport(testReport);
		iSkippedCount = iSkippedCount + 1;
		ExtentManager.getTestReport().log(Status.SKIP, result.getMethod().getMethodName() + "-Test case Skipped");
	}

}
