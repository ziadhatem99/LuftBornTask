package Pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.lang.Thread;

public class HomePage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(HomePage.class);

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        logger.debug("HomePage initialized with WebDriver");
    }

    private By searchField = By.id("gh-ac");
    private By searchButton = By.id("gh-search-btn");
    private By searchResults = By.xpath("//ul[@class='srp-results srp-list clearfix']//li[contains(@class,'s-card')]");
    private By manualCheckBox = By.xpath("//span[contains(@class, 'checkbox')][.//input[@aria-label='Manual']]");

    public HomePage addSearchData(String data) {
        logger.info("Entering search data: {}", data);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(searchField));
        element.sendKeys(data);
        logger.info("Search data entered successfully");
        return this;
    }

    public HomePage clickSearchButton() {
        logger.info("Clicking search button");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        element.click();
        logger.info("Search button clicked, waiting for results");
        return this;
    }

    public int getSearchResultsCount() {
        logger.info("Getting search results count");
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(searchResults, 0));
        List<WebElement> results = driver.findElements(searchResults);
        int count = results.size();
        logger.info("Found {} search results", count);
        return count;
    }

    public boolean areSearchResultsDisplayed() {
        logger.info("Checking if search results are displayed");
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(searchResults));
            logger.info("Search results are displayed");
            return true;
        } catch (Exception e) {
            logger.warn("Search results are not displayed", e);
            return false;
        }
    }

    public HomePage clickManualCheckBox() {
        logger.info("Attempting to click manual checkbox");
        WebElement element = null;
        int attempts = 0;
        while (attempts < 10) {
            try {
                element = wait.until(ExpectedConditions.elementToBeClickable(manualCheckBox));
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
                Thread.sleep(500);
                element.click();
                logger.info("Manual checkbox clicked successfully");
                return this;
            } catch (Exception e) {
                logger.debug("Manual checkbox not ready, scrolling down. Attempt: {}", attempts + 1);
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("window.scrollBy(0, 300);");
                attempts++;
            }
        }
        logger.error("Could not find and click manual checkbox after scrolling");
        throw new RuntimeException("Could not find and click manual checkbox after scrolling");
    }
}
