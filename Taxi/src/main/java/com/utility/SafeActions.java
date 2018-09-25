package com.utility;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class SafeActions {
	 WebDriver driver;
	private static final Logger log = Logger.getLogger(SafeActions.class.getName());
	public static ConfigReader reader = new ConfigReader();

	public SafeActions(WebDriver driver) {
		this.driver = driver;
	}

	 public void openUrl(String url) {
		try {
			driver.get(url);

		} catch (WebDriverException e) {
			
			Assert.fail("URL not found");
		}

	}

	SoftAssert sa = new SoftAssert();

	public void click_on_the_field(By loc, String elementName) {
		
		try {		
			
			WebElement element = driver.findElement(loc);
			element.click();

		} catch (StaleElementReferenceException e) {
		
			Assert.fail(elementName + "is not attached to the page document - StaleElementReferenceException");
		
		} catch (NoSuchElementException e) {

			Assert.fail(elementName + " was not found in DOM in time - Seconds" + " - NoSuchElementException");
			
		} catch (Exception e) {

			Assert.fail(elementName + " was not found on the web page");
						
		}

	}

	public void enter_text_in_texbox(By loc, String value, String elementName) {
		try {
			//driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
			WebElement element = driver.findElement(loc);
			
			element.sendKeys(value);
			
		} catch (InvalidSelectorException e) {

			Assert.fail(elementName + " - provided path is wrong");
			
		} catch (StaleElementReferenceException e) {

			Assert.fail(elementName + " - is not attached to the page document - StaleElementReferenceException");
			
		} catch (NoSuchElementException e) {

			Assert.fail(elementName + " - was not found in DOM in time - Seconds" + " - NoSuchElementException");
			
		} catch (Exception e) {

			Assert.fail(elementName + " - was not found on the web page");
			
		}
		
	}

	public void select_value_in_dropdownByText(By loc, String value, String elementName) {
		// new Select(driver.findElement(By.xpath(loc))).selectByVisibleText(value);
		try {
			Select element = new Select(driver.findElement(loc));
			if(elementName.equalsIgnoreCase("selectByText"))
			    element.selectByVisibleText(value);
			else if(elementName.equalsIgnoreCase("selectByValue"))
				element.selectByValue(value);
			else if(elementName.equalsIgnoreCase("selectByIndex"))
				element.selectByIndex(Integer.parseInt(value));
				
		} catch (StaleElementReferenceException e) {
			
			Assert.fail(elementName + " is not attached to the page document - StaleElementReferenceException");
			
		} catch (NoSuchElementException e) {
			
			Assert.fail(elementName + " was not found in DOM in time - Seconds" + " - NoSuchElementException");
			
		} catch (Exception e) {
			
			Assert.fail(elementName + " was not found on the web page");
			
		}
	}
 

	public Boolean validateElemantOnPage(By loc, String elementName) {

		Boolean text = driver.findElement(loc).isDisplayed();

		return text;
	}

	public void enterTOFrame(String loc) {

		try {
			driver.switchTo().frame(loc);
			
		} catch (NoSuchFrameException e) {

			Assert.fail(" Frame has not identified on the page");
			
		}

	}

	public void handleUnExpectedAlerts(String loc) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 300);
			wait.until(ExpectedConditions.alertIsPresent());
			driver.switchTo().alert().accept();
		} catch (NoAlertPresentException e) {

		}
	}

	public void mouseHoverToElement(String loc, String elementName) {

		try {
			Actions act = new Actions(driver);
			act.moveToElement(driver.findElement(By.xpath(loc))).build().perform();
		} catch (StaleElementReferenceException e) {

			Assert.fail(elementName + " is not attached to the page document - StaleElementReferenceException");
			
		} catch (NoSuchElementException e) {

			Assert.fail(elementName + " was not found in DOM in time - Seconds" + " - NoSuchElementException");
			
		} catch (Exception e) {

			Assert.fail(elementName + " was not found on the web page");
			
		}
	}

	public void driverWait(String locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
	}

	public void implicitWait(int timeout) {
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
	}

	public void threadStoper(String timeout) throws NumberFormatException, InterruptedException {
		Thread.sleep(Integer.parseInt(timeout));
	}

	public void refreshThePage() {
		driver.navigate().refresh();
	}

	public void maximise() {
		driver.manage().window().maximize();
	}

	// ---To collect Data from the page---------------\\
	public String collectData(By loc, String elementName) {
		String errorContent = " ";
		try {
			String data = driver.findElement(loc).getText();
			return data;
		} catch (StaleElementReferenceException e) {

			Assert.fail(elementName + " is not attached to the page document - StaleElementReferenceException");
			return errorContent;
		} catch (NoSuchElementException e) {

			Assert.fail(elementName + " was not found in DOM in time - Seconds" + " - NoSuchElementException");
			return errorContent;
		} catch (Exception e) {

			Assert.fail(elementName + " was not found on the web page");
			return errorContent;
		}
	}

	public List<WebElement> collectAllElements(By loc){

		List<WebElement> ertNames = driver.findElements(loc);

		return ertNames;
	}

	

	

	public void scrollUp() {
		/*
		 * JavascriptExecutor js = (JavascriptExecutor) driver;
		 * js.executeScript("window.scrollBy(1000,0)");
		 */
		Actions action = new Actions(driver);
		action.sendKeys(Keys.PAGE_UP).build().perform();
	}

	public void scrollUpWithOffsets() {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,500)");
	}

	public String readData(String loc, String attributeName) {
		WebElement element = driver.findElement(By.xpath(loc));
		String text = element.getAttribute(attributeName);
		System.out.println(text);
		return text;

	}

	
	
	public String getText(String loc) {
		WebElement element = driver.findElement(By.xpath(loc));
		String text = element.getText();
		System.out.println(text);
		return text;

	}
	public String verifyAlertText() {
		String empty = "";
		try {
			WebDriverWait wait = new WebDriverWait(driver, 100);
			wait.until(ExpectedConditions.alertIsPresent());
		String text = driver.switchTo().alert().getText();
		driver.switchTo().alert().accept();
		return text;
		}catch(NoAlertPresentException e) {
			log.info("alerts not available");
			return empty;
		}
	
	}
	public void clearDataFrom(By loc, String elementName) {
		driver.findElement(loc).clear();
	}
	
	
}
