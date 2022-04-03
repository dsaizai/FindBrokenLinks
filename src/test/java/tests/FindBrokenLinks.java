package tests;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import methods.TestBase;

public class FindBrokenLinks extends TestBase {
	
	Set<String> brokenLinks = new HashSet<String>();
	int count = 0;
	String fileName = "Suammary_BrokenLinks.txt",
		   URL = "https://www.planittesting.com/nz/home";
	
	
	@BeforeClass
	public void openBrowser() {
		openChrome();
	}
	
	@Test
	public void findBrokenLinksOnPage() throws IOException, InterruptedException {
		//Test if there are broken links on page "https://www.planittesting.com/nz/home" and write the broken links to file "Suammary_BrokenLinks.txt" 
		goToPage(URL);
		
		Thread.sleep(3000);
		
		Set<String> urls = getLinkUrls();
		
		for (String url : urls) {
			if (getHttpStatusCodeForUrl(url) >= 400) {
				brokenLinks.add(url);
				count++;
			}
		}
		
		if (count > 0) {
			writeListToFile(fileName, brokenLinks);
			throw new AssertionError("There are " + count + " broken links on page " + URL);
		}
	}
	
	@AfterClass
	public void closeBrowser() {
		closeChrome();
	}
	
}
