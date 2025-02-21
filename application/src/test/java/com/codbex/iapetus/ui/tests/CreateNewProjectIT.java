package com.codbex.iapetus.ui.tests;

import org.junit.jupiter.api.Test;

class CreateProjectIT extends UserInterfaceIntegrationTest {

    @Test
    void testCreateNewBlankProject() {
        ide.createNewBlankProject("orders-etl");
    }
}
