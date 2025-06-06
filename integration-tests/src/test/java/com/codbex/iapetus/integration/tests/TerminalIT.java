package com.codbex.iapetus.integration.tests;

import org.eclipse.dirigible.tests.framework.ide.Workbench;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;

// @Import(TerminalTestRestConfigDiffName.class)
public class TerminalIT extends IapetusIntegrationTest {

    @LocalServerPort
    private int localServerPort;

    @Test
    void testTerminalWorks() {
        Workbench workbench = ide.openWorkbench();
        workbench.openTerminal();
    }

}
