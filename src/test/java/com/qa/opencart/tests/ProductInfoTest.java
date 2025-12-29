package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;

public class ProductInfoTest extends BaseTest{

	@BeforeClass
	public void productInfoSetUp()
	{
		accPage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@DataProvider
		public Object[][] getProductsData()
		{
			Object obj[][] = new Object[3][2];
			
			obj[0][0] = "macbook";
			obj[0][1] = "MacBook Air";
			
			obj[1][0] = "samsung";
			obj[1][1] = "Samsung SyncMaster 941BW";
					
			obj[2][0] = "canon";
			obj[2][1] = "Canon EOS 5D";
			
			return obj;
		}
	
	
	@Test(dataProvider = "getProductsData")
	public void productHeaderTest(String searchValue, String productName)
	{
		searchResultsPage=accPage.doSearch(searchValue);
		productInfoPage=searchResultsPage.selectProduct(productName);
		
		String actProductHeader=productInfoPage.getProductHeader();
		Assert.assertEquals(actProductHeader, productName);
	}
	
	
	@DataProvider
		public Object[][] getProductImages()
		{
		
			Object obj[][] = {{"macbook","MacBook Air",4},{"samsung","Samsung SyncMaster 941BW",1},{"canon","Canon EOS 5D",3}};
			return obj;
			
		}
	
	
	
	@Test(dataProvider="getProductImages")
	public void productImagesCount(String searchValue, String productName, int imageCount)
	{
		searchResultsPage=accPage.doSearch(searchValue);
		productInfoPage=searchResultsPage.selectProduct(productName);
		
		int actProductImages=productInfoPage.getProductImagesCount();
		Assert.assertEquals(actProductImages, imageCount);
	}
	
	@Test
	public void productInfoTest()
	{
		SoftAssert so = new SoftAssert();
		searchResultsPage=accPage.doSearch("macbook");
		productInfoPage=searchResultsPage.selectProduct("MacBook Pro");
		
		
		Map<String, String> allProductsInfo=productInfoPage.getAllProductInfo();
		so.assertEquals(allProductsInfo.get("Brand"), "Apple");
		so.assertEquals(allProductsInfo.get("Product Code"), "Product 18");
		//specified fail
		so.assertEquals(allProductsInfo.get("Reward Points"), "800");
		so.assertEquals(allProductsInfo.get("Availability"), "Out Of Stock");
		//specified fail
		so.assertEquals(allProductsInfo.get("Product Price"), "$2,000.00");
		so.assertEquals(allProductsInfo.get("Brand"), "Apple");
		
		so.assertAll();
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
