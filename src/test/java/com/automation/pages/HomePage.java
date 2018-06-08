package com.automation.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import com.custom.library.Base;

public class HomePage extends Base{
	
	public HomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "twotabsearchtextbox")
	private WebElement searchTab;
	
	
	
	 

	public void searchCaseForIpad(String item) throws Exception {
		
		myLib.waitTillElementVisible(searchTab);
		myLib.highlightElement(searchTab);
		searchTab.sendKeys(item + Keys.RETURN);
	}
}
