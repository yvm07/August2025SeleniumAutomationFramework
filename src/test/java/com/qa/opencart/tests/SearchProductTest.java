package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class SearchProductTest extends BaseTest{

	@BeforeClass
	public void searchProductSetUp()
	{
		accPage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void searchProductTest()
	{
		searchResultsPage=accPage.doSearch("macbook");
		productInfoPage=searchResultsPage.selectProduct("MacBook Air");
		
		String actHeader=productInfoPage.getProductHeader();
		
		Assert.assertEquals(actHeader, "MacBook Air");
	}
}
