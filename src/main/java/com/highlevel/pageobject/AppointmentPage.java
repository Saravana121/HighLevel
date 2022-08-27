package com.highlevel.pageobject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.highlevel.base.BaseTest;

/**
 * 
 * @author Saravana This page class contains Appointment page web elements and
 *         its implemented methods
 */
public class AppointmentPage extends BaseTest {

	public AppointmentPage() {
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@class='booking-info-value' and contains(text(),'GMT')]")
	private WebElement timezoneTxt;

	@FindBy(xpath = "//div[contains(@class,'booking-info-value ')]")
	private WebElement dateTxt;

	@FindBy(xpath = "//div[contains(@class,'booking-info-value ')]/span")
	private WebElement timeTxt;

	@FindBy(xpath = "//div[@class='multiselect__tags']")
	private WebElement timezoneDrpdwn;

	@FindBy(xpath = "//li[@class='multiselect__element']//span[@class='option__title']")
	private List<WebElement> timezoneDrpdwnList;

	@FindBy(xpath = "(//li[@class='widgets-time-slot']/span)[1]")
	private WebElement firstTimeSlotBtn;

	@FindBy(xpath = "//button[@class='btn btn-schedule']")
	private WebElement continueBtn;

	@FindBy(id = "first_name")
	private WebElement firstNameTxtField;

	@FindBy(id = "last_name")
	private WebElement lastNameTxtField;

	@FindBy(id = "phone")
	private WebElement phoneTxtField;

	@FindBy(name = "email")
	private WebElement emailTxtField;

	@FindBy(xpath = "//h5[@class='confirmation-message']")
	private WebElement confirmationText;

	/**
	 * This method used to select the time zone randomly
	 */
	public void selectTimezone() {
		click(timezoneDrpdwn);
		Random random = new Random();
		int ranNum = random.nextInt(timezoneDrpdwnList.size());
		click(timezoneDrpdwnList.get(ranNum));
		String timeZone = timezoneTxt.getText();
		Assert.assertTrue(timeZone.contains(timezoneDrpdwnList.get(ranNum).getText()));
		click(firstTimeSlotBtn);
	}

	/**
	 * This method used to schedule the appointment using following params,
	 * 
	 * @param firstName
	 * @param lastName
	 * @param phone
	 * @param mail
	 */
	public void scheduleAppointMent(String firstName, String lastName, String phone, String mail) {
		click(continueBtn);
		click(firstNameTxtField);
		sendKeys(firstNameTxtField, firstName);
		click(lastNameTxtField);
		sendKeys(lastNameTxtField, lastName);
		click(phoneTxtField);
		sendKeys(phoneTxtField, phone);
		click(emailTxtField);
		sendKeys(emailTxtField, mail);
		click(continueBtn);
		waitForElement(confirmationText);
		Assert.assertTrue(confirmationText.getText().contains("Your Meeting has been Scheduled"));
	}

	/**
	 * This method used to convert the scheduled date and time to Business TimeZone
	 * 
	 * @param firstName
	 * @param lastName
	 * @param phone
	 * @param mail
	 * @return converted date and time
	 * @throws ParseException
	 */
	public String convertToBusinessTimeZone(String firstName, String lastName, String phone, String mail)
			throws ParseException {
		selectTimezone();
		click(firstTimeSlotBtn);
		String date = dateTxt.getText();
		String zone = timezoneTxt.getText();
		System.out.println("Scheduled Date is " + date);
		System.out.println("Scheduled time zone is " + zone);

		SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
		Date d = parseFormat.parse(date.substring(20, 28));

		DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy',' hh:mm a");
		outputFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

		DateFormat inputFormat = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
		String inputText = date.substring(0, 3) + " " + date.substring(5, 8) + " " + date.substring(9, 11) + " "
				+ displayFormat.format(d) + " " + zone.substring(0, 9) + " " + date.substring(13, 17);

		Date date1 = inputFormat.parse(inputText);
		String outputText = outputFormat.format(date1);
		System.out.println("Scheduled time after converting to business time zone is " + outputText);

		scheduleAppointMent(firstName, lastName, phone, mail);
		return outputText;

	}

}
