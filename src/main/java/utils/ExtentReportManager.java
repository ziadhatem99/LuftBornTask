package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExtentReportManager {
    private static final Logger logger = LogManager.getLogger(ExtentReportManager.class);
    private static String reportPath;
    private static List<TestResult> testResults = new ArrayList<>();
    private static LocalDateTime startTime;

    static {
        startTime = LocalDateTime.now();
        reportPath = ReportConfig.getReportPath();
    }

    public static void addTestResult(String testName, String status, String logs) {
        testResults.add(new TestResult(testName, status, logs));
    }

    public static void generateReport() {
        try {
            new File(reportPath).getParentFile().mkdirs();
            FileWriter writer = new FileWriter(reportPath);
            writer.write(generateHTML());
            writer.close();
            logger.info("Report generated at: " + reportPath);

            if (ReportConfig.isOpenAutomatically()) {
                openReport();
            }
        } catch (IOException e) {
            logger.error("Failed to generate report", e);
        }
    }

    private static String generateHTML() {
        LocalDateTime endTime = LocalDateTime.now();
        int passed = (int) testResults.stream().filter(t -> t.status.equals("PASS")).count();
        int failed = (int) testResults.stream().filter(t -> t.status.equals("FAIL")).count();
        int skipped = (int) testResults.stream().filter(t -> t.status.equals("SKIP")).count();

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html lang=\"en\">\n");
        html.append("<head>\n");
        html.append("    <meta charset=\"UTF-8\">\n");
        html.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        html.append("    <title>").append(ReportConfig.getReportTitle()).append("</title>\n");
        html.append("    <style>\n");
        html.append(getCSS());
        html.append("    </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("    <div class=\"container\">\n");
        html.append("        <header>\n");
        html.append("            <h1>").append(ReportConfig.getReportTitle()).append("</h1>\n");
        html.append("            <p>Test Execution Report</p>\n");
        html.append("        </header>\n");
        html.append("        <div class=\"summary\">\n");
        html.append("            <div class=\"summary-item passed\">\n");
        html.append("                <h3>").append(passed).append("</h3>\n");
        html.append("                <p>Passed</p>\n");
        html.append("            </div>\n");
        html.append("            <div class=\"summary-item failed\">\n");
        html.append("                <h3>").append(failed).append("</h3>\n");
        html.append("                <p>Failed</p>\n");
        html.append("            </div>\n");
        html.append("            <div class=\"summary-item skipped\">\n");
        html.append("                <h3>").append(skipped).append("</h3>\n");
        html.append("                <p>Skipped</p>\n");
        html.append("            </div>\n");
        html.append("            <div class=\"summary-item total\">\n");
        html.append("                <h3>").append(testResults.size()).append("</h3>\n");
        html.append("                <p>Total</p>\n");
        html.append("            </div>\n");
        html.append("        </div>\n");
        html.append("        <div class=\"execution-info\">\n");
        html.append("            <p><strong>Start Time:</strong> ").append(startTime).append("</p>\n");
        html.append("            <p><strong>End Time:</strong> ").append(endTime).append("</p>\n");
        html.append("            <p><strong>OS:</strong> ").append(System.getProperty("os.name")).append("</p>\n");
        html.append("            <p><strong>Java Version:</strong> ").append(System.getProperty("java.version")).append("</p>\n");
        html.append("        </div>\n");
        html.append("        <div class=\"test-results\">\n");
        html.append("            <h2>Test Details</h2>\n");

        for (TestResult result : testResults) {
            html.append("            <div class=\"test-case ").append(result.status.toLowerCase()).append("\">\n");
            html.append("                <div class=\"test-header\">\n");
            html.append("                    <span class=\"status-badge ").append(result.status.toLowerCase()).append("\">").append(result.status).append("</span>\n");
            html.append("                    <h3>").append(result.testName).append("</h3>\n");
            html.append("                </div>\n");
            html.append("                <div class=\"test-logs\">\n");
            html.append("                    <details>\n");
            html.append("                        <summary>View Logs</summary>\n");
            html.append("                        <pre>").append(result.logs).append("</pre>\n");
            html.append("                    </details>\n");
            html.append("                </div>\n");
            html.append("            </div>\n");
        }

        html.append("        </div>\n");
        html.append("        <footer class=\"report-footer\">\n");
        html.append("            <p>Developed by Ziad Hatem</p>\n");
        html.append("        </footer>\n");
        html.append("    </div>\n");
        html.append("</body>\n");
        html.append("</html>\n");

        return html.toString();
    }

    private static String getCSS() {
        return "* {\n" +
                "    margin: 0;\n" +
                "    padding: 0;\n" +
                "    box-sizing: border-box;\n" +
                "}\n" +
                "body {\n" +
                "    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;\n" +
                "    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n" +
                "    min-height: 100vh;\n" +
                "    padding: 20px;\n" +
                "}\n" +
                ".container {\n" +
                "    max-width: 1200px;\n" +
                "    margin: 0 auto;\n" +
                "    background: white;\n" +
                "    border-radius: 12px;\n" +
                "    box-shadow: 0 20px 60px rgba(0,0,0,0.3);\n" +
                "    overflow: hidden;\n" +
                "}\n" +
                "header {\n" +
                "    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n" +
                "    color: white;\n" +
                "    padding: 40px 20px;\n" +
                "    text-align: center;\n" +
                "}\n" +
                "header h1 {\n" +
                "    font-size: 2.5em;\n" +
                "    margin-bottom: 10px;\n" +
                "}\n" +
                "header p {\n" +
                "    font-size: 1.1em;\n" +
                "    opacity: 0.9;\n" +
                "}\n" +
                ".summary {\n" +
                "    display: grid;\n" +
                "    grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));\n" +
                "    gap: 20px;\n" +
                "    padding: 30px 20px;\n" +
                "    background: #f5f5f5;\n" +
                "    border-bottom: 2px solid #e0e0e0;\n" +
                "}\n" +
                ".summary-item {\n" +
                "    text-align: center;\n" +
                "    padding: 20px;\n" +
                "    border-radius: 8px;\n" +
                "    color: white;\n" +
                "}\n" +
                ".summary-item.passed {\n" +
                "    background: #4caf50;\n" +
                "}\n" +
                ".summary-item.failed {\n" +
                "    background: #f44336;\n" +
                "}\n" +
                ".summary-item.skipped {\n" +
                "    background: #ff9800;\n" +
                "}\n" +
                ".summary-item.total {\n" +
                "    background: #2196f3;\n" +
                "}\n" +
                ".summary-item h3 {\n" +
                "    font-size: 2em;\n" +
                "    margin-bottom: 5px;\n" +
                "}\n" +
                ".summary-item p {\n" +
                "    font-size: 0.9em;\n" +
                "    opacity: 0.9;\n" +
                "}\n" +
                ".execution-info {\n" +
                "    padding: 20px;\n" +
                "    background: #fafafa;\n" +
                "    border-bottom: 1px solid #e0e0e0;\n" +
                "}\n" +
                ".execution-info p {\n" +
                "    margin: 10px 0;\n" +
                "    color: #333;\n" +
                "}\n" +
                ".test-results {\n" +
                "    padding: 30px 20px;\n" +
                "}\n" +
                ".test-results h2 {\n" +
                "    margin-bottom: 20px;\n" +
                "    color: #333;\n" +
                "}\n" +
                ".test-case {\n" +
                "    margin-bottom: 20px;\n" +
                "    border-left: 4px solid #ccc;\n" +
                "    background: #fafafa;\n" +
                "    border-radius: 4px;\n" +
                "    overflow: hidden;\n" +
                "}\n" +
                ".test-case.pass {\n" +
                "    border-left-color: #4caf50;\n" +
                "    background: #f1f8f6;\n" +
                "}\n" +
                ".test-case.fail {\n" +
                "    border-left-color: #f44336;\n" +
                "    background: #fff5f5;\n" +
                "}\n" +
                ".test-case.skip {\n" +
                "    border-left-color: #ff9800;\n" +
                "    background: #fffaf0;\n" +
                "}\n" +
                ".test-header {\n" +
                "    display: flex;\n" +
                "    align-items: center;\n" +
                "    padding: 15px 20px;\n" +
                "    border-bottom: 1px solid #e0e0e0;\n" +
                "}\n" +
                ".test-header h3 {\n" +
                "    margin-left: 15px;\n" +
                "    color: #333;\n" +
                "}\n" +
                ".status-badge {\n" +
                "    padding: 4px 12px;\n" +
                "    border-radius: 20px;\n" +
                "    font-size: 0.8em;\n" +
                "    font-weight: bold;\n" +
                "    color: white;\n" +
                "}\n" +
                ".status-badge.pass {\n" +
                "    background: #4caf50;\n" +
                "}\n" +
                ".status-badge.fail {\n" +
                "    background: #f44336;\n" +
                "}\n" +
                ".status-badge.skip {\n" +
                "    background: #ff9800;\n" +
                "}\n" +
                ".test-logs {\n" +
                "    padding: 15px 20px;\n" +
                "}\n" +
                ".test-logs summary {\n" +
                "    cursor: pointer;\n" +
                "    font-weight: bold;\n" +
                "    color: #667eea;\n" +
                "    padding: 10px;\n" +
                "    background: #f0f0f0;\n" +
                "    border-radius: 4px;\n" +
                "    user-select: none;\n" +
                "}\n" +
                ".test-logs summary:hover {\n" +
                "    background: #e0e0e0;\n" +
                "}\n" +
                ".test-logs pre {\n" +
                "    background: #1e1e1e;\n" +
                "    color: #d4d4d4;\n" +
                "    padding: 15px;\n" +
                "    border-radius: 4px;\n" +
                "    overflow-x: auto;\n" +
                "    margin-top: 10px;\n" +
                "    font-size: 0.85em;\n" +
                "    font-family: 'Courier New', monospace;\n" +
                "    line-height: 1.4;\n" +
                "}\n" +
                ".report-footer {\n" +
                "    padding: 15px 20px;\n" +
                "    background: #f5f5f5;\n" +
                "    border-top: 1px solid #e0e0e0;\n" +
                "    text-align: center;\n" +
                "    color: #666;\n" +
                "    font-size: 0.8em;\n" +
                "}\n" +
                ".report-footer p {\n" +
                "    margin: 0;\n" +
                "    font-weight: 500;\n" +
                "}\n";
    }

    private static void openReport() {
        try {
            String absolutePath = new File(reportPath).getAbsolutePath();
            logger.info("Opening report: " + absolutePath);
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", absolutePath});
            } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                Runtime.getRuntime().exec(new String[]{"open", absolutePath});
            } else {
                Runtime.getRuntime().exec(new String[]{"xdg-open", absolutePath});
            }
        } catch (IOException e) {
            logger.error("Failed to open report", e);
        }
    }

    static class TestResult {
        String testName;
        String status;
        String logs;

        TestResult(String testName, String status, String logs) {
            this.testName = testName;
            this.status = status;
            this.logs = logs;
        }
    }
}
