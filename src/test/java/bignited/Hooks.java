package bignited;

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
