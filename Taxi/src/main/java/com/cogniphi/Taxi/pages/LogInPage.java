package com.cogniphi.Taxi.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.utility.BaseClass;

import atu.testrecorder.exceptions.ATUTestRecorderException;

public class LogInPage extends BaseClass{
	
public static final Logger log = Logger.getLogger(LogInPage.class.getName());
	
	public static void chrome_LogIn() throws ATUTestRecorderException {

		//BaseClass.beforeTest();
		log.info("chrome browser Launched");
		driver.findElement(By.id(reader.get("username_id"))).sendKeys(reader.get("UserName"));
		log.info("Username Entered");
		driver.findElement(By.id(reader.get("userpwd_id"))).sendKeys(reader.get("Password"));
		log.info("Password entered");
		driver.findElement(By.name(reader.get("LogIn_name"))).click();
		log.info("Clicked on Login button");
		driver.manage().window().maximize();
	}

}
