package tests;

import com.aventstack.extentreports.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
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
    public void testAddProductToCart() throws InterruptedException {
        test = extent.createTest("Add Product To Cart Test");

        productPage.addProductToCart(); 
        cartPage.openCart();
        String itemName = cartPage.getCartItemName(); 

        String screenshotPath = ScreenShotUtils.takeScreenshot(driver, "AddProductToCart");
        test.info("Item in cart: " + itemName)
            .addScreenCaptureFromPath(screenshotPath);

        Assert.assertFalse(itemName.isEmpty(), "No product was added to the cart!");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @AfterTest
    public void endReport() {
        extent.flush();
    }
}
