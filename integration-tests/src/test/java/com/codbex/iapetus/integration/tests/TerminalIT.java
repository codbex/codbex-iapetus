package com.codbex.iapetus.integration.tests;

import com.codeborne.selenide.Configuration;
import org.eclipse.dirigible.tests.framework.ide.Workbench;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.web.server.LocalServerPort;

//@Import(TerminalTestRestConfigDiffName.class)
public class TerminalIT extends IapetusIntegrationTest {

    @LocalServerPort
    private int localServerPort;

    @Test
    void testTerminalWorks() {
        Configuration.browser = "chrome";
        Configuration.browserSize = null;

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // or just --headless
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");

        Workbench workbench = ide.openWorkbench();

        workbench.openTerminal();
    }

}
