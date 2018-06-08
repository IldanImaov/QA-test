package com.automation.pages;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Scanner;

import com.custom.library.Base;

public class UserPurchaseProgram extends Base {
	Scanner scanner = new Scanner(System.in);
	ArrayList<String> itemList;
	boolean program = false;

	public UserPurchaseProgram() throws IOException {
		prop = new Properties();
		FileInputStream fis = new FileInputStream("src\\test\\resources\\dynamicConfig.properties");
		prop.load(fis);
	}

	public void purchase(Map<Double, String> input) {
		itemList = new ArrayList<String>();
		System.out.println(prop.getProperty("welcome"));
		System.out.println(prop.getProperty("options"));
		for (Entry<Double, String> e : input.entrySet()) {
			itemList.add(e.getValue());
		}
		do {
			String userPriceRange = scanner.next();
			if (userPriceRange.equals("20-30")) {
				System.out.println("you may like these 2 items:" + itemList.get(0) + "\n---\n" + itemList.get(1)
						+ prop.getProperty("goodBye"));
			} else if (userPriceRange.equals("30-50")) {
				System.out.println("you may like these 2 items:" + itemList.get(2) + "\n---\n" + itemList.get(3)
						+ prop.getProperty("goodBye"));
			} else if (userPriceRange.equals("50-90")) {
				System.out.println("you may like this item:\n--" + itemList.get(4));
			} else {
				System.out.println(prop.getProperty("inputInvalidMessage"));
				program = true;
			}
		} while (program == true);
	}
}
