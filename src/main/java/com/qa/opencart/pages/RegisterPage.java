package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class RegisterPage {

	private final By firstName=By.id("input-firstname");
	private final By lastName=By.id("input-lastname");
	private final By email=By.id("input-email");
	private final By telephone=By.id("input-telephone");
	private final By password=By.id("input-password");
	private final By confirmPassword=By.id("input-confirm");
	private final By subscribeYes=By.xpath("(//input[@type='radio'])[2]");
	private final By subscribeNo=By.xpath("(//input[@type='radio'])[3]");
	private final By policy=By.xpath("//input[@name='agree']");
	private final By clickBtn=By.xpath("//input[@value='Continue']");
	
	private final By registerLink=By.linkText("Register");
	private final By logoutLink=By.linkText("Logout");
	
	private final By successMsg=By.cssSelector("div#content h1");
	
	private WebDriver driver;
	private ElementUtil eUtil;
	
	//public constructor
	public RegisterPage(WebDriver driver)
	{
		this.driver=driver;
		eUtil=new ElementUtil(driver);
		
	}
	
	public boolean userRegistration(String fName, String lName, String mail, String phone, String pass, String subscribe)
	{
		eUtil.waitforElementVisibility(firstName, AppConstants.DEFAULT_SHORT_WAIT);
		eUtil.doSendKeys(firstName, fName);
		eUtil.doSendKeys(lastName, lName);
		eUtil.doSendKeys(email, mail);
		eUtil.doSendKeys(telephone, phone);
		eUtil.doSendKeys(password, pass);
		eUtil.doSendKeys(confirmPassword, pass);
		
		if(subscribe.equalsIgnoreCase("yes"))
		{
			eUtil.doClick(subscribeYes);
		}
		else
		{
			eUtil.doClick(subscribeNo);
		}
		
		eUtil.doClick(policy);
		eUtil.doClick(clickBtn);
		
		String successMessage=eUtil.waitForElementPresence(successMsg, AppConstants.DEFAULT_SHORT_WAIT).getText();
		System.out.println("Success Message : "+successMessage);
		
		eUtil.doClick(logoutLink);
		eUtil.doClick(registerLink);
		
		if(successMessage.equals(AppConstants.USER_REGISTRATION_SUCCESS_MESSAGE))
		{
			return true;
		}
		else
		{
			return false;
		}
		
		
	}
	
	
}
