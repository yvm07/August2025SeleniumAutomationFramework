package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class AccountsPageTest extends BaseTest{

	
	@BeforeClass
	public void accSetUp()
	{
		accPage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void isLogoutLinkExist()
	{
		boolean flag=accPage.isLogOutLinkExist();
		Assert.assertTrue(flag);
	}
	
	@Test
	public void accPageHeadersTest()
	{
		List<String> accPageHeaders=accPage.getAccPageHeaders();
		Assert.assertEquals(accPageHeaders.size(), 4);
	}
	
	@Test
	public void searchProduct()
		{
			accPage.doSearch("imac");
		}
	
}
