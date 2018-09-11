package com.utility;

import java.util.ArrayList;
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
	static WebDriver driver;
	private static final Logger log = Logger.getLogger(SafeActions.class.getName());
	public static ConfigReader reader = new ConfigReader();

	public SafeActions(WebDriver driver) {
		this.driver = driver;
	}

	public void openUrl(String url) {
		try {
			driver.get(url);

		} catch (WebDriverException e) {
			e.printStackTrace();
			sa.fail("URL not found");
		}

	}

	SoftAssert sa = new SoftAssert();

	public void click_on_the_field(String loc, String elementName, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, 300);
		try {
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loc)));	
			
			WebElement element = driver.findElement(By.xpath(loc));
			element.click();

		} catch (StaleElementReferenceException e) {
		
			Assert.fail(elementName + "is not attached to the page document - StaleElementReferenceException");
		
		} catch (NoSuchElementException e) {

			Assert.fail(elementName + " was not found in DOM in time - Seconds" + " - NoSuchElementException");
			
		} catch (Exception e) {

			Assert.fail(elementName + " was not found on the web page");
						
		}

	}

	public void enter_text_in_texbox(String loc, String value, String elementName) {
		try {
			WebElement element = driver.findElement(By.xpath(loc));
			
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

	public void select_value_in_dropdownByText(String loc, String value, String elementName) {
		// new Select(driver.findElement(By.xpath(loc))).selectByVisibleText(value);
		try {
			Select element = new Select(driver.findElement(By.xpath(loc)));
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
 

	public void validateElemantOnPage(String loc) {

		if (driver.findElement(By.xpath(loc)).isDisplayed()) {

		}
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
	public String collectData(String loc, String elementName) {
		String errorContent = " ";
		try {
			String data = driver.findElement(By.xpath(loc)).getText();
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

	public List<WebElement> collectAllElements(String loc, int timeout) throws InterruptedException {

		Thread.sleep(timeout);
		List<WebElement> ertNames = driver.findElements(By.xpath(loc));

		return ertNames;
	}

	public void markAsperERTAndPerson(String string, int personNumber) {

		WebDriverWait wait = new WebDriverWait(driver, 300);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(text(), 'ERT-" + string
				+ "DAY-HYD')]/following::table/tbody/tr[" + personNumber + "]/td/div[4]/div/button[1]")));
		//Actions action = new Actions(driver);
		
		  click_on_the_field("//label[contains(text(), 'ERT-" + string +"DAY-HYD')]/following::table/tbody/tr["+personNumber+"]/td/div[4]/div/button[1]", "Attendance", 3000);
		 
		/*action.moveToElement(driver.findElement(By.xpath("//label[contains(text(), 'ERT-" + string
				+ "-KCH')]/following::table/tbody/tr[" + personNumber + "]/td/div[4]/div/button[1]"))).click().build()
				.perform();*/

	}

	

	public Boolean checkCondition(String string, int personNumber) {

		WebDriverWait wait = new WebDriverWait(driver, 300);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(text(), 'ERT-" + string
				+ "DAY-HYD')]/following::table/tbody/tr[" + personNumber + "]/td/div[4]/div/button[2]")));

		Boolean text = driver.findElement(By.xpath("//label[contains(text(), 'ERT-" + string
				+ "DAY-HYD')]/following::table/tbody/tr[" + personNumber + "]/td/div[4]/div/button[2]")).getText()
				.equals("Clear Attendance");
		return text;
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

	static List<String> erts;

	public static  List<String> getStatusOfEachVehicle() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 300);
		String path = reader.get("noOfERTs");

		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(path)));
		erts = new ArrayList<String>();

		int noOfERTss = driver.findElements(By.xpath(path)).size();
		 System.out.println(noOfERTss);

		for (int i = 0; i < noOfERTss; i++) {
			Thread.sleep(1000);
			// System.out.println(driver.findElements(By.xpath(path)).get(i).getText());
			String text = driver.findElements(By.xpath(path)).get(i).getText();
			String texter = text.substring(4, 6);
			erts.add(i, texter);

		}
		return erts;
	}
	
	public String getText(String loc) {
		WebElement element = driver.findElement(By.xpath(loc));
		String text = element.getText();
		System.out.println(text);
		return text;

	}
	
	public List<String> getElementsFromDropdown(String loc, String elementName) {
		WebDriverWait wait = new WebDriverWait(driver, 300);
		List<String> errorContent = null;
		erts = new ArrayList<String>();
	try {		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loc)));
		List<WebElement> text = new Select(driver.findElement(By.xpath("//select[@id='vehicledrpdwn']"))).getOptions();
		//System.err.println(text.get);
		for (int i = 0; i < text.size(); i++) {
			String text1 = text.get(i).getText();
			erts.add(text1);
		}
			System.out.println(erts);
			return erts;
		
	} catch (StaleElementReferenceException e) {
		Assert.fail(elementName + "is not attached to the page document - StaleElementReferenceException");		
		return errorContent;
	} catch (NoSuchElementException e) {
		Assert.fail(elementName + " was not found in DOM in time - Seconds" + " - NoSuchElementException");		
		return errorContent;
	} catch (Exception e) {
		Assert.fail(elementName + " was not found on the web page");
		return errorContent;
	}
	}
	
}
