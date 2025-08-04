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
package tech.bitkernel.devops.sonar.java.checks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.plugins.java.api.JavaCheck;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.ClassTree;

import java.net.URI;

public class GttBackendNamingV2Rule extends AbstractGttBackendNamingRule {

    private static final Logger LOGGER = LoggerFactory.getLogger(GttBackendNamingV2Rule.class);

    protected GttBackendNamingV2Rule(JavaCheck javaCheck, ClassTree classTree, JavaFileScannerContext context) {
        super(javaCheck, classTree, context);
    }


    @Override
    public void visit() {
        URI fileUri = context.getInputFile().uri();
        if (fileUri.getPath().contains("/api/endpoint/")) {
            visitOpenAPIClass();
        } else if (fileUri.getPath().contains("/api/controller")) {
            visitControllerClass();
        } else if (fileUri.getPath().contains("/repo/entity/")) {
            visitRepoEntityClass();
        } else if (fileUri.getPath().contains("/repo/dao/")) {
            visitRepoDaoClass();
        }
    }

}
