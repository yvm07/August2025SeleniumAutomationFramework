package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class SearchResultsPage {

	//private By locators
	private final By searchResults = By.xpath("//div[@class='product-thumb']");
	private final By resultsHeader = By.tagName("h1");
	
	
	private WebDriver driver;
	private ElementUtil eUtil;
	
	//public constructor
	
	public SearchResultsPage(WebDriver driver) {
		this.driver=driver;
		eUtil=new ElementUtil(driver);
	}
	
	
	//public methods
	
	public int getSearchedResultsCount()
	{
		int count=eUtil.waitForElementsPresence(searchResults, AppConstants.DEFAULT_SHORT_WAIT).size();
		System.out.println("Total searched products are : "+count);
		return count;
	}
	
	public String getResultsHeaderValue()
	{
		String header=eUtil.doElementGetText(resultsHeader);
		System.out.println("Search result header : "+header);
		return header;
	}
	
	public ProductInfoPage selectProduct(String productName)
	{
		System.out.println("Product to be selected : "+productName);
		eUtil.doClick(By.linkText(productName));
		return new ProductInfoPage(driver);
	}
	
}
