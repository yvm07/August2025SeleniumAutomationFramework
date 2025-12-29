
package com.qa.opencart.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

	// POM : Page Object Model

	// Every page will have three things:

	// 1. private By locators
	// 2. public page class constructor
	// 3. page class action methods

	// private By locators

	private final By email = By.id("input-email");
	private final By password = By.id("input-password");
	private final By loginBtn = By.xpath("//input[@value='Login']");
	private final By forgotPwdLink = By.xpath("//a[text()='Forgotten Password']");
	private final By header = By.tagName("h2");
	private final By register = By.xpath("(//a[text()='Register'])[2]");
	private final By warningErrorMessage = By.xpath("//div[@class='alert alert-danger alert-dismissible']");

	private static final Logger log = LogManager.getLogger(LoginPage.class);

	private WebDriver driver;

	private ElementUtil eUtil;

	// public constructor

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eUtil = new ElementUtil(driver);
	}

	// Public methods

	public String getLoginPageTitle() {
		String title = eUtil.waitforexactTitle("Account Title", AppConstants.DEFAULT_SHORT_WAIT);
		// System.out.println("Login Page title is : "+title);
		log.info("Login Page title is : " + title);
		return title;
	}

	@Step("This is the Login Page Url Method")
	public String getLoginPageUrl() {
		String url = eUtil.waitforUrlContains("account/login", AppConstants.DEFAULT_SHORT_WAIT);
		// System.out.println("Login Page URL is : "+url);
		log.info("Login Page URL is : " + url);
		return url;
	}

	public boolean isForgotPwdLinkExist() {

		boolean flag = eUtil.isElementDisplayed(forgotPwdLink);
		return flag;
	}

	public boolean isHeaderExist() {

		boolean flag = eUtil.isElementDisplayed(header);
		return flag;
	}

	public AccountPage doLogin(String uname, String pword) {
		// System.out.println("App Credentials are : "+uname+ " : "+pword);
		log.info("App Credentials are : " + uname + " : " + pword);

		eUtil.waitforElementVisibility(email, AppConstants.DEFAULT_SHORT_WAIT);
		eUtil.doSendKeys(email, uname);
		eUtil.doSendKeys(password, pword);
		eUtil.doClick(loginBtn);

		eUtil.waitforexactTitle("My Account", AppConstants.DEFAULT_SHORT_WAIT);

		return new AccountPage(driver);
	}

	public boolean doLoginwithInvalidCreds(String inUname, String inPass) {

		log.info("Invalid App Credentials are : " + inUname + " :" + inPass);

		WebElement el = eUtil.waitForElementPresence(email, AppConstants.DEFAULT_SHORT_WAIT);
		el.clear();
		el.sendKeys(inUname);
		WebElement el2 = eUtil.waitforElementVisibility(password, AppConstants.DEFAULT_SHORT_WAIT);
		el2.clear();
		eUtil.doSendKeys(password, inPass);
		eUtil.waitForElementClickable(loginBtn, AppConstants.DEFAULT_SHORT_WAIT);

		String error = eUtil.waitForElementPresence(warningErrorMessage, AppConstants.DEFAULT_SHORT_WAIT).getText();

		log.error("Login Error Message is : " + error);

		if (error.contains(AppConstants.LOGIN_MAX_ATTEMPT_MESSG)) {
			return true;
		} else if (error.contains(AppConstants.LOGIN_INVALID_ERROR_MESSG)) {
			return true;
		}
		return false;
	}

	public RegisterPage navigateToRegisterPage() {
		eUtil.waitForElementPresence(register, AppConstants.DEFAULT_SHORT_WAIT).click();
		return new RegisterPage(driver);
	}

}
