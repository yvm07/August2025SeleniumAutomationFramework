package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("EP100: Design the Opencart Login App Page")
@Feature("F101: Design Opencart Login page")
@Story("S01: develop login core features")

public class LoginPageTest extends BaseTest{

	@Description("This is the Login Title Page Test")
	@Link("JIRA-1001")
	@Owner("Yamuna Murthy")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority=10)
	public void loginPageTitleTest() {
		
		String actTitle=loginPage.getLoginPageTitle();
		ChainTestListener.log("Login Page Title is : "+actTitle);
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE);
	}
	
	@Test(priority=20)
	public void loginPageUrlTest() {
		
		String actUrl=loginPage.getLoginPageUrl();
		Assert.assertTrue(actUrl.contains(AppConstants.LOGIN_PAGE_FRACTION_URL));
	}
	
	@Test(priority=30)
	public void isForgotPwdLinkExistTest() {
		
		boolean flag=loginPage.isForgotPwdLinkExist();
		Assert.assertTrue(flag);
	}

	@Test(priority=40)
	public void isPageHeaderExistTest() {
		
		boolean flag=loginPage.isHeaderExist();
		Assert.assertTrue(flag);
	}

	@Test(priority=50)
	public void loginTest() {
		
		accPage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		boolean flag=accPage.isLogOutLinkExist();
		Assert.assertTrue(flag);
		
	}
	
	


}



