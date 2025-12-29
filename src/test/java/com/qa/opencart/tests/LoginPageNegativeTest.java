package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class LoginPageNegativeTest extends BaseTest{

	@DataProvider
	public Object[][] getNegativeLoginData() {
		return new Object[][] {
			{"test@test.com","test"},
			{"","test123"},
			{"test123@test.com",""}
		};
	}
	
	
	
	@Test(dataProvider="getNegativeLoginData")
	public void negativeLoginTest(String inUname, String inPass) {
		boolean flag=loginPage.doLoginwithInvalidCreds(inUname, inPass);
		Assert.assertTrue(flag);
	}
}
