package com.dnt.douglas.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.dnt.douglas.commonutils.FileVariables;
import com.dnt.douglas.util.WebActionUtil;

public class Extent {

	public static ThreadLocal<ExtentReports> driver = new ThreadLocal<>();
	private ExtentReports extent;
	FileVariables fileVariables = new FileVariables();

	public ExtentReports getExtent(String filepath) {
		if (extent == null) {
			try {
				extent = new ExtentReports();
				extent.attachReporter(getHtmlReporter(filepath + "/AutomationReport.html"));
				return extent;
			} catch (Exception e) {
				WebActionUtil.info("Exception while creating report html file.");
			}
		}
		return extent;
	}

	private static ExtentTest extentTtest;
	private static ExtentReports extentReports;

	public static ExtentTest createTest(String name) {

		extentTtest = extentReports.createTest(name);
		return extentTtest;
	}

	private static ExtentHtmlReporter getHtmlReporter(String filePath) {
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(filePath);
		htmlReporter.loadXMLConfig(System.getProperty("user.dir") + "/config/extent-config.xml");
		return htmlReporter;
	}

}
