package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ScreenShotUtils {

    public static String takeScreenshot(WebDriver driver, String testName) {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH-mm-ss"));
        String folderPath = "screenshots/" + date;
        File dir = new File(folderPath);
        if (!dir.exists()) dir.mkdirs();

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = folderPath + "/" + testName + "_" + time + ".png";
        try {
            Files.copy(src.toPath(), new File(path).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}