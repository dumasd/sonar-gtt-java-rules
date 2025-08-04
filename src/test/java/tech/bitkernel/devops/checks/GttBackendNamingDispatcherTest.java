/*
 * Bitkernel GTT Java Rules
 * Copyright (C) 2009-2025 dumasd
 * wukai213@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the Sonar Source-Available License Version 1, as published by SonarSource SA.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the Sonar Source-Available License for more details.
 *
 * You should have received a copy of the Sonar Source-Available License
 * along with this program; if not, see https://sonarsource.com/license/ssal/
 */
package tech.bitkernel.devops.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;
import tech.bitkernel.devops.sonar.java.checks.GttBackendNamingDispatcher;

public class GttBackendNamingDispatcherTest {

    @Test
    public void testWithNoIssues() {
        // Use an instance of the check under test to raise the issue.
        GttBackendNamingDispatcher dispatcher = new GttBackendNamingDispatcher();

        // Verifies that the check will raise the adequate issues with the expected message.
        // In the test file, lines which should raise an issue have been commented out
        // by using the following syntax: "// Noncompliant {{EXPECTED_MESSAGE}}"
        CheckVerifier.newVerifier()
                .onFile("src/test/files/gtt/demo/order/api/endpoint/IOrderController.java")
                .withCheck(dispatcher)
                .verifyNoIssues();
//                .verifyIssues();
    }

    @Test
    public void testWithIssues() {
        GttBackendNamingDispatcher dispatcher = new GttBackendNamingDispatcher();
        CheckVerifier.newVerifier()
                .onFile("src/test/files/gtt/demo/order/api/endpoint/ViolationControlle.java")
                .withCheck(dispatcher)
                .verifyIssues();
    }

    @Test
    public void testWithPackageIssues() {
        GttBackendNamingDispatcher dispatcher = new GttBackendNamingDispatcher();
        CheckVerifier.newVerifier()
                .onFile("src/test/files/gtt/demo/order/api/endpoint/ViolationPackageController.java")
                .withCheck(dispatcher)
                .verifyIssues();
    }

}
