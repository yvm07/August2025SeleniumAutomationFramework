package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.exceptions.ElementException;

public class ElementUtil {

	private WebDriver driver;
	private Actions action;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		action = new Actions(driver);
		
	}

	public WebElement getElement(By locator) {

		WebElement e = driver.findElement(locator);
		return e;
	}

	public List<WebElement> getElements(By locator) {

		List<WebElement> elList = driver.findElements(locator);
		return elList;
	}

	public void doSendKeys(By locator, String value) {

		if (value == null) {
			throw new ElementException("===Invalid String Value. Null Not Allowed===");
		} else {
			getElement(locator).sendKeys(value);
		}

	}

	public void doMultipleSendKeys(By locator, CharSequence... value) {
		getElement(locator).sendKeys(value);
	}

	public void doClick(By locator) {
		getElement(locator).click();
	}

	public String doElementGetText(By locator) {
		String text = getElement(locator).getText();
		return text;
	}

	public boolean isElementDisplayed(By locator) {

		try {
			getElement(locator).isDisplayed();
			return true;
		} catch (NoSuchElementException e) {
			System.out.println("Locator is incorrect");
			e.printStackTrace();
			return false;
		}

	}

	public boolean isElementEnabled(By locator) {

		try {
			getElement(locator).isEnabled();
			return true;
		} catch (NoSuchElementException e) {
			System.out.println("Element is not interactable or enabled");
			e.printStackTrace();
			return false;
		}

	}

	public String getElementDomPropertyValue(By locator, String attrName) {

		String attrValue = getElement(locator).getDomProperty(attrName);
		return attrValue;
	}

	public String getElementDomAttributeValue(By locator, String attrName) {

		String attrValue = getElement(locator).getDomAttribute(attrName);
		return attrValue;
	}

	public List<String> getElementsTextList(By locator) {
		List<WebElement> elList = getElements(locator);

		List<String> textList = new ArrayList<String>();

		for (WebElement e : elList) {
			String text = e.getText();

			if (text.length() != 0) {
				textList.add(text);
			}
		}

		return textList;
	}

	public boolean isElementExist(By locator) {
		int count = 0;
		try {
			count = getTotalElementsCount(locator);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (count == 1) {
			System.out.println("Element size is 1");
			return true;
		} else {
			System.out.println("Element is not available");
			return false;
		}
	}

	public boolean isElementExist(By locator, int expectedCount) {
		int count = getTotalElementsCount(locator);

		if (count == expectedCount) {
			System.out.println("Element size is : " + expectedCount);
			return true;
		} else {
			System.out.println("Element is not available");
			return false;
		}
	}

	public int getTotalElementsCount(By locator) {

		int size = getElements(locator).size();
		return size;

	}

	public void clickSingleElementFromList(By locator, String value) {
		List<WebElement> elList = getElements(locator);
		for (WebElement e : elList) {
			String text = e.getText();
			if (text.equals(value)) {
				e.click();
				break;
			}
		}
	}

	// Google Auto Suggestion
	public void doSearchAutoSuggestion(String searchItem, String suggestionItem) throws InterruptedException {

		doSendKeys(By.id("APjFqb"), searchItem);

		Thread.sleep(3000);

		List<WebElement> suggestionList = getElements(
				By.xpath("//ul[@jsname='bw4e9b' and @role='listbox']/li//div[@class='wM6W7d']/span"));
		System.out.println("suggestionList --> " + suggestionList.size());
		for (WebElement e : suggestionList) {

			String text = e.getText();
			System.out.println(text);

			if (text.equals(suggestionItem)) {
				e.click();
				break;
			}
		}

	}

	// ************* Select Class Methods *************************

	public void selectByIndex(int value, By locator) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(value);
	}

	public void selectByValue(String value, By locator) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	public void selectByVisibleText(String value, By locator) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(value);
	}

	public List<String> getDropdownValuesList(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> elList = select.getOptions();

		List<String> countryList = new ArrayList<String>();
		for (WebElement e : elList) {
			String text = e.getText();
			if (!text.equals("Country")) {
				countryList.add(text);
			}
		}
		return countryList;
	}

	public int getDropdownOptionsCount(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> elList = select.getOptions();
		int count = elList.size();
		return count;
	}

	public void selectMultipleByValue(By locator, List<String> valueList) {
		Select select = new Select(getElement(locator));
		if (select.isMultiple()) {
			for (String s : valueList) {
				select.selectByValue(s);
			}
		}
	}

	// ***************************Actions class************************
	public void menuHandlingActionClass(By parentMenu, By subMenu) {

		action.moveToElement(getElement(parentMenu)).perform();

		action.moveToElement(getElement(subMenu)).click().perform();

	}

	public List<WebElement> getAllSelectedOptions(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> getAllSelectedList = select.getAllSelectedOptions();
		return getAllSelectedList;
	}

	public WebElement getFirstSelectedOption(By locator) {
		Select select = new Select(getElement(locator));
		WebElement firstSelected = select.getFirstSelectedOption();
		return firstSelected;
	}

	public void deselectByValue(By locator, String text) {
		Select select = new Select(getElement(locator));
		select.deselectByValue(text);
	}

	public void deselectByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.deselectByIndex(index);
	}

	public void deselectByVisibleText(By locator, String text) {
		Select select = new Select(getElement(locator));
		select.deselectByVisibleText(text);
	}

	public void deselectAll(By locator) {
		Select select = new Select(getElement(locator));
		select.deselectAll();
	}

	public void menuSubMenuHandlingLevel4(By parentMenu, By subMenu1, By subMenu2, By subMenu3) throws InterruptedException {
		doClick(parentMenu);

		Thread.sleep(3000);
		action.moveToElement(getElement(subMenu1)).perform();

		Thread.sleep(3000);

		action.moveToElement(getElement(subMenu2)).perform();

		Thread.sleep(3000);

		action.moveToElement(getElement(subMenu3)).click(getElement(subMenu3)).perform();
	}
	
	public String waitforexactTitle(String expectedValue, int timeOut)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try
		{
		wait.until(ExpectedConditions.titleIs(expectedValue));
		}
		catch(Exception e)
		{
			System.out.println("Expected Title is not matching");
			e.printStackTrace();
		}
		return driver.getTitle();
	}
	
	public String waitforUrlContains(String expectedValue, int timeOut)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try
		{
		wait.until(ExpectedConditions.urlContains(expectedValue));
		}
		catch(Exception e)
		{
			System.out.println("Expected Url is not matching");
			e.printStackTrace();
		}
		return driver.getCurrentUrl();
	}
	
	public WebElement waitforElementVisibility(By locator, int timeOut)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try
		{
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		}
		catch(Exception e)
		{
			System.out.println("Expected Element is not Visible");
			e.printStackTrace();
		}
		
		return driver.findElement(locator);
	}
	
	public List<WebElement> waitforElementsVisibility(By locator, int timeOut)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try
		{
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		}
		catch(Exception e)
		{
			System.out.println("Expected Element is not Visible");
			e.printStackTrace();
		}
		
		return driver.findElements(locator);
	}
	
	public WebElement waitForElementPresence(By locator, int timeOut)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try
		{
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		}
		catch(Exception e)
		{
			System.out.println("Expected Element is not Visible");
			e.printStackTrace();
		}
		return driver.findElement(locator);
	}
	
	public List<WebElement> waitForElementsPresence(By locator, int timeOut)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try
		{
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		}
		catch(Exception e)
		{
			System.out.println("Expected Element is not Visible");
			e.printStackTrace();
		}
		return driver.findElements(locator);
	}

	public void waitForElementClickable(By locator, int timeOut)
	{
     	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	
	}
	

}
