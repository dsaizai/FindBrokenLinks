package methods;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	
	public static WebDriver driver;
	
	public void openChrome() {
		WebDriverManager.chromedriver().setup();
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-infobars");
		driver = new ChromeDriver(options);

		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	public void goToPage(String url) {
		driver.get(url);
	}
	
	public Set<String> getLinkUrls() {
		//Find all anchor elements in the html body
		List<WebElement> links = driver.findElements(By.xpath("/html/body//child::a"));
		Set<String> urls = new HashSet<String>();
		
		for (WebElement link: links) {
			String url = link.getAttribute("href");
			//Include only web page
			if (url!= null && url.contains("http"))
				urls.add(url);
		}
		
		return urls;
	}
	
	public int getHttpStatusCodeForUrl(String url) throws IOException {
		URL urlObj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
		return con.getResponseCode();
	}
	
	public void writeListToFile(String fileName, Set<String> list) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
		for (String item : list) {
			bw.write(item + "\n");
		}
		bw.flush();
		bw.close();
	}
	
	public void closeChrome() {
		driver.quit();
	}
}
