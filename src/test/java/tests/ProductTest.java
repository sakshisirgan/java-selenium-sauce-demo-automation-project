package tests;

import org.testng.annotations.*;
import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import pages.ProductPage;
import pages.LoginPage;
import utils.ScreenShotUtils;
import core.DriverFactory;
import utils.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ProductTest {

    WebDriver driver;
    ProductPage productPage;
    LoginPage loginPage;
    ExtentReports extent;
    ExtentTest test;

    @BeforeMethod
    public void setup() {
        driver = DriverFactory.getDriver("chrome");
        extent = ExtentManager.getExtentReports();

        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        productPage = new ProductPage(driver);
    }

    @Test(priority = 1)
    public void testAddProductToCart() {
        test = extent.createTest("Add Product to Cart");
        productPage.addProductToCart();
        int count = productPage.getCartCount();
        Assert.assertEquals(count, 1);
        ScreenShotUtils.takeScreenshot(driver, "AddProductToCart");
        test.pass("Product successfully added to cart");
    }

    @Test(priority = 2)
    public void testClickOnProductDetails() {
        test = extent.createTest("Click Product for Details");
        productPage.clickOnProduct();
        String url = driver.getCurrentUrl();
        Assert.assertTrue(url.contains("inventory-item.html"));
        ScreenShotUtils.takeScreenshot(driver, "ProductDetailsPage");
        test.pass(" User navigated to product details page successfully");
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
        extent.flush();
    }
}