package sel;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;

public class FirsDriver {
	
	static WebDriver driver;
	static JavascriptExecutor js;

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		ReadObject reader = new ReadObject();
		Properties p = reader.getConfiguration();
		//String browser = "Chrome";
		
		//Read from file the browser name.
		String browser = p.getProperty("browser");
		
		if (browser.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}else if(browser.equals("Firefox")) {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
			}else if (browser.equals("IE")) {
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\drivers\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			}
		
		//implicit wait time to go head.
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		//Maximize Browser Window.
		driver.manage().window().maximize();
		
		/*//Application under test.
		String url = "http://www.google.com";
		driver.get(url);*/
		
		//Application Under Test and picking up url from file.
		String url = p.getProperty("url");
		driver.get(url);
		
		System.out.println("Browser Title is : "+driver.getTitle());
		
		//Login to Application
		driver.findElement(By.name("userName")).clear();
		driver.findElement(By.name("userName")).sendKeys("mercury");
		
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("mercury");
		
		//Click on Login using findElement method.
		//driver.findElement(By.name("login")).click();
		
		//Clcik on Login using javascriptexecutor.
		js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", driver.findElement(By.name("login")));
		
		//Verify sign-off web element
		if(driver.findElement(By.linkText("SIGN-OFF")).isDisplayed()) {
			System.out.println("Status -- Passed | Login Successful");
		}else {
			System.out.println("Status -- Faild | Login Failed.");
		}
		
		//Select one way trip type
		
		js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//input[@name=\"tripType\"][@value=\"oneway\"]")));
		
		//Select Departure From
		Select flyFrom = new Select(driver.findElement(By.name("fromPort")));
		flyFrom.selectByValue("London");
		
		//Select Arrival 
		Select flyTo = new Select(driver.findElement(By.name("toPort")));
		flyTo.selectByValue("New York");
						
		//Select Class Preferences
		js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//input[@name=\"servClass\"][@value=\"Business\"]")));
		
		//Select Airline Preferences
		Select airLine = new Select(driver.findElement(By.name("airline")));
		airLine.selectByIndex(2);
		
		//Click on Continue
		js.executeScript("arguments[0].click();", driver.findElement(By.name("findFlights")));
		
		
		//Flights Found
		if(driver.getTitle().equals("Select a Flight: Mercury Tours")) {
			System.out.println("Status -- Passed | Flights Found Successfull");
		}else {
			System.out.println("Status -- Failed | Flight Found NOT Successful");
		}
		
		//Click Continue
		js.executeScript("arguments[0].click();", driver.findElement(By.name("reserveFlights")));
		
		//Enter First Name
		driver.findElement(By.name("passFirst0")).clear();
		driver.findElement(By.name("passFirst0")).sendKeys("Zohaib");
		
		//Enter Second Name
		driver.findElement(By.name("passLast0")).clear();
		driver.findElement(By.name("passLast0")).sendKeys("Mukhtar");
		
		//Enter Credit Card number
		driver.findElement(By.name("creditnumber")).clear();
		driver.findElement(By.name("creditnumber")).sendKeys("5430000011112222");
		
		//Click Secure Purchase
		driver.findElement(By.name("buyFlights")).click();
		
		//Verify booking confirmation.
		String actConText = driver.findElement(By.xpath("//font[@size='2']")).getText();
		if(actConText.contains("itinerary has been booked!")) {
			System.out.println("Status -- Passed | Ticket has been booked successfuly");
		}else {
			System.out.println("Status -- Failed | Ticket has not been booked successfuly");
		}
		
		
		Thread.sleep(5000);
		driver.quit();
	}

}
