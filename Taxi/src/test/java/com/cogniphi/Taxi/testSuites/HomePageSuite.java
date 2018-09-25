package com.cogniphi.Taxi.testSuites;

import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cogniphi.Taxi.pages.ForgetPassword;
import com.cogniphi.Taxi.pages.LogInPage;
import com.utility.BaseClass;
import com.utility.SafeActions;

import atu.testrecorder.exceptions.ATUTestRecorderException;

public class HomePageSuite extends BaseClass {
LogInPage login ;
ForgetPassword forget;
SafeActions action;
	@Test(groups = { "Sanity", "Regression" })
	public void TC_001_verifyForgotPasswordProvision() throws InterruptedException {
		forget = new ForgetPassword();
		forget.verifyAccountPage();

	}
	
	@Test(groups = {"Regression"})
	public void TC_002_invalidAccountVerification() throws InterruptedException {
		forget = new ForgetPassword();
		forget.verifyMobileNumber(reader.get("invalidMobileNumber"));
		forget.validateAlert(reader.get("errorAlertText"));
		forget.removeData();

	}
	@Test(groups = { "Sanity", "Regression" })
	public void TC_003_AccountVerification() throws InterruptedException {
		forget = new ForgetPassword();
		forget.verifyMobileNumber(reader.get("validMobileNumber"));
		forget.validateAlert(reader.get("successAlertText"));

	}
	@Test(groups = { "Sanity", "Regression" })
	public void TC_004_OTPVerification() throws InterruptedException {
		forget = new ForgetPassword();
		forget.verifyOTP();

	}
	@Test(groups = { "Sanity", "Regression" })
	public void TC_005_changePassword() throws InterruptedException {
		forget = new ForgetPassword();
		forget.selectNewPassword();
		forget.verifyConfirmAlert();
		forget.verifyConfirmMesage();

	}

	@BeforeClass
	public void beforeClass() throws ATUTestRecorderException {
		//login = new LogInPage();		
		LogInPage.openChromeBrowser();
	}

	@AfterTest
	public void afterClass() {
		//driver.quit();
	}
	
}
