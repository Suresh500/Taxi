package com.utility;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;

public class BaseClass {
	
	public static WebDriver driver;
	public static ExtentReports extent;
	public static ExtentTest test;
	//public static ATUTestRecorder recorder;
	public static ConfigReader reader = new ConfigReader();
	
	static {
		Calendar calender = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		extent = new ExtentReports(System.getProperty("user.dir") + "/reports/Test "+ formater.format(calender.getTime()) + ".html", true);
	}

	public void getResult(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(LogStatus.PASS, result.getName() + "Test Pass");
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, result.getName() + " Test Skipped" + result.getThrowable());
		} else if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.ERROR, result.getName() + " Test Failed" + result.getThrowable());
			File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(src, new File("./screenShots/" + result.getName()+".png"));
		} else if (result.getStatus() == ITestResult.STARTED) {
			test.log(LogStatus.INFO, result.getName() + "Test Started");
		}

	}

	@BeforeTest
	/*public void loadlog4j() {
		
	}
*/
	public static void beforeTest() throws ATUTestRecorderException {
		String path = System.getProperty("user.dir") + "/log4j.properties";
		PropertyConfigurator.configure(path);

		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(reader.get("StageURL"));
		
		
		/*DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH-mm-ss");
		Date date = new Date();
		recorder = new ATUTestRecorder("D:\\Project\\Script Videos", "TestVideo"
				+ dateFormat.format(date), true);
		recorder.start();*/
		
	}

	@BeforeMethod
	public void beforeMethod(Method result) {
		test = extent.startTest(result.getName());
		test.log(LogStatus.INFO, result.getName());
	}

	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException {
		getResult(result);
	}
	@BeforeClass
	public void beforeClass() throws ATUTestRecorderException {
		
	}

	@AfterClass
	public void toEndTest() {
		extent.endTest(test);
		extent.flush();
	}
	@AfterTest
	public void afterTest() throws ATUTestRecorderException {
		/*recorder.stop();*/
		System.gc();
		
	}
	public static void closeBrowser() {
		driver.quit();
	}

}
