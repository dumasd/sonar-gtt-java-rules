package tech.bitkernel.devops;

import org.junit.jupiter.api.Test;
import org.sonar.api.rule.RuleKey;
import org.sonar.java.checks.verifier.TestCheckRegistrarContext;
import tech.bitkernel.devops.sonar.java.GttJavaFileCheckRegistrar;
import tech.bitkernel.devops.sonar.java.GttJavaRulesDefinition;

import static org.assertj.core.api.Assertions.assertThat;

public class GttJavaFileCheckRegistrarTest {
    @Test
    void checkRegisteredRulesKeysAndClasses() {
        TestCheckRegistrarContext context = new TestCheckRegistrarContext();

        GttJavaFileCheckRegistrar registrar = new GttJavaFileCheckRegistrar();
        registrar.register(context);

        assertThat(context.mainRuleKeys).extracting(RuleKey::toString).containsExactly(
                GttJavaRulesDefinition.REPOSITORY_KEY + ":GttBackendNaming");

        assertThat(context.mainCheckClasses).extracting(Class::getSimpleName).containsExactly(
                "GttBackendNamingDispatcher");
    }
}
