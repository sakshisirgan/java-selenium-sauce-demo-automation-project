package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import core.DriverFactory;
import pages.CheckoutPage;
import utils.ScreenShotUtils;
import utils.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class CheckoutTest {

    WebDriver driver;
    CheckoutPage checkoutPage;
    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setup() {
        driver = DriverFactory.getDriver("chrome");
        driver.get("https://www.saucedemo.com/");
        extent = ExtentManager.getExtentReports();
    }

    @Test(priority = 1)
    public void testNavigateToCheckoutPage() {
        test = extent.createTest("Navigate to Checkout Page");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.cssSelector(".shopping_cart_link")).click();

        checkoutPage = new CheckoutPage(driver);
        checkoutPage.clickCheckoutButton();

        Assert.assertTrue(driver.findElement(checkoutPage.firstName).isDisplayed(), 
                          "Checkout page is not displayed");

        String path = ScreenShotUtils.takeScreenshot(driver, "NavigateToCheckoutPage");
        test.pass("Checkout page displayed successfully").addScreenCaptureFromPath(path);
    }

    @Test(priority = 2)
    public void testCheckoutWithValidInformation() {
        test = extent.createTest("Checkout with Valid Information");

        checkoutPage.enterCheckoutInformation("Marie", "Grander", "11223");
        checkoutPage.completeOrder();

        String confirmation = checkoutPage.getConfirmationMessage();
        Assert.assertEquals(confirmation, "Thank you for your order!");

        String path = ScreenShotUtils.takeScreenshot(driver, "CheckoutSuccess");
        test.pass("Order completed successfully").addScreenCaptureFromPath(path);
    }

    @AfterClass
    public void teardown() {
        DriverFactory.quitDriver();
        extent.flush();
    }
}
