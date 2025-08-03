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
