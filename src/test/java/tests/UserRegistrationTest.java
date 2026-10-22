
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegistrationTest {

    WebDriver driver;
    String baseUrl = "http://127.0.0.1:5000";

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.get(baseUrl);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testRegistrationPageLoads() {
        Assert.assertEquals(
            driver.getTitle(), "User Registration"
        );

        Assert.assertTrue(
            driver.findElement(By.name("name")).isDisplayed()
        );
        Assert.assertTrue(
            driver.findElement(By.name("email")).isDisplayed()
        );
        Assert.assertTrue(
            driver.findElement(By.name("password")).isDisplayed()
        );
    }

    @Test
    public void testSuccessfulRegistration() {
        driver.findElement(By.name("name")).sendKeys("Alice");
        driver.findElement(By.name("email"))
              .sendKeys("alice@example.com");
        driver.findElement(By.name("password"))
              .sendKeys("Test@123");

        driver.findElement(
            By.cssSelector("button[type='submit']")
        ).click();

        String message = driver.findElement(
            By.tagName("p")
        ).getText();

        Assert.assertEquals(
            message, "Registration successful for Alice"
        );

        String userList = driver.findElement(
            By.tagName("ul")
        ).getText();

        Assert.assertTrue(
            userList.contains("Alice - alice@example.com")
        );
    }

    @Test
    public void testEmptyNameValidation() {
        WebElement name = driver.findElement(By.name("name"));

        Assert.assertTrue(
            name.getAttribute("required") != null
        );

        Assert.assertTrue(
            driver.findElement(By.name("email"))
                  .getAttribute("required") != null
        );
    }

    @Test
    public void testEmptyEmailValidation() {
        WebElement email = driver.findElement(By.name("email"));

        Assert.assertTrue(
            email.getAttribute("required") != null
        );
    }

    @Test
    public void testEmptyPasswordValidation() {
        WebElement password = driver.findElement(
            By.name("password")
        );

        Assert.assertTrue(
            password.getAttribute("required") != null
        );
    }

    @Test
    public void testInvalidEmailValidation() {
        WebElement email = driver.findElement(By.name("email"));

        email.sendKeys("invalid-email");

        Boolean invalidEmail = (Boolean) (
            ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript(
                    "return arguments[0].validity.typeMismatch",
                    email
                )
        );

        Assert.assertTrue(
            invalidEmail,
            "Invalid email format should be rejected"
        );
    }

    @Test
    public void testPasswordIsMasked() {
        WebElement password = driver.findElement(
            By.name("password")
        );

        Assert.assertEquals(
            password.getAttribute("type"), "password"
        );
    }

    @Test
    public void testRegisteredUserIsDisplayed() {
        driver.findElement(By.name("name")).sendKeys("Bob");
        driver.findElement(By.name("email"))
              .sendKeys("bob@example.com");
        driver.findElement(By.name("password"))
              .sendKeys("Bob@123");

        driver.findElement(
            By.cssSelector("button[type='submit']")
        ).click();

        String users = driver.findElement(
            By.tagName("ul")
        ).getText();

        Assert.assertTrue(
            users.contains("Bob - bob@example.com")
        );
    }

    @Test
    public void testPasswordIsNotDisplayed() {
        driver.findElement(By.name("name")).sendKeys("Charlie");
        driver.findElement(By.name("email"))
              .sendKeys("charlie@example.com");
        driver.findElement(By.name("password"))
              .sendKeys("Secret@123");

        driver.findElement(
            By.cssSelector("button[type='submit']")
        ).click();

        String pageSource = driver.getPageSource();

        Assert.assertFalse(
            pageSource.contains("Secret@123"),
            "Password should not appear in the HTML page"
        );
    }
}
