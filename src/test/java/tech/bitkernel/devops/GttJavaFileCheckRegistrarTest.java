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
