package com.dnt.douglas.loginpage.scripts;

import org.testng.annotations.Test;

import com.dnt.douglas.baseutil.BaseTest;
import com.dnt.douglas.dataproviders.DataProviderFactory;
import com.dnt.douglas.dataproviders.DataProviderFileRowFilter;

public class Product_Filter extends BaseTest {
	@DataProviderFileRowFilter(file = "./src/main/resources/TestData/DouglasData.xlsx", sql = "Select * from Douglas where SlNo ='Product_Filter_001'")
	@Test(dataProvider = "data1", dataProviderClass = DataProviderFactory.class, description = "Verify user is able filter the Perfume Product")
	public void Product_Filter_001(String SINO, String ddName,String ddValue) {

		pages.homePage.validateHomePage();
		
		pages.homePage.clkOnCookieButton();

		pages.homePage.clkOnPerfumeLink();

		pages.homePage.scrollToHeadline();

		pages.homePage.selectDDValue(ddName,ddValue);

		pages.homePage.validateProductCount(ddValue);

	}
}
