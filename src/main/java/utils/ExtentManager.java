package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getExtentReports() {
        if (extent == null) {
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            ExtentSparkReporter spark = new ExtentSparkReporter("reports/ExtentReport_" + date + ".html");
            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
        return extent;
    }
}