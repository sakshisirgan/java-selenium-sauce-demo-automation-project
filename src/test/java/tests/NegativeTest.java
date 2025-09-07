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

public class NegativeTest {

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

    @Test
    public void testCheckoutWithEmptyCart() {
        test = extent.createTest("Attempt Checkout With Empty Cart");

        driver.findElement(By.cssSelector(".shopping_cart_link")).click();
        driver.findElement(By.id("checkout")).click();

        boolean cartEmpty = driver.findElements(By.cssSelector(".cart_item")).isEmpty();
        Assert.assertTrue(cartEmpty, "Cart is not empty, test failed!");

        String path = ScreenShotUtils.takeScreenshot(driver, "EmptyCartCheckout");
        test.pass("Checkout attempt with empty cart verified").addScreenCaptureFromPath(path);
    }

    @AfterClass
    public void teardown() {
        if (driver != null) driver.quit();
        extent.flush();
    }
}
