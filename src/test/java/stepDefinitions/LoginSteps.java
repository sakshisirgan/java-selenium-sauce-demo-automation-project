package stepDefinitions;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import core.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;
import utils.ExtentManager;
import utils.ScreenShotUtils;

public class LoginSteps {

    WebDriver driver;
    LoginPage loginPage;
    ExtentReports extent = ExtentManager.getExtentReports();
    ExtentTest test;

    @Given("User is on login page using {string}")
    public void user_is_on_login_page_using(String browser) {
        driver = DriverFactory.getDriver(browser);
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);
    }

    @Given("User is on login page")
    public void user_is_on_login_page() {
        driver = DriverFactory.getDriver("chrome");
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);
    }

    @Given("User is logged in with {string} and {string}")
    public void user_is_logged_in_with_and(String username, String password) {
        driver = DriverFactory.getDriver("chrome");
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
    }

    @When("User enters {string} and {string}")
    public void user_enters_and(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
    }

    @When("User logs out")
    public void user_logs_out() {
        loginPage.logout();
    }

    @Then("User should be redirected to inventory page")
    public void user_should_be_redirected_to_inventory_page() {
        test = extent.createTest("Login Success Test");
        try {
            Assert.assertTrue(loginPage.isOnProductsPage());
            ScreenShotUtils.takeScreenshot(driver, "Successful Login");
        } catch (AssertionError e) {
            ScreenShotUtils.takeScreenshot(driver, "Failed to login");
            throw e;
        } finally {
            DriverFactory.quitDriver();
            extent.flush();
        }
    }

    @Then("Error message {string} should be displayed")
    public void error_message_should_be_displayed(String expectedMessage) {
        test = extent.createTest("Error Message Test");
        try {
            String actualMessage = loginPage.getErrorMessage();
            Assert.assertTrue(actualMessage.contains(expectedMessage));
            ScreenShotUtils.takeScreenshot(driver, "Error Message"); 
        } catch (AssertionError e) {
            ScreenShotUtils.takeScreenshot(driver, "Error Message:Failed");
            throw e;
        } finally {
            DriverFactory.quitDriver();
            extent.flush();
        }
    }

    @Then("User should be on login page")
    public void user_should_be_on_login_page() {
        test = extent.createTest("Logout Test");
        try {
            Assert.assertTrue(driver.getCurrentUrl().contains("saucedemo.com"));
            ScreenShotUtils.takeScreenshot(driver, "Successfully Logged Out");
        } catch (AssertionError e) {
            ScreenShotUtils.takeScreenshot(driver, "Failed to logout");
            throw e;
        } finally {
            DriverFactory.quitDriver();
            extent.flush();
        }
    }
}