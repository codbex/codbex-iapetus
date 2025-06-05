package com.codbex.iapetus.integration.tests;

import com.codeborne.selenide.Configuration;
import org.eclipse.dirigible.tests.framework.ide.Workbench;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;

//@Import(TerminalTestRestConfigDiffName.class)
public class TerminalIT extends IapetusIntegrationTest {

    @LocalServerPort
    private int localServerPort;

    @Test
    void testTerminalWorks() {
        Configuration.browser = "chrome";
        Configuration.headless = false;
        Configuration.browserSize = "1920x1080"; // now this will work

        Workbench workbench = ide.openWorkbench();

        workbench.openTerminal();
    }

}
