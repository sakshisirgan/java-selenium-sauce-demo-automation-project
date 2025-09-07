package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.ProductPage;
import pages.CartPage;
import pages.CheckoutPage;
import utils.ExtentManager;
import utils.ScreenShotUtils;
import org.openqa.selenium.WebDriver;
import core.DriverFactory;

public class IntegrationTest {

    private WebDriver driver;
    private ExtentReports extent;
    private ExtentTest test;

    LoginPage loginPage;
    ProductPage productPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;

    @BeforeTest
    public void setupReport() {
        extent = ExtentManager.getExtentReports();
    }

    @BeforeMethod
    public void setup() {
        driver = DriverFactory.getDriver("chrome");
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    
    @Test
    public void testSauceDemoFlow() throws InterruptedException {
        test = extent.createTest("Integration Test for Sauce Demo");

        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isOnProductsPage(), "Login failed!");

        productPage.addProductToCart();
        Thread.sleep(1000);
        Assert.assertTrue(productPage.getCartCount() > 0, "Product not added!");

        cartPage.openCart();

        boolean checkoutVisible = false;
        for (int i = 0; i < 10; i++) {
            if (driver.findElements(cartPage.checkoutButton).size() > 0) {
                checkoutVisible = true;
                break;
            }
            Thread.sleep(500);
        }
        Assert.assertTrue(checkoutVisible, "Checkout button not visible!");

        driver.findElement(cartPage.checkoutButton).click();
        checkoutPage.enterCheckoutInfo("Lata", "Jain", "12345");
        checkoutPage.clickContinue();
        checkoutPage.clickFinish();

        String msg = checkoutPage.getSuccessMessage();
        Assert.assertEquals(msg, "Thank you for your order!", "Order not completed!");
    }


    @AfterMethod
    public void tearDown(org.testng.ITestResult result) {
        String screenshotPath = ScreenShotUtils.takeScreenshot(driver, result.getName());
        if (result.getStatus() == org.testng.ITestResult.FAILURE) {
            test.fail(result.getThrowable()).addScreenCaptureFromPath(screenshotPath);
        } else if (result.getStatus() == org.testng.ITestResult.SUCCESS) {
            test.pass("Test Passed").addScreenCaptureFromPath(screenshotPath);
        }
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterTest
    public void flushReport() {
        extent.flush();
    }
}
