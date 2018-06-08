package com.automation.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.custom.library.Base;

public class SearchResultsPage extends Base {

	public SearchResultsPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	static TreeMap<Double, String> tmap;
	static String itemPrice;
	static String itemName;
	static String itemRating;
	// ===========Refining Elements====================================//
	@FindBy(css = "div#leftNavContainer")
	private WebElement leftNavContainer;
	@FindBy(xpath = "//*[@id=\"leftNavContainer\"]/h3[2]")
	private WebElement refineBy;
	@FindBy(css = "input[name = s-ref-checkbox-8080061011]")
	private WebElement plasticCase;
	@FindBy(id = "low-price")
	private WebElement lowPriceTxtBox;
	@FindBy(id = "high-price")
	private WebElement highPriceTxtBox;
	@FindBy(xpath = "//*[@id=\"a-autoid-76\"]/span/input")
	private WebElement Go;

	// ===========Results Elements================//
	@FindBy(css = "div#resultsCol")
	private WebElement resultPage;
	@FindBy(css = ".s-access-title")
	private List<WebElement> nameList;
	@FindBy(css = ".s-result-item> div > div:nth-child(4) > div > a")
	private List<WebElement> priceList;
	@FindBy(css = ".a-icon-star")
	private List<WebElement> ratingList;

	public void selectPlasticCaseMaterial() throws Exception {
		myLib.waitTillElementVisible(leftNavContainer);
		myLib.highlightElement(leftNavContainer);
		myLib.scrollPageTillElementVisible(plasticCase);
		myLib.highlightElement(plasticCase);
		plasticCase.click();
	}

	public void enterMinMaxPriceRange(String minimum, String maximum) throws Exception {
		myLib.waitTillElementVisible(refineBy);
		myLib.highlightElement(leftNavContainer);
		myLib.scrollPageTillElementVisible(lowPriceTxtBox);
		myLib.highlightElement(lowPriceTxtBox);
		lowPriceTxtBox.sendKeys(minimum);
		highPriceTxtBox.sendKeys(maximum);
		myLib.highlightElement(highPriceTxtBox);
		highPriceTxtBox.sendKeys(Keys.ENTER);
	}

	public void outputFirstFiveResult() throws Exception {
		myLib.waitTillElementVisible(resultPage);
		System.out.println("\n\nBelow are first 5 results:");
		for (int i = 0; i < 5; i++) {
			myLib.highlightElement(nameList.get(i));
			String productName = nameList.get(i).getText();

			myLib.highlightElement(priceList.get(i));
			String productPrice = priceList.get(i).getText();

			myLib.highlightElement(ratingList.get(i));
			String productRating = ratingList.get(i).getAttribute("textContent");

			System.out.printf("\n" + ordinal(i) + " product: " + productName + "\n" + "Price: " + productPrice + "\n"
					+ "Star Rating:" + productRating + "\n");
		}

	}

	public Set<Double> sortedByPrice() {
		System.out.printf(prop.getProperty("lineSeparator") + prop.getProperty("byRating"));
		tmap = new TreeMap<Double, String>();
		for (int i = 0; i < 5; i++) {
			itemPrice = priceList.get(i).getText();
			itemName = nameList.get(i).getText();
			itemRating = ratingList.get(i).getAttribute("textContent");
			String entry = "\n" + itemName + "\n" + itemPrice + "\n" + itemRating;
			try {
				tmap.put(myLib.parseIntoNumber(itemPrice), entry);
			} catch (Exception e) {
				tmap.put(myLib.parseMixedStringIntoNumber(itemPrice), entry);
			}
		}
		for (String r : tmap.values())
			System.out.println(r);
		return tmap.keySet();
	}

	public void sortedByRating() {
		System.out.println(prop.getProperty("lineSeparator") + prop.getProperty("byRating"));
		ArrayList<String> br = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			itemPrice = priceList.get(i).getText();
			itemName = nameList.get(i).getText();
			itemRating = ratingList.get(i).getAttribute("textContent");
			String list = "\n" + itemRating + "\n" + itemName + "\n" + itemPrice;
			br.add(list);
			Collections.sort(br);
		}
		System.out.println(br);
	}

	public Map<Double, String> getSelectedItems() {
		return tmap;
	}

	private String ordinal(int oridinal) {
		String[] sufixes = new String[] { "st", "nd", "rd", "th", "th" };
		return oridinal + 1 + sufixes[oridinal % 10];
	}

}
