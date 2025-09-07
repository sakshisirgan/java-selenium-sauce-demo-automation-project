package tests;

import com.aventstack.extentreports.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.ProductPage;
import pages.CartPage;
import pages.CheckoutPage;
import utils.ExtentManager;
import utils.ScreenShotUtils;

public class CheckoutTest {

    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;
    LoginPage loginPage;
    ProductPage productPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;

    @BeforeTest
    public void startReport() {
        extent = ExtentManager.getExtentReports();
    }

    @BeforeMethod
    public void setup() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);

        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        productPage.addProductToCart();
        cartPage.openCart();
    }

    @Test
    public void testCheckoutWithValidDetails() {
        test = extent.createTest("Checkout With Valid Details Test");

        driver.findElement(cartPage.checkoutButton).click();
        checkoutPage.enterCheckoutInfo("Lata", "Jain", "12345");
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();

        String message = checkoutPage.getSuccessMessage();
        test.info("Checkout success message: " + message);

        Assert.assertEquals(message, "Thank you for your order!", "Checkout not completed!");
    }

    @Test
    public void testCheckoutWithMissingDetails() {
        test = extent.createTest("Checkout With Missing Details Test");

        driver.findElement(cartPage.checkoutButton).click();
        checkoutPage.enterCheckoutInfo("", "Jain", "12345");
        checkoutPage.clickContinue();

        String errorMessage = driver.findElement(By.cssSelector("h3[data-test='error']")).getText();
        test.info("Error message: " + errorMessage);

        Assert.assertTrue(errorMessage.contains("Error"), "Expected error message not shown!");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        String screenshotPath = ScreenShotUtils.takeScreenshot(driver, result.getName());

        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail("Test Failed").addScreenCaptureFromPath(screenshotPath);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test Passed").addScreenCaptureFromPath(screenshotPath);
        }

        driver.quit();
    }

    @AfterTest
    public void endReport() {
        extent.flush();
    }
}
