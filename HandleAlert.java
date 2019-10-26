package sel;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class HandleAlert {
	static WebDriver driver;
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		ReadObject reader = new ReadObject();
		Properties p = reader.getConfiguration();
				
		String browser = p.getProperty("browser");
		
		if(browser.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}else if (browser.equals("Firefox")) {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}else if (browser.equals("IE")) {
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\drivers\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			}
		
		//Implicit wait time
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		//Maximize the Browser Window
		driver.manage().window().maximize();
		
		//Launch Application Under Test
		String url = p.getProperty("url");
		driver.get(url);
		
		//Swtich into Frame
		driver.switchTo().frame(driver.findElement(By.id("iframeResult")));
		
		//Click on Try it button
		driver.findElement(By.xpath("//button[text()='Try it']")).click();
		
		Thread.sleep(3000);
		//Get alert Text
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		
		//Print the alert Text
		System.out.println("Alert text is : "+alertText);
		
		//Click OK button of alert
		alert.accept();
		
		Thread.sleep(5000);
		driver.quit();
	}

}
