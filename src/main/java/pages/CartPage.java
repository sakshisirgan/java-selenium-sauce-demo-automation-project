package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage {

    WebDriver driver;
    WebDriverWait wait;

    public By cartIcon = By.id("shopping_cart_container");
    public By cartItemName = By.className("inventory_item_name");
    public By checkoutButton = By.id("checkout");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void openCart() {
        driver.findElement(cartIcon).click();
    }

    public String getCartItemName() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartItemName));
        return driver.findElement(cartItemName).getText().trim();
    }

    public boolean isCheckoutButtonVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutButton));
        return driver.findElement(checkoutButton).isDisplayed();
    }

    public boolean isCartEmpty() {
        List<?> items = driver.findElements(cartItemName);
        return items.isEmpty();
    }
}
