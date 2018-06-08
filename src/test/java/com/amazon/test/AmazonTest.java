package com.amazon.test;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.annotations.Test;

import com.automation.pages.UserPurchaseProgram;
import com.custom.library.Base;
import com.google.common.collect.Ordering;

public class AmazonTest extends Base {
	UserPurchaseProgram userPurchaseProgram;

	public AmazonTest() throws IOException {
		userPurchaseProgram = new UserPurchaseProgram();
	}

	@Test(description = "Demo Test")
	public void groupSetup() throws Exception {
		hp.searchCaseForIpad("ipad air 2 case");
		Thread.sleep(3000);
		srp.selectPlasticCaseMaterial();
		Thread.sleep(3000);
		srp.enterMinMaxPriceRange("20", "100");
		Thread.sleep(3000);
		srp.outputFirstFiveResult();
		assertTrue(Ordering.natural().isOrdered(srp.sortedByPrice()));
		srp.sortedByRating();
		Thread.sleep(3000);
		userPurchaseProgram.purchase(srp.getSelectedItems());
	}
}
