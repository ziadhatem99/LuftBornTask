package Tests;

import Base.TestBase;
import Pages.HomePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestSearch extends TestBase {
    HomePage homePage;
    private static final Logger logger = LogManager.getLogger(TestSearch.class);

    @Test(dataProvider = "searchData", dataProviderClass = TestDataProvider.class)
    public void testSearchFunctionalityE2E(String searchItem) {
        logger.info("Starting E2E search test for item: {}", searchItem);
        homePage = new HomePage(driver);
        homePage.addSearchData(searchItem).clickSearchButton();

        int resultCountBefore = homePage.getSearchResultsCount();
        logger.info("Number of search results for all transmissions: {}", resultCountBefore);

        homePage.clickManualCheckBox();

        int resultCountAfter = homePage.getSearchResultsCount();
        logger.info("Number of search results after applying Manual filter: {}", resultCountAfter);

        Assert.assertTrue(homePage.areSearchResultsDisplayed(), "Search results are not displayed");
        logger.info("E2E search test completed successfully for item: {}", searchItem);
    }
}
