package com.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    protected WebDriver driver;

    @BeforeTest
    public void setupDriver(ITestContext ctx) throws MalformedURLException {
        // BROWSER => chrome / firefox
        // HUB_HOST => localhost / 10.0.1.3 / hostname
        // https://svdoscience.com/2021-03-17/fix-session-deleted-page-crash-selenium-grid-chrome-docker
       // String host = "localhost";
        String host = "192.168.1.115";
        DesiredCapabilities dc = new DesiredCapabilities();
        ChromeOptions options = new ChromeOptions();
        String testName = ctx.getCurrentXmlTest().getName();

        String completeUrl = "http://" + host + ":4444/wd/hub";
        dc.setCapability("name", testName);

        if(System.getProperty("BROWSER") != null &&
                System.getProperty("BROWSER").equalsIgnoreCase("firefox")){
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions foxOpt=new FirefoxOptions();
            dc = DesiredCapabilities.firefox();
            this.driver = new RemoteWebDriver(new URL(completeUrl), dc);
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        }else{
          //  dc = DesiredCapabilities.chrome();
            WebDriverManager.chromedriver().setup() ;
            options.setCapability(CapabilityType.BROWSER_NAME, "chrome");
            options.addArguments("--headless");
            options.addArguments("--whitelisted-ips");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-dev-shm-usage");
           // this.driver = new ChromeDriver(options);
            this.driver = new RemoteWebDriver(new URL(completeUrl), options);
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        }

        if(System.getProperty("HUB-HOST") != null)
        { host = System.getProperty("HUB_HOST");
        }

    }

    @AfterTest
    public void quitDriver(){
        this.driver.quit();
    }



}