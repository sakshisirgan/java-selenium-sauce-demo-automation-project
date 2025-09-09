package tests;

import com.aventstack.extentreports.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.LoginPage;
import utils.ExtentManager;
import utils.ScreenShotUtils;

public class LoginTest {

    WebDriver driver;
    LoginPage loginPage;
    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setUpReport() {
        extent = ExtentManager.getExtentReports();
    }

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);
    }

    @Test(priority = 1)
    public void validLoginTest() {
        test = extent.createTest("Valid Login Test");

        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isOnProductsPage(), "User should land on Products Page");

        String screenshotPath = ScreenShotUtils.takeScreenshot(driver, "validLoginTest");
        test.pass("Valid login successful. Screenshot saved at: " + screenshotPath);
    }

    @Test(priority = 2)
    public void invalidLoginTest() {
        test = extent.createTest("Invalid Login Test");

        loginPage.enterUsername("locked_out_user");
        loginPage.enterPassword("wrong_password");
        loginPage.clickLogin();

        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Epic sadface"), "Error message should appear");

        String screenshotPath = ScreenShotUtils.takeScreenshot(driver, "invalidLoginTest");
        test.pass("Invalid login validated. Screenshot saved at: " + screenshotPath);
    }

    @Test(priority = 3)
    public void logoutTest() {
        test = extent.createTest("Logout Test");

        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isOnProductsPage(), "User should land on Products Page");

        loginPage.logout();
        Assert.assertTrue(loginPage.getCurrentUrl().contains("saucedemo.com"),
                "User should be redirected to login page");

        String screenshotPath = ScreenShotUtils.takeScreenshot(driver, "logoutTest");
        test.pass("Logout successful. Screenshot saved at: " + screenshotPath);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @AfterClass
    public void flushReport() {
        extent.flush();
    }
}
