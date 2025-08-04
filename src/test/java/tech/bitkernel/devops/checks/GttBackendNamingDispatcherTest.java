/*
 * This file is part of Bitkernel GTT Java Rules.
 *
 * Bitkernel GTT Java Rules is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Bitkernel GTT Java Rules is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
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
