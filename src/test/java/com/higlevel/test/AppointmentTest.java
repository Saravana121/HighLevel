package com.higlevel.test;

import java.io.IOException;
import java.text.ParseException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.highlevel.pageobject.AppointmentPage;
import com.highlevel.pageobject.calendersPage;
import com.higlevel.base.BaseTest;

/**
 * This test class verifying the appointment scheduled time is in business time zone or not.
 * @author Saravana
 *
 */
public class AppointmentTest extends BaseTest {

	/**
	 * This testNG test method used to test the appointment scheduled time with any time
	 * zone is getting converted to business time zone or not
	 * 
	 * Pre-Requisite: One user and Calendar for that user should created. 
	 * 
	 * @throws IOException
	 * @throws ParseException
	 * @throws InterruptedException
	 */
	@Test
	public void verifyAppointmentTime() throws ParseException {
		AppointmentPage app = new AppointmentPage();
		calendersPage cal = new calendersPage();
		String convertedDateAndTime = app.convertToBusinessTimeZone(firstName, lastName, phone, mail);
		cal.loginToHighLevel(url, userName, password);
		String dateInBusinessSite = cal.getAppointmentDateTimeInBusinessTimeZone();
		Assert.assertTrue(convertedDateAndTime.equalsIgnoreCase(dateInBusinessSite),
				"Appointment date not converted to IST");
		cal.deleteAppointment();
	}

}
