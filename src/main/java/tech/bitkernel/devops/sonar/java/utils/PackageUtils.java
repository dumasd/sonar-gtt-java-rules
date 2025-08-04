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
package tech.bitkernel.devops.sonar.java.utils;

import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.PackageDeclarationTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.Deque;
import java.util.LinkedList;

public class PackageUtils {
    private PackageUtils() {
    }

    public static String packageName(PackageDeclarationTree packageDeclarationTree, String separator) {
        if (packageDeclarationTree == null) {
            return "";
        }
        Deque<String> pieces = new LinkedList<>();
        ExpressionTree expr = packageDeclarationTree.packageName();
        while (expr.is(Tree.Kind.MEMBER_SELECT)) {
            MemberSelectExpressionTree mse = (MemberSelectExpressionTree) expr;
            pieces.push(mse.identifier().name());
            pieces.push(separator);
            expr = mse.expression();
        }
        if (expr.is(Tree.Kind.IDENTIFIER)) {
            IdentifierTree idt = (IdentifierTree) expr;
            pieces.push(idt.name());
        }

        StringBuilder sb = new StringBuilder();
        for (String piece : pieces) {
            sb.append(piece);
        }
        return sb.toString();
    }
}
