# Readme

### What is this

A simple sample project on how appium tests works on a physical android devices, such as sending messages to another contact.

## Prerequisites

-   [Visual Studio Code](https://code.visualstudio.com)
-   [Nodejs](https://nodejs.org/en/)
-   [Android Studio](developer.android.com/studio)
-   [Appium Server GUI](https://github.com/appium/appium-desktop/releases/tag/v1.22.3-4)
-   [Appium Inspector](https://github.com/appium/appium-inspector/releases)
-   [Java 8 JRE](https://www.java.com/nl/download/ie_manual.jsp)

## Get your phone ready

On your phone activate Developer options:
-   Go to: settings > about phone > software information.
-   Click 7 times on Build number
Activate USB debugging:
-   Go back to settings menu and look for Developer options
-   Get into Developer option and activate USB debugging
-   Make sure that USB configuration is set as PTP

Now your phone is ready to connect with your PC.

## Getting Started with Android Studio

Firstly we open `Android Studio` and will configure the `SDK Manager` which you can find in `More Actions` or `Options`. This will open a menu that is called `Android SDK`, here you will choose an android SDK. I have chosen for `Android 12.0` as my physical android device runs Android 12. Then open the menu called `SDK tools`. Here we will first check `Hide obsolete packages` at the bottom right.
After this we will check the following list:

-   Android SDK Build-tools
-   Android SDK Command Line Tools
-   Android Emulator
-   Android SDK Platform-Tools
-   Intel x86 Emulator Accelerator

And apply these settings.

## Edit Environment Variables

Open `Windows Search` and search for `Edit the system environment variables`. In the menu click on bottom button called `Environment Variables` and this will open a pop-up. Here make sure under `System variables` there is a variable called `JAVA_HOME` and `ANDROID_HOME`, if not then we will have to add a new variable with the names previously mentioned. As for their value the `JAVA_HOME` should have the directory path to your local java `JDK Folder` and for `ANDROID_HOME` to your local android `SDK Folder`

Next up we will have to add a couple more values to our `Path` variable. These are the following for new value:
-   ../Sdk/tools/bin
-   ../Sdk/tools
-   ../Sdk/platform-tools
-   ../java/jreX.X.X_XXX/bin

Save and exit. Now you are ready to get started with your first appium project.

## Appium CLI & Appium-Doctor

before we start with appium cli, check if you have `Nodejs` installed by opening a CMD terminal and type `node -v`. If this return a version congratz you installed node correctly. If not I would suggest you to download `Nodejs` with the link provided in `Prerequisites`. After this we will run `npm i -g appium` to install appium globally. When you now type `appium` in you terminal an appium server will run. To exit out of this type `ctrl + c`.

Next we will install `appium-doctor` to check if everything is configured correctly. We do this with following command in cmd terminal `npm i -g appium-doctor`. After the installation we will check for `Android` if we configured correctly by running in cmd terminal following command `appium-doctor --android`. If everything is checked green under necessary dependencies then we are ready to get started with appium server.

## Setup Appium for appium inspector

Before starting with anything make sure your `physical android device` is connected to your pc. You can check this by running `adb devices` in CMD terminal. If you get exactly one hit, make sure you copy the `device ID` then for future purposes.

Now wer open `appium server GUI`. This will give you a screen with two inputs called `Host` and `Port`. For physical devices keep the host on `0.0.0.0` and just use the default port `4723`. When this is done just start the server. Next we will open `appium inspector`. In this screen just keep the `Remote Host` default, use for `Remote Port` 4723 and as `Remote Path` type /wd/hub. After this add a new Desired Capability called `platformName` and give as value `Android`. When this is done and your physical device is still connected you can start your session.

This tool can then be used to record each step with the correct selectors for your device and transfer them into generated code. If you wanna know more about this tool and the capabilities then I would suggest to look in their [documentation](https://github.com/appium/appium-inspector).

## Setup your first appium project

open vscode and after this we will have to install some extensions. These extensions are:
-   Extension Pack for Java
-   Maven for Java
-   Test Runner for Java
-   Project Manager for Java
-   Debugger for Java

After these are installed make in vscode a new java project. This can be done in `explorer` and at the bottom the is dropdown called `Java Projects`. Here you can make a new java project with the default `maven artifact` setup. When the project is done initializing then open the `pom.xml file. Here we will add following dependencies.
-   [Appium for Java](https://mvnrepository.com/artifact/io.appium/java-client)
-   [Selenium for java](https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java)
-   [Junit](https://mvnrepository.com/artifact/junit/junit)

Your `pom.xml` file should look like this under `dependencies`.

```xml
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.13.2</version>
      <scope>test</scope>
  </dependency>
    <dependency>
      <groupId>org.seleniumhq.selenium</groupId>
      <artifactId>selenium-java</artifactId>
      <version>4.5.0</version>
    </dependency>
    <dependency>
      <groupId>io.appium</groupId>
      <artifactId>java-client</artifactId>
      <version>8.2.0</version>
    </dependency>
  </dependencies>
```

Now your project should be setup to start your first Appium project.

## Generate the code for sample project.

Under `test` folder from your java project there should be one java test class. Delete this class and create two new classes called `Hooks.java` and `AppiumTest.java`.

In `Hooks.java` the following code should be present:

```java
import java.net.URL;

import org.junit.After;
import org.junit.Before;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class Hooks {

    private static AppiumDriverLocalService localAppiumServer;

    @Before
    public void beforeAll() {

        AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();

        serviceBuilder.usingAnyFreePort();
        localAppiumServer = AppiumDriverLocalService.buildService(serviceBuilder).withBasePath("/wd/hub/");

        localAppiumServer.start();
    }

    @After
    public void afterAll() {
        localAppiumServer.stop();
    }

    public URL getAppiumServerUrl() {
        return localAppiumServer.getUrl();
    }
}  
```

This code is responsible before everything to setup the local appium server and after close the local server again.
Now for `AppiumTest.java` the code of your device could be different under `messageTest` function as this was generated in your `Appium Inspector`. The rest of the code file should be the same.

```java
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
```

There is also a `WaitFor` function as the initial steps were to quick for the devices to process so I had to make a wait before each step to be successful. This is also a possible solution `BUT` isn't always a solution to your exact device as `versioning` and `selectors` can be different.

## Running the tests

After you altered the code to satisfy `YOUR` device. You can then start the tests `AFTER` you unlocked your device. As these tests don't unlock your device for you. If the test is succesful and you send a message to your dear `Koen Van Belle` then you succesfully followed the README
