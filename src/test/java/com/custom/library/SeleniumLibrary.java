package com.custom.library;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumLibrary {

	private WebDriver driver;
	private Properties prop;
	public boolean isDemoMode = false;

	public SeleniumLibrary(WebDriver _driver) {
		driver = _driver;
	}

	public WebDriver initializeDriver(String browser) throws Exception {
		prop = new Properties();
		FileInputStream fis = new FileInputStream("src\\test\\resources\\dynamicConfig.properties");
		prop.load(fis);
		String browserName = prop.getProperty("browser");
		if (browserName.equals("chrome")) {
		//	System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browserName.equals("firefox")) {// webdriver.ie.driver
			System.setProperty("webdriver.gecko.driver", "src\\test\\resources\\geckodriver.exe");
			driver = new FirefoxDriver();
		} else if (browserName.equals("IE")) {
			System.setProperty("webdriver.ie.driver", "src\\test\\resources\\IEDriverServer.exe");
			driver = new ChromeDriver();
		}

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		return driver;
	}

	public WebElement waitTillElementVisible(WebElement element) {
		WebElement myDynamicElement = (new WebDriverWait(driver, 60)).until(ExpectedConditions.visibilityOf(element));
		return myDynamicElement;
	}

	public WebElement scrollPageTillElementVisible(WebElement Element) {
		WebElement targetElem = null;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", Element);
		return targetElem;
	}

	public void customWait(double inSeconds) throws Exception {
		Thread.sleep((long) (inSeconds * 1000));
	}

	public void highlightElement(WebElement element) throws Exception {
		if (isDemoMode == true) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			customWait(0.5);
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
					"color: red; border: 2px solid red;");
			customWait(0.5);
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
		}
	}

	public void hoverOverTargerElement(WebElement target) {
		Actions a = new Actions(driver);
		a.moveToElement(target).build().perform();
	}

	public double parseIntoNumber(String input) {
		String dollarSymbolRemoved = input.replace("$", "");
		double value = 0;
		if (input.contains(" "))
			value = Double.parseDouble(dollarSymbolRemoved.trim().replace(" ", "."));
		else
			value = Double.parseDouble(dollarSymbolRemoved);
		return value;
	}

	public double parseMixedStringIntoNumber(String input) {
		StringBuilder value = new StringBuilder(input.replace("$", ""));
		StringBuilder stringBuilt = null;
		if (input.contains("(") & input.contains(")")) {
			int startIndex = value.indexOf("(");
			int endIndex = value.indexOf(")");
			stringBuilt = value.delete(startIndex, endIndex + 1);
		}
		return Double.parseDouble(stringBuilt.toString());
	}

	public static boolean isSorted(double[] data) {
		for (int i = 1; i < data.length; i++) {
			if (data[i - 1] > data[i]) {
				return false;
			}
		}
		return true;
	}

}