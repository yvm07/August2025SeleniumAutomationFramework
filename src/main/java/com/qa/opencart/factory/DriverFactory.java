package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.exceptions.FrameworkException;
import com.qa.opencart.utils.AppError;

public class DriverFactory {

	public WebDriver driver;
	public Properties prop;
	
	//a single driver used by every thread in parallel testing can cause issues like resource locking, class execution at same time
	// to avoid this we give a copy of driver to each thread using ThreadLocal class
	
	public static ThreadLocal<WebDriver> tlDriver=new ThreadLocal<WebDriver>();
	
	//4types of logs: info,warn,fatal,error
	private static final Logger log=LogManager.getLogger(DriverFactory.class); 
	
	public OptionsManager optionsManager;
	
	public WebDriver initDriver(Properties prop) 
	{
		String browser=prop.getProperty("browser");
		
		optionsManager=new OptionsManager(prop);
		
		boolean remoteExecution= Boolean.parseBoolean(prop.getProperty("remote"));
		
		//System.out.println("Browser Name is : "+browser);
		log.info("Browser Name is : "+browser);
		
		
		switch(browser.trim().toLowerCase()) {
		case "chrome":
			//driver=new ChromeDriver();
			if(remoteExecution) {
				init_remoteDriver(browser);
			}
			else
			{
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}
			break;
			
		case "edge":
			//driver=new EdgeDriver();
			if(remoteExecution) {
				init_remoteDriver(browser);
			}
			else
			{
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			}
			break;
			
		case "firefox":
			//driver=new FirefoxDriver();
			if(remoteExecution) {
				init_remoteDriver(browser);
			}
			else
			{
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			}
			break;
			
		case "safari":
			//driver=new SafariDriver();
			tlDriver.set(new SafariDriver());
			break;
			
		default:
			//System.out.println(AppError.INVALID_BROWSER_NAME);
			log.warn(AppError.INVALID_BROWSER_NAME);
			throw new FrameworkException("===Invalid Exception===");
		}
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		
		getDriver().get(prop.getProperty("url"));
		return getDriver();
	}
	
	public Properties initProp()
	{
		//mvn clean install -Denv="prod"
		//mvn clean install -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml
		prop=new Properties();
		FileInputStream fp=null;
		
		String envName=System.getProperty("env");
		
		try {
			if(envName==null) {
				log.info("No Env is given hence running the test case on default env : "+envName);
				fp=new FileInputStream("src/test/resources/config/config.properties");
			}
			else {
				
				switch(envName.toLowerCase().trim()) {
				case "prod":
					log.info("Env is given hence running the test case on env : "+envName);
					fp=new FileInputStream("src/test/resources/config/config_prod.properties");
				break;
				
				case "uat":
					log.info("Env is given hence running the test case on env : "+envName);
					fp=new FileInputStream("src/test/resources/config/config_uat.properties");
				break;
				
				default:
					log.info("Incorrect Env given");
					throw new FrameworkException("Incorrect Env Name");
		
				}
				
	
			}
			
			prop.load(fp);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return prop;
	}

	public static WebDriver getDriver() {
		return tlDriver.get();
	}
	
	
	private void init_remoteDriver(String browser) {
		try {
		switch(browser.toLowerCase().trim()) {
		
		case "chrome":
			log.info("Test Cases are running on Remote Webdriver Chrome");
			tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),optionsManager.getChromeOptions()));
			break;
		
		case "firefox":
			log.info("Test Cases are running on Remote Webdriver Firefox");
			tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),optionsManager.getFirefoxOptions()));
			break;
			
		case "edge":
			log.info("Test Cases are running on Remote Webdriver Edge");
			tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),optionsManager.getEdgeOptions()));
			break;
			
			default:
				log.error("INCORRECT BROWSER NAME");
				throw new FrameworkException("========Incorrect Browser==============");
				
		}
		}catch(MalformedURLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	public static File getScreenshotAsFile() {
		
		File file=((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		return file;
	
	}
}
