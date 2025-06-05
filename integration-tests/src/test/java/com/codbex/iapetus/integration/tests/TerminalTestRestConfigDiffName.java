package com.codbex.iapetus.integration.tests;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@TestConfiguration
class TerminalTestRestConfigDiffName {

    @RestController
    static class TerminalTestRest {
        public static final String TEST_PATH = "/services/core/version/terminal/api/test";
        private static boolean called = false;

        static boolean isCalled() {
            return called;
        }

        @GetMapping(TEST_PATH)
        String test() {
            called = true;
            return "Hello";
        }
    }
}
