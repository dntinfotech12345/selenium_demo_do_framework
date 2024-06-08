package com.dnt.douglas.baseutil;

import org.openqa.selenium.WebDriver;

import com.dnt.douglas.pages.Home_Page;
import com.dnt.douglas.util.WebActionUtil;

public class InitializePages {
	public Home_Page homePage;

	public InitializePages(WebDriver driver, long ETO, WebActionUtil actionUtil) {
		homePage = new Home_Page(driver, ETO, actionUtil);
	}

}
