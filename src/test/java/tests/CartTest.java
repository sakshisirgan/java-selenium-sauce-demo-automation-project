package tests;

import com.aventstack.extentreports.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.ProductPage;
import pages.CartPage;
import utils.ExtentManager;
import utils.ScreenShotUtils;

public class CartTest {

    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;
    LoginPage loginPage;
    ProductPage productPage;
    CartPage cartPage;

    @BeforeTest
    public void startReport() {
        extent = ExtentManager.getExtentReports();
    }

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);

        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isOnProductsPage(), "Login failed!");
    }

    @Test
    public void testCartEmptyInitially() {
        test = extent.createTest("Cart Empty Initially Test");

        cartPage.openCart();
        boolean isEmpty = cartPage.isCartEmpty();

        test.info("Cart empty status: " + isEmpty);
        Assert.assertTrue(isEmpty, "Cart is not empty initially!");
    }

    @Test
    public void testAddProductToCart() throws InterruptedException {
        test = extent.createTest("Add Product To Cart Test");

        productPage.addProductToCart();
        cartPage.openCart();
        String itemName = cartPage.getCartItemName();

        test.info("Item in cart: " + itemName);
        Assert.assertTrue(itemName.contains("Sauce Labs Bike Light"),
                "Expected Sauce Labs Bike Light but found: " + itemName);
    }

    @Test
    public void testCheckoutButtonVisible() throws InterruptedException {
        test = extent.createTest("Checkout Button Visibility Test");

        productPage.addProductToCart();
        cartPage.openCart();
        boolean checkoutVisible = cartPage.isCheckoutButtonVisible();

        test.info("Checkout button visible: " + checkoutVisible);
        Assert.assertTrue(checkoutVisible, "Checkout button not visible after adding product!");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        String screenshotPath = ScreenShotUtils.takeScreenshot(driver, result.getName());

        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail("Test Failed").addScreenCaptureFromPath(screenshotPath);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test Passed").addScreenCaptureFromPath(screenshotPath);
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.skip("Test Skipped");
        }

        driver.quit();
    }

    @AfterTest
    public void endReport() {
        extent.flush();
    }
}
