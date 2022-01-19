import com.deque.axe.AXE;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.Collections;

public class TestUnilever {

    WebDriver _driver;
    private static final URL scriptUrl=TestUnilever.class.getResource("/axe.min.js");

    @BeforeMethod
    public void Setup(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT_AND_NOTIFY);
        options.addArguments("enable-automation");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-extensions");
        options.addArguments("--dns-prefetch-disable");
        options.addArguments("--disable-gpu");
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        //options.setExperimentalOption("useAutomationExtension", false);
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        _driver = new ChromeDriver(options);
        _driver.manage().deleteAllCookies();
        _driver.get("https://www.magnumicecream.com/br/home.html");

    }

    @Test
    public void TestAccessibility(){
        var response=new AXE.Builder(_driver,scriptUrl).analyze();
        var violations=response.getJSONArray("violations");

        if(violations.length()==0){
            System.out.println("No Error");
        }
        else{
            AXE.writeResults("Unilever",response);
            Assert.assertTrue(false,AXE.report(violations));
        }
    }

    @AfterMethod
    public void TearDown(){
        _driver.quit();
    }



}
