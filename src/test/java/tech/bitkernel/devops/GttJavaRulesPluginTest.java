package tech.bitkernel.devops;

import org.junit.jupiter.api.Test;
import org.sonar.api.Plugin;
import org.sonar.api.SonarEdition;
import org.sonar.api.SonarProduct;
import org.sonar.api.SonarQubeSide;
import org.sonar.api.SonarRuntime;
import org.sonar.api.utils.Version;
import tech.bitkernel.devops.sonar.java.GttJavaRulesPlugin;

import static org.assertj.core.api.Assertions.assertThat;

public class GttJavaRulesPluginTest {
    @Test
    void testName() {
        Plugin.Context context = new Plugin.Context(new MockedSonarRuntime());
        new GttJavaRulesPlugin().define(context);
        assertThat(context.getExtensions())
                .extracting(ext -> ((Class) ext).getSimpleName())
                .containsExactlyInAnyOrder(
                        "GttJavaRulesDefinition",
                        "GttJavaFileCheckRegistrar");
    }

    public static class MockedSonarRuntime implements SonarRuntime {

        @Override
        public Version getApiVersion() {
            return Version.create(9, 9);
        }

        @Override
        public SonarProduct getProduct() {
            return SonarProduct.SONARQUBE;
        }

        @Override
        public SonarQubeSide getSonarQubeSide() {
            return SonarQubeSide.SCANNER;
        }

        @Override
        public SonarEdition getEdition() {
            return SonarEdition.COMMUNITY;
        }
    }
}
