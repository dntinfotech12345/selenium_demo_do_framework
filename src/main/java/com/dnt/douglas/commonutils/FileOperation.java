package com.dnt.douglas.commonutils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dnt.douglas.baseutil.BaseTest;
import com.dnt.douglas.reports.ExtentReport;
import com.dnt.douglas.util.WebActionUtil;

public class FileOperation {
	FileVariables fileVariables = new FileVariables();

	public void CreateFiles() {

		fileVariables.setDate(new Date());
		fileVariables.setSdf(new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss"));
		fileVariables.setsDate(fileVariables.getSdf().format(fileVariables.getDate()));
		fileVariables.setsStartTime(fileVariables.getsDate());
		fileVariables.setExtentReportFolderPath(System.getProperty("user.dir") + "/reports");
		fileVariables.setExtentDir(fileVariables.getExtentReportFolderPath() + "/Automation Report- "
				+ WebActionUtil.getCurrentDateTime());
		BaseTest.currentDateAndTime = WebActionUtil.getCurrentDateTime();
		BaseTest.currentDateAndTimeNewFormat = WebActionUtil.getCurrentDateTime1();
		fileVariables.setScreenShotPath(fileVariables.getExtentDir() + "/Screenshots/");
		WebActionUtil.deleteDirectory(fileVariables.getExtentReportFolderPath());

		try {
			File file = new File(fileVariables.getExtentDir());
			if (!(file.exists())) {
				boolean extentFolderStatus = file.mkdirs();
				if (extentFolderStatus == true) {
					new ExtentReport().initReport(fileVariables.getExtentDir());
				} else {
					WebActionUtil.info("--> Extent Folder not Created");
				}
			}
		} catch (Exception e) {
			WebActionUtil.info("Inside on start catch block" + e.getMessage());
			e.printStackTrace();
		}
		try {
			File screenShot = new File(FileVariables.getScreenShotPath());
			if (!(screenShot.exists())) {
				boolean screenshotFolderStatus = screenShot.mkdirs();
				if (screenshotFolderStatus == true) {
				} else {
					WebActionUtil.info("Screenshot Folder Not Created");

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
