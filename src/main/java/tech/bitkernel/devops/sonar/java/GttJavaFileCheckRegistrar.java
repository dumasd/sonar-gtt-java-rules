package tech.bitkernel.devops.sonar.java;

import org.sonar.plugins.java.api.CheckRegistrar;
import org.sonarsource.api.sonarlint.SonarLintSide;

@SonarLintSide
public class GttJavaFileCheckRegistrar implements CheckRegistrar {
    @Override
    public void register(RegistrarContext registrarContext) {
        registrarContext.registerClassesForRepository(GttJavaRulesDefinition.REPOSITORY_KEY, RulesList.getChecks(), RulesList.getJavaTestChecks());
    }
}
