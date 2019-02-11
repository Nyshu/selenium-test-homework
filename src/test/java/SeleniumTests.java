import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertTrue;


public class SeleniumTests {

    private static ChromeDriver driver;

    private static String DriverPath = "C:\\InstallationTools\\chromedriver_win32\\chromedriver.exe";
    private static String DriverName = "webdriver.chrome.driver";
    private static String BaseUrl = "http://amazon.com";

    @Before
    public void OpenBrowser() {
        System.setProperty(DriverName, DriverPath);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(BaseUrl);
    }

    @Test
    public void main() {

        String expectedTitle = "Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more";

        // get the actual value of the title
        verifyTitle(expectedTitle);
        searchKeyword();
        SortResult();
        CheckDetails();
    }

    public void searchKeyword() {

        //Search given keyword and verify correct page is opened
        String searchKeyword = "Nikon";
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys((searchKeyword));
        driver.findElement(By.xpath("//*[@id='nav-search']/form//input")).click();
        String expectedTitle = "Amazon.com: Nikon";
        verifyTitle(expectedTitle);
    }

    public void SortResult() {
        //Sort Result with Price High to Low
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#s-result-sort-select")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#s-result-sort-select")));
        Select dropdown = new Select(driver.findElement(By.cssSelector("#s-result-sort-select")));
        dropdown.selectByValue("price-desc-rank");
        }

        public void CheckDetails () {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            //Here we are choosing second product index = 2;
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"search\"]//div[@class=\"s-result-list sg-row\"]//div[@data-index=1]//a")));
            driver.findElement(By.xpath("//*[@id=\"search\"]//div[@class=\"s-result-list sg-row\"]//div[@data-index=1]//a")).click();
            //Nikon  D5
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("titleSection")));
            String Title = driver.findElement(By.id("titleSection")).getText();
            assertTrue(Title.contains("Nikon D53"));

        }
        public void verifyTitle (String expectedTitle){
            WebDriverWait wait = new WebDriverWait(driver, 20);
            wait.until(ExpectedConditions.titleContains(expectedTitle));
        }

        @After
        public void CloseBrowser () {
            driver.close();
            driver.quit();

        }

    }
