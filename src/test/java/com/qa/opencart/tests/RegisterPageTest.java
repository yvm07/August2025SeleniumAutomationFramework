package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.CsvUtil;
import com.qa.opencart.utils.ExcelUtil;
import com.qa.opencart.utils.StringUtil;

public class RegisterPageTest extends BaseTest{

	@BeforeClass
	public void goToRegisterPage()
	{
		registerPage=loginPage.navigateToRegisterPage();
	}
	
	@Test
	public void userRegistrationTest()
	{
		boolean flag=registerPage.userRegistration("Test", "User", StringUtil.generateEmailId(), "989898888", "test1234", "yes");
		Assert.assertTrue(flag);
	}
	
	
	
	@DataProvider
	public Object[][] getUserDataFromSheet()
	{
		Object[][] obj=ExcelUtil.getTestData("user_registration");
		return obj;
	}
	
	@Test(dataProvider="getUserDataFromSheet")
	public void userRegistrationFromSheetTest(String fName, String lName, String phone, String pass, String subscribe)
	{
		boolean flag=registerPage.userRegistration(fName, lName, StringUtil.generateEmailId(), phone, pass, subscribe);
		Assert.assertTrue(flag);
	}
	
	@DataProvider
	public Object[][] getUserDataFromCsvFile()
	{
		Object[][] obj=CsvUtil.csvData("registration");
		return obj;
	}
	
	@Test(dataProvider="getUserDataFromCsvFile")
	public void userRegistrationFromCsvFileTest(String fName, String lName, String phone, String pass, String subscribe)
	{
		boolean flag=registerPage.userRegistration(fName, lName, StringUtil.generateEmailId(), phone, pass, subscribe);
		Assert.assertTrue(flag);
	}
	
}
