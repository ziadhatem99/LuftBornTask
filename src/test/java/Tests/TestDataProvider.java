package Tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;

public class TestDataProvider {
    private static final Logger logger = LogManager.getLogger(TestDataProvider.class);

    @DataProvider(name = "searchData")
    public static Object[][] getSearchData() {
        logger.info("Loading search test data from JSON");
        JsonNode data = TestDataProvider.getJsonData();
        JsonNode items = data.get("searchItems");
        Object[][] testData = new Object[items.size()][1];
        for (int i = 0; i < items.size(); i++) {
            testData[i][0] = items.get(i).get("item").asText();
        }
        logger.info("Loaded {} search items from test data", items.size());
        return testData;
    }

    private static JsonNode getJsonData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = TestDataProvider.class.getClassLoader().getResourceAsStream("testdata/search-testdata.json");
            if (is == null) {
                logger.error("JSON file not found in classpath");
                throw new RuntimeException("JSON file not found in classpath");
            }
            JsonNode data = mapper.readTree(is);
            logger.debug("Successfully parsed JSON test data");
            return data;
        } catch (Exception e) {
            logger.error("Error reading JSON data", e);
            throw new RuntimeException("Error reading JSON data", e);
        }
    }
}
