package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {

    WebDriver driver;
    WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public By usernameInput = By.id("user-name");
    public By passwordInput = By.id("password");
    public By loginButton = By.id("login-button");
    public By errorMessage = By.cssSelector("h3[data-test='error']");
    public By menuButton = By.id("react-burger-menu-btn");
    public By logoutButton = By.id("logout_sidebar_link");

    public void enterUsername(String username) {
        driver.findElement(usernameInput).clear();
        driver.findElement(usernameInput).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public String getErrorMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
        return driver.findElement(errorMessage).getText();
    }

    public boolean isOnProductsPage() {
        return driver.getCurrentUrl().contains("inventory.html");
    }
    
    public String getCurrentUrl() {
    	 return driver.getCurrentUrl();
	}
    
    public void logout() {
        driver.findElement(menuButton).click();
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        driver.findElement(logoutButton).click();
    }
}