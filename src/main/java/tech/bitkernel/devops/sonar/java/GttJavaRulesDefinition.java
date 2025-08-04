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

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionAnnotationLoader;


public class GttJavaRulesDefinition implements RulesDefinition {

    public static final String REPOSITORY_KEY = "BitkernelGttJava";

    public static final String REPOSITORY_NAME = "BitkernelGttJava";


    @Override
    public void define(Context context) {
        NewRepository repository = context.createRepository(REPOSITORY_KEY, "java").setName(REPOSITORY_NAME);
        new RulesDefinitionAnnotationLoader()
                .load(repository, RulesList.getChecks().toArray(new Class[]{}));
        repository.done();
    }
}
