package com.highlevel.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * @author Saravana,  
 * This java class contains launching browser, fetching data
 *         from property file and also have generic Selenium methods
 */
public class BaseTest {
	public static WebDriver driver = null;
	public static Properties prop = null;
	public int timeout = 30;
	protected String firstName;
	protected String lastName;
	protected String phone;
	protected String mail;
	protected String url;
	protected String userName;
	protected String password;
	
/**
 * This method used for fetching data from property file
 * @throws IOException
 */
	public void readFromProp() throws IOException {
		prop = new Properties();
		FileInputStream file = new FileInputStream("src/test/resource/prop.properties");
		prop.load(file);
		firstName = prop.getProperty("firstName");
		lastName = prop.getProperty("lastName");
		phone = prop.getProperty("phone");
		mail = prop.getProperty("mail");
		url = prop.getProperty("url");
		userName = prop.getProperty("userName");
		password = prop.getProperty("password");
		System.out.println("Property file read sucessfully");
	}

	/**
	 * This method used for launching the chrome browser and navigate to highlevel appointment page
	 * @throws IOException
	 */
	@BeforeClass(alwaysRun = true)
	public void launchBrowser() throws IOException {
		readFromProp();
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		String appointmentURL = prop.getProperty("appointmentUrl");
		driver.get(appointmentURL);

	}

	/**
	 * This method used for closing all the chrome browser tabs
	 */
	@AfterClass(alwaysRun = true)
	public void closeBrowser() {
		driver.close();
		driver.quit();
	}

	/**
	 * This method used to click the web elements
	 * @param web
	 */
	public void click(WebElement web) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.elementToBeClickable(web));
			web.click();
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	/**
	 * This method used to wait till the web elements to visible
	 * @param web
	 * @return boolean value
	 */
	public boolean waitForElement(WebElement web) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.visibilityOf(web));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * This method used to enter the texts input in text fields using sendkeys function
	 * @param web
	 * @param txt
	 */
	public void sendKeys(WebElement web, String txt) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.elementToBeClickable(web));
			Thread.sleep(1000);
			web.sendKeys(txt);
			;
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

}
