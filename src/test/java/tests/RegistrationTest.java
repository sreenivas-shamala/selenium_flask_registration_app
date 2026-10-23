package test.java.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.By;

import java.io.IOException;

public class RegistrationTest extends BaseTest {

    @Test
    public void verifyFlaskRegistration() throws Exception {

        try {
            String title = driver.getTitle();
            System.out.println("Page title: " + title);

            driver.findElement(By.name("name"))
                    .sendKeys("admin");

            driver.findElement(By.name("email"))
                    .sendKeys("admin@gmail.com");

            driver.findElement(By.name("password"))
                    .sendKeys("admin");

            driver.findElement(
                    By.cssSelector("button[type='submit']")
            ).click();

            String result = driver.findElement(
                    By.tagName("p")
            ).getText();

            Assert.assertTrue(
                    result.toLowerCase().contains("registration"),
                    "Registration in Paragraph Text was not found"
            );

            ScreenshotUtil.capture(
                    driver, "registration-success"
            );

            System.out.println(
                    "******** Flask Registration Successful ********"
            );

        } catch (Exception | AssertionError e) {

            try {
                ScreenshotUtil.capture(
                        driver, "registration-failure"
                );
            } catch (IOException screenshotError) {
                e.addSuppressed(screenshotError);
                System.err.println(
                        "Could not save screenshot: "
                                + screenshotError.getMessage()
                );
            }

            throw e;
        }
    }
}
