package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage {

	WebDriver driver;
	
	public By cartItemsBadge = By.cssSelector(".shopping_cart_badge");
	public By addProductButton = By.id("add-to-cart-sauce-labs-bike-light");
	public By productTitleLink = By.id("item_4_title_link");

	public ProductPage(WebDriver driver) {
		this.driver = driver;
	}

	public void addProductToCart() throws InterruptedException {
		driver.findElement(addProductButton).click();
		Thread.sleep(1000);
	}

	public void clickOnProduct() throws InterruptedException {
		driver.findElement(productTitleLink).click();
		Thread.sleep(2000);
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
