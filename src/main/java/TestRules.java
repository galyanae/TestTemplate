import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.Augmenter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Annag on 5/23/2017.
 */
public class TestRules {
    public static WebDriver driver;
    public static ExtentReports extent = new ExtentReports("C:\\Users\\annag\\Desktop\\junit_report\\extentReportFile.html", true);

    public static ExtentTest extentTest;

    public static String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    public static String email = date + "qa@websplanet.com";
    public static String password = "cosmos123145";

    public static String path = "C:\\Users\\annag\\Desktop\\junit_report\\screenshots\\";


    @BeforeClass
    public static void setChromeProperties() {

        System.out.println("BEFORE CLASS");

        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\annag\\Desktop\\chromedriver.exe");

        // Maximize browser window
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        // Disable 'remember password' prompt
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        // Disable pop-ups
        options.addArguments("--disable-popup-blocking");

        // Disable extensions
        options.addArguments("chrome.switches", "–disable-extensions");
        // Disable security warnings
        options.addArguments("--test-type");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }


    @Before
    public void before() {
        System.out.println("BEFORE");
    }

    @After
    public void after() {
        System.out.println("AFTER");
        //Close the report
        extent.endTest(extentTest);


    }

    @AfterClass
    public static void afterClass() {
        System.out.println("AFTER CLASS");
        driver.close();

        // writing everything to document.
        extent.flush();


    }

    public void screenShot() {

        try {

            WebDriver augmentedDriver = new Augmenter().augment(driver);
            File source = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
            path = "C:\\Users\\annag\\Desktop\\junit_report\\screenshots\\" + source.getName();
            FileUtils.copyFile(source, new File(path));
        } catch (IOException e) {
            path = "Failed to capture screenshot: " + e.getMessage();
        }

    }
}
