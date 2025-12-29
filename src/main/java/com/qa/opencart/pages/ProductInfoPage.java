package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {
	
	//private By locators
	
	private final By header = By.tagName("h1");
	private final By images = By.xpath("//a[@class='thumbnail']");
	private final By productMetadata = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	
	private final By productPrice = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
	
	public Map<String,String> productInfo;
	
	private WebDriver driver;
	private ElementUtil eUtil;
	
	//public constructor
	
	public ProductInfoPage(WebDriver driver) {
		this.driver=driver;
		eUtil=new ElementUtil(driver);
	}

	//public methods
	
	public String getProductHeader()
	{
		String headerValue=eUtil.waitforElementVisibility(header, AppConstants.DEFAULT_SHORT_WAIT).getText();
		System.out.println("Product Info Page Header is : "+headerValue);
		return headerValue;
	}
	
	public int getProductImagesCount()
	{
		int imagesCount=eUtil.waitforElementsVisibility(images, AppConstants.DEFAULT_SHORT_WAIT).size();
		System.out.println("Overall images of the selected product is : "+imagesCount);
		return imagesCount;
	}
	
	private void getProductMetadata()
	{
		List<WebElement> metaList=eUtil.waitforElementsVisibility(productMetadata, AppConstants.DEFAULT_SHORT_WAIT);
		System.out.println("Total size of metadata is : "+metaList.size());
		
		for(WebElement e:metaList) {
			String text = e.getText();
			
			String meta[]=text.split(":");
			String key=meta[0];
			String value=meta[1].trim();
			
			productInfo.put(key, value);
		}	
	}
	
	private void getProductPriceData() {
		
		List<WebElement> priceList=eUtil.waitforElementsVisibility(productPrice, AppConstants.DEFAULT_SHORT_WAIT);	
		System.out.println("Total size of price list is : "+priceList.size());
		
		String productPrice=priceList.get(0).getText();
		String exTaxPrice=priceList.get(1).getText().split(":")[1].trim();
		
		productInfo.put("Product Price", productPrice);
		productInfo.put("Excluding Tax Price", exTaxPrice);
		
	}
	
	public Map<String, String> getAllProductInfo() {
		
		productInfo=new HashMap<String, String>();
		getProductMetadata();
		getProductPriceData();
		
		System.out.println("Product Info is : \n"+productInfo);
		return productInfo;
	}
	
	
}
