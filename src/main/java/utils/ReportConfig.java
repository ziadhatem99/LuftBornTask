package utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ReportConfig {
    private static Properties properties;

    static {
        try {
            properties = new Properties();
            FileInputStream fis = new FileInputStream("src/main/resources/report.properties");
            properties.load(fis);
            fis.close();
        } catch (Exception e) {
            System.out.println("report.properties not found, using default values");
        }
    }

    public static boolean isOpenAutomatically() {
        return Boolean.parseBoolean(properties != null ? properties.getProperty("report.open.automatically", "true") : "true");
    }

    public static String getReportPath() {
        return properties != null ? properties.getProperty("report.path", "target/ExtentReports/report.html") : "target/ExtentReports/report.html";
    }

    public static String getReportTitle() {
        return properties != null ? properties.getProperty("report.title", "Test Automation Report") : "Test Automation Report";
    }
}

