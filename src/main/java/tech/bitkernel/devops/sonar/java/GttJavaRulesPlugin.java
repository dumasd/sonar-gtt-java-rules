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

import org.sonar.api.Plugin;

public class GttJavaRulesPlugin implements Plugin {
    @Override
    public void define(Context context) {
        // server extensions -> objects are instantiated during server startup
        context.addExtension(GttJavaRulesDefinition.class);

        // batch extensions -> objects are instantiated during code analysis
        context.addExtension(GttJavaFileCheckRegistrar.class);
    }
}
