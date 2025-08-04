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

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.java.model.PackageUtils;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.PackageDeclarationTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Rule(key = "GttBackendNaming", name = "GttBackendNaming", description = "Check the code style compliance of the Gtt project.", priority = Priority.MAJOR)
public class GttBackendNamingDispatcher extends IssuableSubscriptionVisitor {

    private static final List<String> V1_BASE_PACKAGES = Arrays.asList(
            "api",
            "app",
            "app.config",
            "app.controller",
            "app.filter",
            "bean.bo",
            "bean.command.",
            "bean.dto",
            "core.util",
            "core.constant",
            "core.dao",
            "core.entity",
            "core.service"
    );

    private static final List<String> V2_BASE_PACKAGES = Arrays.asList(
            "api.endpoint",
            "api.controller",
            "api.service",
            "api.model.command",
            "api.model.dto",
            "api.model.mapper",
            "app",
            "app.config",
            "app.filter",
            "core.service",
            "core.bo",
            "core.constant",
            "core.util",
            "core.mapper",
            "repo.dao",
            "repo.entity"
    );

    private static final Pattern V1_BASE_PACKAGE_PATTERN = Pattern.compile("^gtt\\.[a-z_][a-z0-9_]*\\.[a-z_][a-z0-9_]*\\.(api|app|bean|core)?((?:\\.[a-z_][a-z0-9_]*)*)$");
    private static final Pattern V2_BASE_PACKAGE_PATTERN = Pattern.compile("^gtt\\.[a-z_][a-z0-9_]*\\.[a-z_][a-z0-9_]*\\.(api|app|core|repo)?((?:\\.[a-z_][a-z0-9_]*)*)$");

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Arrays.asList(Tree.Kind.CLASS, Tree.Kind.INTERFACE);
    }

    @Override
    public void visitNode(Tree tree) {
        ClassTree classTree = (ClassTree) tree;

        PackageDeclarationTree packageDeclarationTree = context.getTree().packageDeclaration();
        if (packageDeclarationTree != null) {
            String packageName = PackageUtils.packageName(packageDeclarationTree, ".");

            Matcher matcher = V1_BASE_PACKAGE_PATTERN.matcher(packageName);
            if (!matcher.matches()) {
                matcher = V2_BASE_PACKAGE_PATTERN.matcher(packageName);
            }

            if (matcher.matches()) {
                String base = matcher.group(1);
                String rest = matcher.group(2);

                String restPackage = rest.isEmpty() ? base : base + rest;
                if (V2_BASE_PACKAGES.stream().anyMatch(restPackage::startsWith)) {
                    GttBackendNamingV2Rule rule = new GttBackendNamingV2Rule(this, classTree, context);
                    rule.visit();
                } else if (V1_BASE_PACKAGES.stream().anyMatch(restPackage::startsWith)) {
                    GttBackendNamingV1Rule rule = new GttBackendNamingV1Rule(this, classTree, context);
                    rule.visit();
                } else {
                    context.reportIssue(this, packageDeclarationTree, "The package of the class does not comply with either the v1 or v2 conventions.");
                }
            } else {
                context.reportIssue(this, packageDeclarationTree, "The package of the class does not comply with either the v1 or v2 conventions.");
            }
        }

        super.visitNode(tree);
    }
}
