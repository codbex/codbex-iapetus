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

import org.eclipse.dirigible.integration.tests.api.SecurityIT;
import org.eclipse.dirigible.integration.tests.api.java.messaging.MessagingFacadeIT;
import org.eclipse.dirigible.integration.tests.ui.tests.*;
import org.eclipse.dirigible.integration.tests.ui.tests.camel.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({//
        CamelDirigibleJavaScriptComponentCronRouteIT.class, //
        CamelDirigibleJavaScriptComponentHttpRouteIT.class, //
        CamelDirigibleTwoStepsJSInvokerCronRouteIT.class, //
        CamelDirigibleTwoStepsJSInvokerHttpRouteIT.class, //
        CamelExtractTransformLoadJdbcIT.class, //
        CamelExtractTransformLoadTypescriptIT.class, //
        CreateNewProjectIT.class, //
        CsvimIT.class, //
        CustomSecurityIT.class, //
        DatabasePerspectiveIT.class, //
        GitPerspectiveIT.class, //
        HomepageRedirectIT.class, //
        MailIT.class, //
        MessagingFacadeIT.class, //
        SecurityIT.class, //
        TerminalIT.class//
})
public class DirigibileCommonTestSuiteIT {
}
