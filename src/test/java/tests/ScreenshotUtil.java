package test.java.tests;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ScreenshotUtil {

    public static void capture(WebDriver driver, String name)
            throws IOException {

        Path folder = Path.of("screenshots");
        Files.createDirectories(folder);

        File source = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.FILE);

        Path destination = folder.resolve(name + ".png");

        Files.copy(source, destination,
                StandardCopyOption.REPLACE_EXISTING);

        System.out.println("Screenshot saved: "
                + destination.toAbsolutePath());
    }
}
