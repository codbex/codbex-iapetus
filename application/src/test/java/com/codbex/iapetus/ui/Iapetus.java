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
package com.codbex.iapetus.ui;

import org.eclipse.dirigible.tests.Workbench;
import org.eclipse.dirigible.tests.framework.Browser;
import org.eclipse.dirigible.tests.framework.HtmlAttribute;
import org.eclipse.dirigible.tests.framework.HtmlElementType;
import org.eclipse.dirigible.tests.util.SleepUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
@Lazy
@ComponentScan("org.eclipse.dirigible.tests.framework")
public class Iapetus {
    private static final Logger LOGGER = LoggerFactory.getLogger(Iapetus.class);

    private static final String LOGIN_PAGE_TITLE = "Please sign in";

    private static final String ROOT_PATH = "/";

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    private static final String USERNAME_FIELD_ID = "username";
    private static final String PASSWORD_FIELD_ID = "password";
    private static final String SUBMIT_TYPE = "submit";

    private static final String SIGN_IN_BUTTON_TEXT = "Sign in";
    private final Browser browser;

    public Iapetus(Browser browser) {
        this.browser = browser;
    }

    public void assertPublishingProjectMessage(String projectName) {
        String publishingMessage = "Publishing '/" + projectName + "'...";
        assertStatusBarMessage(publishingMessage);
    }

    public void assertStatusBarMessage(String expectedMessage) {
        browser.assertElementExistsByTypeAndText(HtmlElementType.SPAN, expectedMessage);
    }

    public void assertPublishedProjectMessage(String projectName) {
        String publishedMessage = "Published '/workspace/" + projectName + "'";
        assertStatusBarMessage(publishedMessage);
    }

    public void openHomePage() {
        browser.openPath(ROOT_PATH);
        login(false);

        SleepUtil.sleepMillis(500);
        browser.reload();
    }

    public void login(boolean forceLogin) {
        if (!forceLogin && !isLoginPageOpened()) {
            LOGGER.info("Already logged in");
            return;
        }
        LOGGER.info("Logging...");
        browser.enterTextInElementByAttributePattern(HtmlElementType.INPUT, HtmlAttribute.ID, USERNAME_FIELD_ID, USERNAME);
        browser.enterTextInElementByAttributePattern(HtmlElementType.INPUT, HtmlAttribute.ID, PASSWORD_FIELD_ID, PASSWORD);
        browser.clickOnElementByAttributePatternAndText(HtmlElementType.BUTTON, HtmlAttribute.TYPE, SUBMIT_TYPE, SIGN_IN_BUTTON_TEXT);
    }

    private boolean isLoginPageOpened() {
        String pageTitle = browser.getPageTitle();
        return LOGIN_PAGE_TITLE.equals(pageTitle);
    }
//
//    public void createAndPublishProjectFromResources(String resourcesFolderPath) {
//        createAndPublishProjectFromResources(resourcesFolderPath, Collections.emptyMap());
//    }

//    public void createAndPublishProjectFromResources(String resourcesFolderPath, Map<String, String> placeholders) {
////        projectUtil.copyResourceProjectToUserWorkspace(USERNAME, resourcesFolderPath, placeholders);
//
//
//        Workbench workbench = openWorkbench();
//        workbench.publishAll();
//    }

    public Workbench openWorkbench() {
        openHomePage();

//        browser.clickOnElementById("perspective-workbench");
        browser.clickOnElementByAttributePattern(HtmlElementType.DIV, HtmlAttribute.ID, "dgProjects");

        return new Workbench(browser);
    }


    public void createNewBlankProject(String projectName) {
        Workbench workbench = openWorkbench();

        workbench.createNewProject(projectName);

        assertPublishedProjectMessage(projectName);
    }
}
