package pages;

import core.DriverFactory;
import org.openqa.selenium.WebDriver;

public class BasePage {
     WebDriver driver;

    public BasePage() {
        this.driver = DriverFactory.getDriver("chrome");
    }
}