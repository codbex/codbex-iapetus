/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.iapetus.integration.tests;

import com.codeborne.selenide.Configuration;
import org.eclipse.dirigible.integration.tests.api.SecurityIT;
import org.eclipse.dirigible.integration.tests.ui.tests.TerminalIT;
import org.junit.jupiter.api.Disabled;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.openqa.selenium.chrome.ChromeOptions;

@Disabled
@Suite
@SelectClasses({//
        // CamelCronRouteStarterTemplateIT.class, //
        // CamelHttpRouteStarterTemplateIT.class, //
        // CamelDirigibleJavaScriptComponentCronRouteIT.class, //
        // CamelDirigibleJavaScriptComponentHttpRouteIT.class, //
        // CamelDirigibleTwoStepsJSInvokerCronRouteIT.class, //
        // CamelDirigibleTwoStepsJSInvokerHttpRouteIT.class, //
        // CamelExtractTransformLoadJdbcIT.class, //
        // CamelExtractTransformLoadTypescriptIT.class, //
        // CreateNewProjectIT.class, //
        // CsvimIT.class, //
        // CustomSecurityIT.class, //
        // DatabasePerspectiveIT.class, //
        // GitPerspectiveIT.class, //
        // HomepageRedirectIT.class, //
        // MailIT.class, //
        // MessagingFacadeIT.class, //
        TerminalIT.class, //
        SecurityIT.class //
})
public class DirigibleCommonTestSuiteIT {

    static {
        // TODO: to be removed once https://github.com/eclipse-dirigible/dirigible/pull/5104
        // is released
        // Configuration.browserSize = "1920x1080";
        Configuration.browserSize = null;

        Configuration.browser = "chrome";
        Configuration.browserCapabilities = new ChromeOptions().addArguments("--remote-allow-origins=*")
                                                               .addArguments("--window-size=1920,1080")
                                                               .addArguments("--headless=new")
                                                               .addArguments("--disable-gpu")
                                                               .addArguments("--no-sandbox")
                                                               .addArguments("--disable-dev-shm-usage");
        Configuration.browserSize = "1920x1080";
    }
}
