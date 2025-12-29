package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountPage {

	//private By locators
	
	private final By headers=By.xpath("//div[@id='content']/h2");
	private final By logoutLink=By.xpath("(//a[text()='Logout'])[2]");
	private final By searchBar=By.xpath("//input[@name='search']");
	private final By searchIcon=By.xpath("//span[@class='input-group-btn']/button");
	
	private WebDriver driver;
	private ElementUtil eUtil;
	
	//public constructor
	
	public AccountPage(WebDriver driver) {
		this.driver=driver;
		eUtil=new ElementUtil(driver);
	}
	
	//Public methods
	
	public List<String> getAccPageHeaders()
	{		
		return eUtil.getElementsTextList(headers);
	}
	
	
	public boolean isLogOutLinkExist() {
		boolean flag=eUtil.isElementDisplayed(logoutLink);
		return flag;
	}
	
	
	public SearchResultsPage doSearch(String searchvalue) {
		
		
		WebElement el=eUtil.waitForElementPresence(searchBar, AppConstants.DEFAULT_SHORT_WAIT);
		el.clear();
		el.sendKeys(searchvalue);
		eUtil.doClick(searchIcon);
		return new SearchResultsPage(driver);
	}
	
	
	
	
	
	
}
