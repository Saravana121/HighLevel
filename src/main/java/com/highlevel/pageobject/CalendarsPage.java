package com.highlevel.pageobject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.highlevel.base.BaseTest;


/**
 * @author Saravana
 * This page class contains login and calendar page web elements and its implemented methods
 */
public class CalendarsPage extends BaseTest {
	public CalendarsPage() {
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "sb_calendars")
	private WebElement calendarsFromLeftPanel;

	@FindBy(id = "tb_apppontment-tab")
	private WebElement apppontmentTab;

	@FindBy(id = "email")
	private WebElement emailTxtField;

	@FindBy(id = "password")
	private WebElement paswordTxtField;

	@FindBy(xpath = "//button[text()='Sign in' and @type='button']")
	private WebElement signInBtn;

	@FindBy(xpath = "//button[@title='Confirmed']")
	private WebElement actionsDrpdwn;

	@FindBy(xpath = "//a[@class='dropdown-item']//span[text()='Delete']")
	private WebElement deleteAction;

	@FindBy(xpath = "(//section[@id='calendarEvents']//tr)[2]/td[3]")
	private WebElement requestedTime;

	
	/**
	 * This method used to login to the High Level site, To login need to pass following params,
	 * @param url
	 * @param userName
	 * @param password
	 */
	public void loginToHighLevel(String url, String userName, String password) {
		driver.get(url);
		sendKeys(emailTxtField, userName);
		sendKeys(paswordTxtField, password);
		click(signInBtn);

	}

	/**
	 * This method used to get the business time zone appointment date and time  
	 * @return requested date and time in business time zone 
	 */
	public String getAppointmentDateTimeInBusinessTimeZone() {
		waitForElement(calendarsFromLeftPanel);
		click(calendarsFromLeftPanel);
		waitForElement(apppontmentTab);
		click(apppontmentTab);
		waitForElement(requestedTime);
		return requestedTime.getText();
	}

	/**
	 * This method used to delete the scheduled appointments. 
	 */
	public void deleteAppointment() {
		click(actionsDrpdwn);
		waitForElement(deleteAction);
		click(deleteAction);
		driver.switchTo().alert().accept();
	}

}
