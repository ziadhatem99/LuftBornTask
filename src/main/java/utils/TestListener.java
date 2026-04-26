package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TestListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext context) {
        logger.info("Test Suite Started: " + context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Test Started: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String logs = getTestLogs();
        ExtentReportManager.addTestResult(testName, "PASS", logs);
        logger.info("✓ Test Passed: " + testName);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String logs = getTestLogs() + "\n\nError: " + result.getThrowable();
        ExtentReportManager.addTestResult(testName, "FAIL", logs);
        logger.error("✗ Test Failed: " + testName, result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String logs = getTestLogs();
        ExtentReportManager.addTestResult(testName, "SKIP", logs);
        logger.info("⊙ Test Skipped: " + testName);
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Test Suite Finished: " + context.getName());
        ExtentReportManager.generateReport();
    }

    private String getTestLogs() {
        StringBuilder logs = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("target/test-logs/test.log"));
            String line;
            while ((line = reader.readLine()) != null) {
                logs.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            logger.debug("Could not read logs: " + e.getMessage());
        }
        return logs.toString();
    }
}


