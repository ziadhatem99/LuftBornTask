# LuftBorn Task - eBay Search Automation

A Selenium-based test automation project for eBay search functionality with automated HTML reporting.

## Features

- ✅ Automated eBay search testing
- ✅ Manual transmission filter testing
- ✅ Beautiful HTML test reports
- ✅ Cross-platform browser automation
- ✅ Comprehensive logging
- ✅ Data-driven testing with JSON
- ✅ Automatic report generation and opening

## Prerequisites

- Java 11 or higher
- Maven 3.6+
- Chrome browser
- Internet connection (for eBay access)

## Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd LuftBornTask
```

2. Install dependencies:
```bash
mvn clean install
```

## Usage

### Run All Tests
```bash
mvn clean test
```

### Run Tests Using TestNG XML
```bash
mvn clean test
```
Tests are configured to run from `testng.xml` which includes all test classes.

### Run Specific Test Class
```bash
mvn test -Dtest=TestSearch
```

### Generate Reports
Reports are automatically generated after each test run and saved to:
```
target/ExtentReports/report.html
```

The report opens automatically in your default browser.

## Configuration

### Report Settings
Edit `src/main/resources/report.properties`:

```properties
report.open.automatically=true          # Auto-open report in browser
report.path=target/ExtentReports/report.html  # Report location
report.title=eBay Search Test Automation Report  # Report title
```

### Test Data
Edit `src/main/resources/testdata/search-testdata.json` to modify search terms.

## Project Structure

```
LuftBornTask/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── Pages/
│   │   │   │   └── HomePage.java          # Page Object Model
│   │   │   └── utils/
│   │   │       ├── ReportConfig.java      # Report configuration
│   │   │       ├── ExtentReportManager.java # HTML report generator
│   │   │       └── TestListener.java      # TestNG listener
│   │   └── resources/
│   │       ├── log4j2.xml                 # Logging configuration
│   │       ├── report.properties          # Report settings
│   │       └── testdata/
│   │           └── search-testdata.json   # Test data
│   └── test/
│       ├── java/
│       │   ├── Base/
│       │   │   └── TestBase.java          # Base test class
│       │   └── Tests/
│       │       ├── TestSearch.java        # Search tests
│       │       └── TestDataProvider.java  # Data provider
│       └── resources/
├── target/                                # Generated files
├── pom.xml                                # Maven configuration
└── README.md                              # This file
```

## Technologies Used

- **Java 11** - Programming language
- **Selenium WebDriver** - Browser automation
- **TestNG** - Testing framework
- **Log4j2** - Logging framework
- **Jackson** - JSON processing
- **WebDriverManager** - Driver management

## Test Scenarios

1. **Search Functionality**
   - Navigate to eBay
   - Enter search terms
   - Verify search results display

2. **Manual Filter**
   - Apply manual transmission filter
   - Verify filtered results

## Reports

- **Location**: `target/ExtentReports/report.html`
- **Features**:
  - Test summary (passed/failed/skipped)
  - Individual test details
  - Execution logs
  - System information
  - Developer credit

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Developer

Developed by Ziad Hatem
