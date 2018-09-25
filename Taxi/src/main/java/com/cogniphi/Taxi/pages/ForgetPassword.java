package com.cogniphi.Taxi.pages;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.utility.BaseClass;
import com.utility.SafeActions;

public class ForgetPassword extends BaseClass {
	public static final Logger log = Logger.getLogger(ForgetPassword.class.getName());
	SafeActions action = new SafeActions(driver);

	public void verifyAccountPage() {

		action.enter_text_in_texbox(By.xpath(reader.get("username_xpath")), reader.get("UserName"), "UserName");
		log.info("Username Entered");
		action.click_on_the_field(By.xpath(reader.get("forgetPassword_xpath")), "ForgotPassword Link");
		log.info("Clicked on Forgot Password");
		String text1 = driver.getTitle();
		Assert.assertEquals("Forgot/Reset", text1);
		log.info("Verified Title");

	}

	public void verifyMobileNumber(String number) throws InterruptedException {
		/*
		 * String window = driver.getWindowHandle(); System.out.println(window);
		 * driver.switchTo().window(window);
		 */
		action.enter_text_in_texbox(By.xpath(reader.get("mobileNumber_xpath")), number, "MobileNumber");
		log.info("Entered last 4 digits of mobile number");
		action.click_on_the_field(By.id(reader.get("nextButton_id")), "Next Button");
		log.info("Clicked on next button");
		/*
		 * WebDriverWait wait = new WebDriverWait(driver, 100);
		 * wait.until(ExpectedConditions.alertIsPresent());
		 */
		Thread.sleep(4000);
		// driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// driver.switchTo().alert().accept();

	}

	public void validateAlert(String actualText) {
		String alertText = action.collectData(By.xpath(reader.get("alertText_xpath")), "alertText");
		action.click_on_the_field(By.xpath("//button[contains(text(), 'Ok')]"), "Ok Button");
		System.out.println(alertText);
		Assert.assertEquals(actualText, alertText);
	}

	public void removeData() {
		action.clearDataFrom(By.xpath(reader.get("mobileNumber_xpath")), "MobileNumberField");
	}

	public void verifyCancelFunctionality() {

	}

	public void verifyOTP() {
		Scanner sc = new Scanner(System.in);
		String otp = sc.nextLine();
		action.enter_text_in_texbox(By.id(reader.get("otp_id")), otp, "OTP Field");
		action.click_on_the_field(By.id(reader.get("nextButton_id")), "Verify Button");
	}

	public void selectNewPassword() {
		Scanner scanner = new Scanner(System.in);
		String newPassword = scanner.nextLine();
		action.enter_text_in_texbox(By.id(reader.get("newPassword_id")), newPassword, "Newpassword");
		String confirmPassword = scanner.nextLine();
		action.enter_text_in_texbox(By.name(reader.get("confirmPassword_name")), confirmPassword, "Newpassword");
		//action.click_on_the_field(By.id(reader.get("nextButton_id")), "Verify Button");
		action.click_on_the_field(By.id(reader.get("change_id")), "Change Button");
		
	}
	public void verifyConfirmAlert() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(reader.get("confirmSuccessAlertText_xpath"))));
		String text = action.collectData(By.xpath(reader.get("confirmSuccessAlertText_xpath")), "ConfirmAlert");
		//String text = driver.switchTo().alert().getText();
		Assert.assertEquals(text, reader.get("confirmSuccessAlertText"));
		action.click_on_the_field(By.id(reader.get("continue_id")), "Continue Button");
	}
	public void verifyConfirmMesage() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(reader.get(""))));
		String text = action.collectData(By.xpath(reader.get("")), "ConfirmationMessage");
		//String text = driver.switchTo().alert().getText();
		Assert.assertEquals(text, reader.get(""));
		action.click_on_the_field(By.id(reader.get("")), "OK Button");
	}
}
