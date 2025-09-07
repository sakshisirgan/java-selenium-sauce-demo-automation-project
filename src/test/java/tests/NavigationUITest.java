package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import core.DriverFactory;
import utils.ExtentManager;
import utils.ScreenShotUtils;

public class NavigationUITest {

    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setup() {
        driver = DriverFactory.getDriver("chrome");
        driver.get("https://www.saucedemo.com/");
        extent = ExtentManager.getExtentReports();

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
    }

    @Test(priority = 1)
    public void testVerifyBurgerMenuPresence() {
        test = extent.createTest("Verify Presence of Burger Menu");

        By burgerMenu = By.id("react-burger-menu-btn");
        Assert.assertTrue(driver.findElement(burgerMenu).isDisplayed(), 
                          "Burger menu is not visible");

        String path = ScreenShotUtils.takeScreenshot(driver, "BurgerMenuPresence");
        test.pass("Burger menu is visible").addScreenCaptureFromPath(path);
    }

    @AfterClass
    public void teardown() {
        if (driver != null) driver.quit();
        extent.flush();
    }
}
