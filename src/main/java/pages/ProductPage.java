package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {

	WebDriver driver;
	WebDriverWait wait;
	
	public By cartItemsBadge = By.cssSelector(".shopping_cart_badge");
	public By addProductButton = By.id("add-to-cart-sauce-labs-bike-light");
	public By productTitleLink = By.id("item_4_title_link");

	public ProductPage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }
	
	public void addProductToCart() {
	    wait.until(ExpectedConditions.elementToBeClickable(addProductButton)).click();
	}

	public void clickOnProduct() {
	    wait.until(ExpectedConditions.elementToBeClickable(productTitleLink)).click();
	}

	public int getCartCount() {
		try {
			String count = driver.findElement(cartItemsBadge).getText();
			return Integer.parseInt(count);
		} catch (Exception e) {
			return 0; 
		}
	}
}
