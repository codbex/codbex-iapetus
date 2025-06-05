package com.codbex.iapetus.integration.tests;

import com.codeborne.selenide.Configuration;
import org.eclipse.dirigible.tests.framework.ide.Terminal;
import org.eclipse.dirigible.tests.framework.ide.Workbench;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@Import(TerminalTestRestConfig.class)
public class TerminalIT extends IapetusIntegrationTest {

    @LocalServerPort
    private int localServerPort;

    @Test
    void testTerminalWorks() {
        Configuration.browser = "chrome";
        Configuration.browserCapabilities = new ChromeOptions().addArguments("--remote-allow-origins=*")
                                                               .addArguments("--window-size=1920,1080");
        Configuration.browserSize = "1920x1080";
        
        Workbench workbench = ide.openWorkbench();

        Terminal terminal = workbench.openTerminal();

        String testRest = "http://localhost:" + localServerPort + TerminalTestRestConfig.TerminalTestRest.TEST_PATH;
        terminal.enterCommand("wget -qO- " + testRest);

        await().pollInterval(1, TimeUnit.SECONDS)
               .atMost(30, TimeUnit.SECONDS)
               .until(() -> TerminalTestRestConfig.TerminalTestRest.isCalled());
    }

}
