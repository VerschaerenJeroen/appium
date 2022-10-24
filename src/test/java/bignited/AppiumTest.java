package bignited;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

public class AppiumTest {

    private AndroidDriver driver;

    @Before
    public void setUp() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "Android");

        URL remoteUrl = new URL("http://127.0.0.1:4723/wd/hub");

        driver = new AndroidDriver(remoteUrl, desiredCapabilities);
    }

    @Test
    public void messageTest() {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
        waitFor(1);
        WebElement telefoonButton = driver.findElement(AppiumBy.accessibilityId("Telefoon"));
        telefoonButton.click();
        waitFor(1);
        WebElement contactButton = driver.findElement(AppiumBy.accessibilityId("Contacten"));
        contactButton.click();
        waitFor(1);
        WebElement searchInput = driver.findElement(By.id("android:id/search_src_text"));
        searchInput.sendKeys("Koen Van Belle");
        WebElement searchbutton = driver.findElement(By.id("android:id/search_mag_icon"));
        searchbutton.click();
        WebElement koenButton = driver.findElement(By.xpath(
                "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ListView/android.view.ViewGroup[1]"));
        koenButton.click();
        waitFor(1);
        WebElement messageButton = driver.findElement(AppiumBy.accessibilityId("Sms"));
        messageButton.click();
        waitFor(1);
        WebElement openKeyboardButton = driver
                .findElement(By.id("com.google.android.apps.messaging:id/compose_message_text"));
        openKeyboardButton.sendKeys("Hey Koen, dit is Jeroen via appium.");
        waitFor(1);
        WebElement sendMessageButton = driver
                .findElement(AppiumBy.accessibilityId("Sms verzenden"));
        sendMessageButton.click();

    }

    private void waitFor(int numberOfSeconds) {
        try {
            System.out.println("Sleep for " + numberOfSeconds);
            Thread.sleep(numberOfSeconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}