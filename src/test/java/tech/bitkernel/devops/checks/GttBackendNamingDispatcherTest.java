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


}
