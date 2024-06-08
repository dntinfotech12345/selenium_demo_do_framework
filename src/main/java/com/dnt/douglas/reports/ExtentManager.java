package com.dnt.douglas.reports;

import com.aventstack.extentreports.ExtentTest;

public class ExtentManager {
	private static ThreadLocal<ExtentTest> extentReportForTestMethod = new ThreadLocal<ExtentTest>();

	public static ExtentTest getExtentReportForTestMethods() {
		return extentReportForTestMethod.get();
	}

	public static void setExtentReportsForTestMethods(ExtentTest extent) {
		extentReportForTestMethod.set(extent);
	}

	private static ThreadLocal<ExtentTest> extentReportForClassMethod = new ThreadLocal<ExtentTest>();

	public static ExtentTest getExtentReportForClassMethods() {
		return extentReportForClassMethod.get();
	}

	public static void setExtentReportsForClassMethods(ExtentTest extent) {
		extentReportForClassMethod.set(extent);
	}

	private static ThreadLocal<ExtentTest> extentReportForPrecondition = new ThreadLocal<ExtentTest>();

	public static ExtentTest getExtentReportForPrecondition() {
		return extentReportForPrecondition.get();
	}

	public static void setExtentReportsForPrecondition(ExtentTest extent) {
		extentReportForPrecondition.set(extent);
	}

	private static ThreadLocal<ExtentTest> parentReport = new ThreadLocal<ExtentTest>();

	public static void setParentReport(ExtentTest extentTest) {
		parentReport.set(extentTest);
	}

	public static ExtentTest getParentReport() {
		return parentReport.get();
	}

	private static ThreadLocal<ExtentTest> testReport = new ThreadLocal<ExtentTest>();

	public static ExtentTest getTestReport() {
		return testReport.get();
	}

	public static void setTestReport(ExtentTest extent) {
		testReport.set(extent);
	}

	private static ThreadLocal<ExtentTest> exteReport = new ThreadLocal<ExtentTest>();

	public static void setReport(ExtentTest extentTest) {
		exteReport.set(extentTest);
	}

	public static ExtentTest getReport() {
		return exteReport.get();
	}
}
