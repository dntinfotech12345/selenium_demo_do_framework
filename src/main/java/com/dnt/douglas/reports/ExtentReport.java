package com.dnt.douglas.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ExtentReport {
	private static ExtentTest extentTtest;
	private static ExtentReports extentReports;

	public static ExtentTest createTest(String name) {

		extentTtest = extentReports.createTest(name);
		return extentTtest;
	}

	public static ExtentReports getExtent() {
		return extentReports;
	}

	public void initReport(String filepath) {
		extentReports = new Extent().getExtent(filepath);
	}

}
