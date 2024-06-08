package com.dnt.douglas.commonutils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FileVariables {
	private static String extentDir;
	private static String screenShotPath;
	private String sStartTime;
	private String extentReportFolderPath;
	private Date date;
	private SimpleDateFormat sdf;
	private String sDate;
	private String hash;

	public static String getScreenShotPath() {
		return screenShotPath;
	}

	public void setScreenShotPath(String screenShotPath) {
		FileVariables.screenShotPath = screenShotPath;
	}

	public String getsStartTime() {
		return sStartTime;
	}

	public void setsStartTime(String sStartTime) {
		this.sStartTime = sStartTime;
	}

	public String getExtentReportFolderPath() {
		return extentReportFolderPath;
	}

	public void setExtentReportFolderPath(String extentReportFolderPath) {
		this.extentReportFolderPath = extentReportFolderPath;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}

	public String getsDate() {
		return sDate;
	}

	public void setsDate(String sDate) {
		this.sDate = sDate;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public static String getExtentDir() {
		return extentDir;
	}

	public void setExtentDir(String extentDir) {
		this.extentDir = extentDir;
	}
}
