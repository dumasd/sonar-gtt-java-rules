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
package tech.bitkernel.devops.sonar.java;

import org.sonar.api.SonarRuntime;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonarsource.analyzer.commons.RuleMetadataLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;


public class GttJavaRulesDefinition implements RulesDefinition {

    private static final String RESOURCE_BASE_PATH = "org/sonar/l10n/java/rules/java";

    public static final String REPOSITORY_KEY = "bitkernel-gtt-java";

    public static final String REPOSITORY_NAME = "Bitkernel GTT Repository";

    private static final Set<String> RULE_TEMPLATES_KEY = Collections.emptySet();

    private final SonarRuntime runtime;

    public GttJavaRulesDefinition(SonarRuntime runtime) {
        this.runtime = runtime;
    }


    @Override
    public void define(Context context) {
        NewRepository repository = context.createRepository(REPOSITORY_KEY, "java").setName(REPOSITORY_NAME);
        RuleMetadataLoader ruleMetadataLoader = new RuleMetadataLoader(RESOURCE_BASE_PATH, runtime);
        ruleMetadataLoader.addRulesByAnnotatedClass(repository, new ArrayList<>(RulesList.getChecks()));
        setTemplates(repository);
        repository.done();
    }

    private static void setTemplates(NewRepository repository) {
        RULE_TEMPLATES_KEY.stream()
                .map(repository::rule)
                .filter(Objects::nonNull)
                .forEach(rule -> rule.setTemplate(true));
    }
}
